package cn.px.sys.modular.activity.service;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.activity.constant.IsSignEnum;
import cn.px.sys.modular.activity.constant.SignEnum;
import cn.px.sys.modular.activity.constant.TimeStatusEnum;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import cn.px.sys.modular.activity.mapper.ActivityMapper;
import cn.px.sys.modular.activity.vo.*;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.mapper.ReportCadreMapper;
import cn.px.sys.modular.doubleReport.mapper.UnitMapper;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.mapper.MemberMapper;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.project.mapper.ProjectMapper;
import cn.px.sys.modular.project.vo.ProVo;
import cn.px.sys.modular.system.entity.SysConfigHomeBannersEntity;
import cn.px.sys.modular.system.service.SysConfigHomeBannersService;
import cn.px.sys.modular.system.vo.SysConfigHomeBannersVO;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.stylefeng.roses.core.mutidatasource.annotion.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jodd.util.URLDecoder;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ????????????(Activity)??????????????????
 *
 * @author
 * @since 2020-08-27 15:09:55
 */
@Service("activityService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "activity")
public class ActivityService extends BaseServiceImpl<ActivityEntity, ActivityMapper> {
    @Resource
    private ActivityUserSignService activityUserSignService;
    @Resource
    private ActiveCommentService activeCommentService;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private CommunityClassService communityClassService;
    @Resource
    private AllUserService allUserService;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private ReportCadreMapper reportCadreMapper;
    @Resource
    private UnitMapper unitMapper;

    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private SysConfigHomeBannersService sysConfigHomeBannersService;
    @Resource
    private ActivityService activityService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private ReportCadreService reportCadreService;



    /**
     * ????????????
     * @param name
     * @param
     * @return
     */
       public Page<Map<String,Object>> getActivity(String name, Integer status, Long userId, String contactPerson, String phone, Integer timeStatus){
        Page page = LayuiPageFactory.defaultPage();
        Date date = new Date();
        Long cid;
       if(userId == 1){
           cid = null;
       }else {
          CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
           cid =  communityClassEntity.getId();

       }
        return super.mapper.getActivity(page,name,date,status,cid,contactPerson,phone,timeStatus);

      }

    /**
     * ????????????????????????
     * @param page
     * @return
     */
    public Page<Map<String,Object>> getActivityWx(Integer page,Integer pageSize,Integer timeStatus){
        Page page1 = new Page(page,pageSize);
        Date date = new Date();
        return super.mapper.getActivityWx(page1,date,timeStatus);
    }

    public ActivityDeVo getActivityEntity(Long id,Long userId){

        ActivityEntity activityEntity =  super.mapper.selectById(id);
        ActivityDeVo activityDeVo = new ActivityDeVo();
        BeanUtil.copyProperties(activityEntity,activityDeVo);
       String str = URLDecoder.decode(activityDeVo.getIntroduction(),"UTF-8");
        activityDeVo.setIntroduction(str);
        //????????????????????????????????????
        if(activityDeVo.getEndTime().compareTo(new Date())==-1){
            activityDeVo.setTimeStatus(TimeStatusEnum.TIME_STATUS_END.getValue());

        }else{
            if(activityDeVo.getStartTime().compareTo(new Date())==1){
                activityDeVo.setTimeStatus(TimeStatusEnum.TIME_STATUS_TRUE.getValue());
            }else {
                activityDeVo.setTimeStatus(TimeStatusEnum.TIME_STATUS_FALSE.getValue());
            }
        }

        //???????????????????????????????????????
       ActivityUserSignEntity activityUserSignEntity = new ActivityUserSignEntity();
       activityUserSignEntity.setActivityId(id);
       activityUserSignEntity.setUserId(userId);
       ActivityUserSignEntity activityUserSignEntity1 = activityUserSignService.selectOne(activityUserSignEntity);
       if(activityUserSignEntity1 == null){
           activityDeVo.setIsSign(IsSignEnum.IS_SIGN_ENUM_FALSE.getValue());

       }else {
           activityDeVo.setIsSign(IsSignEnum.IS_SIGN_ENUM_TRUE.getValue());
           activityDeVo.setSignInOrOut(activityUserSignEntity1.getStatus());
       }
        List<UserVo> activityUserSignEntityList = activityUserSignService.getListSignBy(id);
        activityDeVo.setUserVoList(activityUserSignEntityList);


        activityUserSignEntityList.forEach(a->{


            Long uId =  a.getUserId();
            if (uId == null){
                return;
            }
            AllUserEntity allUserEntity =   allUserService.queryById(uId);
            if (allUserEntity == null){
                return;
            }
            Integer userType = allUserEntity.getType();
            if (userType == null){
                return;
            }
            //????????????
            if (userType == 1){
                MemberEntity mEntity = new MemberEntity();
                mEntity.setUserId(uId);
                MemberEntity memberEntity = memberService.selectOne(mEntity);
                if (memberEntity==null){
                    return;
                }
                a.setRealName(memberEntity.getName());
                a.setPhone(memberEntity.getPhone());

            };

            //????????????
            if (userType == 2) {
                ReportCadreEntity rEntity = new ReportCadreEntity();
                rEntity.setUserId(uId);
                ReportCadreEntity reportCadreEntity = reportCadreService.selectOne(rEntity);
                a.setRealName(reportCadreEntity.getName());
                a.setPhone(reportCadreEntity.getPhone());
            };

            //??????
            if (userType == 3) {
                UnitEntity uEntity = new UnitEntity();
                uEntity.setUserId(uId);
                UnitEntity unitEntity = unitService.selectOne(uEntity);
                a.setRealName(unitEntity.getName());
                a.setPhone(unitEntity.getPhone());
            }

        });



        List<CommentVo> activeCommentEntityList = activeCommentService.getListBy(id);
        activityDeVo.setCommentVoList(activeCommentEntityList);
        return activityDeVo;
    }

    /**
     * ????????????????????????
     * @return
     */
    public ActiveAndProjectVo getActiveAndProjectVo(){
        ActiveAndProjectVo activeAndProjectVo = new ActiveAndProjectVo();
       List<SysConfigHomeBannersEntity> sysConfigHomeBannersEntityList = sysConfigHomeBannersService.getSysConfigHomeBannersVO();
        activeAndProjectVo.setSysConfigHomeBannersEntityList(sysConfigHomeBannersEntityList);
        List<ActiveVo> activeVoList = super.mapper.getActiveVo(new Date());
        activeAndProjectVo.setActiveVoList(activeVoList);
        List<ProVo> proVoList = projectMapper.getProVo(new Date());
        activeAndProjectVo.setProVoList(proVoList);
        return activeAndProjectVo;
    }


    /**
     * ???????????????
     * @param urlList
     * @param path
     */
    public void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getD(String urlList){
        ServletOutputStream out = null;
        InputStream inputStream = null;
        try {
            //?????????
            String pdfName = "name.jpg";
            // ?????????????????????
            URL url = new URL(urlList);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000);
            //?????????????????????????????????403??????
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            /**
             * ????????????????????????
             */
            int len = 0;
            // ?????? ??????????????????????????????????????????????????????????????????????????????url??????
            HttpServletResponse response = null;
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(pdfName, "UTF-8"));
            response.setHeader("Cache-Control", "no-cache");
            out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }

    }

    /**
     * ??????????????????
     * @param aid ??????id
     * @return
     */
    public int getSignIn(Long aid,Long userId){

        ActivityUserSignEntity activityUserSignEntity = new ActivityUserSignEntity();
        activityUserSignEntity.setUserId(userId);
        activityUserSignEntity.setActivityId(aid);
        ActivityUserSignEntity activityUserSignEntity1 =  activityUserSignService.selectOne(activityUserSignEntity);
        if(activityUserSignEntity1 != null && activityUserSignEntity1.getStatus().equals(SignEnum.SIGN_ENUM_ONE.getValue())){
            activityUserSignEntity1.setStatus(SignEnum.SIGN_ENUM_TWO.getValue());
            ActivityUserSignEntity activityUserSignEntity2 = activityUserSignService.update(activityUserSignEntity1);
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * ??????????????????
     * @param aid ??????id
     * @return
     */
    @Transactional
    public int getSignOut(Long aid,Long userId){

        ActivityEntity activityEntity =  activityService.queryById(aid);
        ActivityUserSignEntity activityUserSignEntity = new ActivityUserSignEntity();
        activityUserSignEntity.setUserId(userId);
        activityUserSignEntity.setActivityId(aid);
        ActivityUserSignEntity activityUserSignEntity1 =  activityUserSignService.selectOne(activityUserSignEntity);
        if(getTime(activityEntity.getEndTime())){
            if(activityUserSignEntity1 != null && activityUserSignEntity1.getStatus().equals(SignEnum.SIGN_ENUM_TWO.getValue()) ){
                //integralRecordService.getIntegralRecordEntity(aid,userId);
                activityUserSignEntity1.setStatus(SignEnum.SIGN_ENUM_THREE.getValue());
                ActivityUserSignEntity activityUserSignEntity2 = activityUserSignService.update(activityUserSignEntity1);
                return 1;
            }else {
                return 0;
            }
        }else {
            return 3;
        }

    }

    /**
     * ?????????????????????????????????????????????
     * @param date
     * @return
     */
    public Boolean getTime(Date date){
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        c.setTime(date);
        long lastly = c.getTimeInMillis();
        return (lastly - now) <= 3600000 & (lastly - now)>= 0;
    }

    /**
     * ????????????id
     * @param userId
     * @return
     */
    public Long getCid(Long userId){
        CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
        Long cid = null;
        if(communityClassEntity != null){
            cid = communityClassEntity.getId();
        }else {
            AllUserEntity allUserEntity = allUserService.queryById(userId);
            int type = allUserEntity.getType();
            //????????????
            if(type==1){
                MemberEntity memberEntity = memberMapper.getMemberEntityByPhone(userId);
                cid = memberEntity.getBelongingCommunity();
            }else if(type == 2){ //????????????
                ReportCadreEntity reportCadreEntity = reportCadreMapper.getReportCadreEntityByUserId(userId);
                cid = reportCadreEntity.getBelongingCommunity();
            }else if(type == 3){ //??????
                UnitEntity unitEntity = unitMapper.getUnitEntityByUserId(userId);
                cid = unitEntity.getBelongingCommunity();
            }
        }
        return cid;
    }

    /**
     * ????????????
     * @param page
     * @return
     */
    public Page<Map<String,Object>> getNote(Integer page,Integer pageSize,Long userId){
        Page  page1 = new Page(page,pageSize);
        return super.mapper.getNote(page1,userId);
    }

    /**
     * ????????????
     * @param page
     * @return
     */
    public Page<Map<String,Object>> getNote1(Integer page,Integer pageSize){

            Page page1 = new Page(page,pageSize);

        return super.mapper.getNote1(page1);
    }


    /**
     * ??????????????????????????????
     * @return
     */
    public List<ActivityEntity> getList(){
        return super.mapper.getList();
    }

    /**
     * ??????
     * @param
     * @return
     */
   public Page<Map<String,Object>> getApply(Integer page,Integer pageSize,Long userId){
        Page page1 = new Page(page,pageSize);
       Long cid = null;
       if(userId == 1){
           cid = null;
       }else {
           CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
           cid =  communityClassEntity.getId();

       }
        return super.mapper.getApply(page1,cid);

    }

    /**
     * ?????????
     * @return
     */
    public int getTime1(Date endTime)  {
        Date date = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");//???2016-08-10 20:40
        String fromDate = simpleFormat.format(date);
        String toDate = simpleFormat.format(endTime);
        long from = 0;
        try {
            from = simpleFormat.parse(fromDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long to = 0;
        try {
            to = simpleFormat.parse(toDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int minutes = (int) ((to - from)/(1000 * 60));
        return minutes;
    }
}