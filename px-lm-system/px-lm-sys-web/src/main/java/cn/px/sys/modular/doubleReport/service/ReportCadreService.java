package cn.px.sys.modular.doubleReport.service;

import cn.px.base.Constants;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.util.ExcelUploadUtil;
import cn.px.base.util.ExportExcelUtil;
import cn.px.base.util.UUIDUtil;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.mapper.CommunityClassMapper;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.doubleReport.constant.IsBindEnum;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.mapper.ReportCadreMapper;
import cn.px.sys.modular.doubleReport.mapper.UnitMapper;
import cn.px.sys.modular.doubleReport.vo.UserVo;
import cn.px.sys.modular.homeClass.entity.HomeClassEntity;
import cn.px.sys.modular.homeClass.mapper.HomeClassMapper;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.integralWater.service.IntegralWaterService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.mapper.MemberMapper;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党员干部信息管理(ReportCadre)表服务实现类
 *
 * @author
 * @since 2020-08-28 14:10:47
 */
@Service("reportCadreService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "reportCadre")
public class ReportCadreService extends BaseServiceImpl<ReportCadreEntity, ReportCadreMapper> {

    private final static String excel2003 = ".xls";
    private final static String excel2007 = ".xlsx";
    @Resource
    private AllUserService allUserService;
    @Resource
    private ReportCadreService reportCadreService;
    @Resource
    private UnitMapper unitMapper;
    @Resource
    private UnitService unitService;
    @Resource
    private MemberService memberService;
    @Resource
    private CommunityClassService communityClassService;

    @Resource
    private HomeClassMapper homeClassMapper;
    @Resource
    private CommunityClassMapper communityClassMapper;
    @Resource
    private ReportCadreMapper reportCadreMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private IntegralWaterService integralWaterService;


    /**
     * @param in
     * @param fileName 处理上传的excel文件
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = null;
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
            }
        }
        return list;
    }
     /**
     * @param file
     * 处理上传的excel文件
     *
     * */
    public  List<ReportCadreEntity> getBankListByExcel(MultipartFile file) throws Exception{
        List<ReportCadreEntity> list = null;
        String title[] = {"name", "phone", "birthday", "belongingCommunity", "belongingUnit", "belongingHome", "address"};
        List<Map<String, Object>> excelData = ExcelUploadUtil.readExcelByMultipartFile(file, title, 2, 1);
        List<UnitEntity> unitList = unitMapper.selectList(new QueryWrapper<UnitEntity>());
        List<HomeClassEntity> homeList = homeClassMapper.selectList(new QueryWrapper<HomeClassEntity>());
        List<CommunityClassEntity> classList = communityClassMapper.selectList(new QueryWrapper<CommunityClassEntity>());
        List<ReportCadreEntity> result = new ArrayList<ReportCadreEntity>();
        Date now = new Date();
        for(Map<String, Object> map : excelData){
            ReportCadreEntity entity = new ReportCadreEntity();
            Long cadreId = UUIDUtil.getOrderIdByUUId();
            entity.setId(cadreId);
            entity.setCreateTime(now);
            entity.setUpdateTime(now);
            entity.setEnable(Integer.valueOf(1));
            if(map.get("name") != null ){
                entity.setName(String.valueOf(map.get("name")));
            }
            if(map.get("phone") != null ){
                entity.setPhone(String.valueOf(map.get("phone")));
            }
            for(CommunityClassEntity classEntity : classList){
                if(map.get("belongingCommunity") != null && String.valueOf(map.get("belongingCommunity")).trim().equals(classEntity.getName())){
                    entity.setBelongingCommunity(classEntity.getId());
                }
            }
            for(HomeClassEntity homeEntity : homeList){
                if(map.get("belongingHome") != null && String.valueOf(map.get("belongingHome")).trim().equals(homeEntity.getName())){
                    entity.setBelongingHome(homeEntity.getId());
                }
            }
            for(UnitEntity unintEntity : unitList){
                if(map.get("belongingUnit") != null && String.valueOf(map.get("belongingUnit")).trim().equals(unintEntity.getName())){
                    entity.setBelongingUnit(unintEntity.getId());
                }
            }
            result.add(entity);
        }
        return result;
    }

    //判断excel文件的格式
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);
        } else if (excel2007.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }


    public int insert(ReportCadreEntity reportCadreEntity) {
        return super.mapper.insert(reportCadreEntity);
    }


    /**
     * 信息绑定
     *
     * @return
     */
    @Transactional
    public int getIsBind(UserVo userVo, Long userId) {

        int type = userVo.getType();

        switch (type) {
            //普通用户
            case 1:
               int n = reportCadreMapper.countByPhone(userVo.getPhone());
               int m = unitMapper.countByPhone(userVo.getPhone());
               if(n ==0 && m ==0){
                   AllUserEntity allUserEntity2 = new AllUserEntity();
                   //判断普通用户是不是第一次注册信息
                   AllUserEntity allUser =  allUserService.queryById(userId);
                   if(allUser != null){
                       if(allUser.getType() == null){
                           allUserEntity2.setIntegral(5);
                           allUserEntity2.setRemainingPoints(5);
                           //积分记录
                           integralRecordService.sendInteger(allUser.getId());
                       }
                   }
                   allUserEntity2.setId(userId);
                   allUserEntity2.setPhone(userVo.getPhone());
                   allUserEntity2.setType(userVo.getType());
                   allUserService.update(allUserEntity2);
                   MemberEntity memberEntity = new MemberEntity();
                   memberEntity.setName(userVo.getName());
                   memberEntity.setAddress(userVo.getAddress());
                   memberEntity.setBelongingCommunity(userVo.getBelongingCommunity());
                   memberEntity.setBelongingHome(userVo.getBelongingHome());
                   memberEntity.setBelongingUnit(userVo.getBelongingUnit());
                   memberEntity.setIdNumber(userVo.getIdNumber());
                   memberEntity.setPhone(userVo.getPhone());
                   memberEntity.setUserId(userId);
                   memberEntity.setIsBind(IsBindEnum.IS_BIND_TRUE.getValue());
                   memberEntity.setExpertise(userVo.getExpertise());
                   if(memberMapper.getMemberEntityByPhone(userId) != null){
                       memberEntity.setId(memberMapper.getMemberEntityByPhone(userId).getId());
                   }
                   memberService.update(memberEntity);
                   break;
               }else {
                   return 3;
               }

            //党团员干部
            case 2:
                ReportCadreEntity reportCadreEntity = super.mapper.getReportCadreEntityByPhone(userVo.getPhone());
                 if (reportCadreEntity != null) {
                     reportCadreEntity.setName(userVo.getName());
                     reportCadreEntity.setUserId(userId);
                     reportCadreEntity.setBelongingCommunity(userVo.getBelongingCommunity());
                     reportCadreEntity.setBelongingHome(userVo.getBelongingHome());
                     reportCadreEntity.setBelongingUnit(userVo.getBelongingUnit());
                     reportCadreEntity.setIsBind(IsBindEnum.IS_BIND_TRUE.getValue());
                     reportCadreEntity.setAddress(userVo.getAddress());
                     reportCadreEntity.setExpertise(userVo.getExpertise());
                     reportCadreService.update(reportCadreEntity);
                     AllUserEntity allUserEntity = new AllUserEntity();
                     //判断普通用户是不是第一次注册信息
                     AllUserEntity allUser =  allUserService.queryById(userId);
                     if(allUser != null){
                         if(allUser.getType() == null){
                             allUserEntity.setIntegral(5);
                             allUserEntity.setRemainingPoints(5);
                             //积分记录
                             integralRecordService.sendInteger(allUser.getId());
                         }
                     }
                     allUserEntity.setId(userId);
                     allUserEntity.setPhone(userVo.getPhone());
                     allUserEntity.setType(userVo.getType());
                     allUserService.update(allUserEntity);
                     break;
                 }else {
                     return 1;
                 }
            //单位
            case 3:
                UnitEntity unitEntity = unitMapper.getUnitEntityByPhone(userVo.getPhone());
                  if (unitEntity != null) {
                      unitEntity.setName(userVo.getName());
                      unitEntity.setUserId(userId);
                      unitEntity.setBelongingCommunity(userVo.getBelongingCommunity());
                      unitService.update(unitEntity);
                      AllUserEntity allUserEntity1 = new AllUserEntity();
                      //判断普通用户是不是第一次注册信息
                      AllUserEntity allUser =  allUserService.queryById(userId);
                      if(allUser != null){
                          if(allUser.getType() == null){
                              allUserEntity1.setIntegral(5);
                              allUserEntity1.setRemainingPoints(5);
                              //积分记录
                              integralRecordService.sendInteger(allUser.getId());
                          }
                      }
                      allUserEntity1.setId(userId);
                      allUserEntity1.setPhone(userVo.getPhone());
                      allUserEntity1.setType(userVo.getType());
                      allUserService.update(allUserEntity1);
                      break;
                  }else {
                      return 2;
                  }

        }
      return 0;
    }


    /**
     * 查询党员干部列表
     * @param
     * @param name
     * @param phone
     * @param
     * @return
     */
    public Page<Map<String,Object>> getListHome(String name, String phone,Long userId,Long belongingCommunity ,Long belongingUnit,Long expertise){
        Page page = LayuiPageFactory.defaultPage();
        Long cid;
        if(userId == 1){
            cid = null;
        }else {
            CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
            cid =  communityClassEntity.getId();
        }
        return super.mapper.getListHome(page,name,phone,cid,belongingCommunity,belongingUnit,expertise);
    }
    /**
     * 查询党员干部列表
     * @param
     * @param name
     * @param phone
     * @param
     * @return
     */
    public Page<Map<String,Object>> getListHome1(String name, String phone,Long userId,Long belongingCommunity ,Long belongingUnit,Long expertise){
        Page page = new Page(1,99999);
        Long cid;
        if(userId == 1){
            cid = null;
        }else {
            CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
            cid =  communityClassEntity.getId();
        }
        return super.mapper.getListHome(page,name,phone,cid,belongingCommunity,belongingUnit,expertise);
    }
    public Workbook createWorkbook(Long  belongingCommunity, Long belongingUnit, String year){
        //List<Map<String, Object>> list = new ArrayList<>();
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String,Object>> cadre =  reportCadreService.getListHome1(null,null,userId,belongingCommunity,belongingUnit,null);
        cadre.getRecords().forEach(map -> {
            if(map.containsKey("userId")){
                long user = Long.parseLong(map.get("userId").toString());
                int sumIntegral = integralRecordService.sumIntegral(user,year,1)- integralRecordService.sumIntegral(user,year,2);
                int showIntegral = integralWaterService.showIntegral(user,year);
                map.put("showIntegral",showIntegral);
                map.put("remainingPoints",sumIntegral);
                if(map.get("isBind").equals(1)){
                    map.put("isBind","已绑定");
                }else if(map.get("isBind").equals(2)) {
                    map.put("isBind","未绑定");
                }

                if(allUserService.queryById(user)!= null){
                    if(allUserService.queryById(user).getStatus()==1){   //1. 未报到2.已报道0.未绑定信息
                        map.put("reportStatus","未报到");
                    }else {
                        map.put("reportStatus","已报到");
                    }
                }else {
                    map.put("reportStatus","未绑定信息");
                }
                //所属单位
                if(map.get("belongingUnit") != null){
                  UnitEntity unitEntity =  unitService.queryById(Long.parseLong(map.get("belongingUnit").toString()));
                  map.put("unit",unitEntity.getName());
                }else{
                    map.put("unit","");
                }
            }else {
                map.put("showIntegral",0);
                map.put("remainingPoints",0);
                map.put("isBind","未绑定");
                map.put("reportStatus","未报到");
                map.put("unit","");
            }


        });
        String titles[] = {"name","nickname","phone","remainingPoints","showIntegral","isBind","reportStatus","unit"};
        String titleNames[] = {"党员干部姓名","昵称","电话","公益积分","双报到积分","是否绑定","是否报道","所属单位"};
        Workbook wb = ExportExcelUtil.createWorkbook(cadre.getRecords(), titles, titleNames);
        return wb;
    }


    public Page<Map<String,Object>> getListByUnitId(Page page,Long id,String name,String phone){
        return super.mapper.getListByUnitId(page,id,name,phone);
    }

    /**
     * 通过手机号查询党员干部信息是否有匹配
     * @param phone
     * @return
     */
    public int countByPhone(String phone){
        return super.mapper.countByPhone(phone);
    }


    public List<ReportCadreEntity> getReportCadreEntityByunit(Long unit){
        return super.mapper.getReportCadreEntityByunit(unit);
    }
}