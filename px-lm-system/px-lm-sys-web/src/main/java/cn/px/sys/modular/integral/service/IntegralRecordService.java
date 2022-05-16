package cn.px.sys.modular.integral.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Pagination;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.service.ActivityService;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.mapper.CommunityClassMapper;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.mapper.ReportCadreMapper;
import cn.px.sys.modular.doubleReport.mapper.UnitMapper;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.doubleReport.vo.ReportCadreVo;
import cn.px.sys.modular.doubleReport.vo.UnitListVo;
import cn.px.sys.modular.integral.constant.StatusOrderEnum;
import cn.px.sys.modular.integral.constant.TypeEnum;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.mapper.IntegralRecordMapper;
import cn.px.sys.modular.member.mapper.MemberMapper;
import cn.px.sys.modular.member.vo.MemberVo;
import cn.px.sys.modular.merchant.entity.MerchantEntity;
import cn.px.sys.modular.merchant.service.MerchantService;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.service.ProjectService;
import cn.px.sys.modular.spike.constant.TypeEnum1;
import cn.px.sys.modular.spike.entity.OrdersEntity;
import cn.px.sys.modular.spike.entity.ProductEntity;
import cn.px.sys.modular.spike.entity.SpikeEntity;
import cn.px.sys.modular.spike.mapper.OrdersMapper;
import cn.px.sys.modular.spike.service.OrdersService;
import cn.px.sys.modular.spike.service.ProductService;
import cn.px.sys.modular.spike.service.SpikeService;
import cn.px.sys.modular.spike.vo.SpVo;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.stylefeng.roses.core.mutidatasource.annotion.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 积分管理(IntegralRecord)表服务实现类
 *
 * @author
 * @since 2020-09-07 09:35:59
 */
@Service("integralRecordService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "integralRecord")
public class IntegralRecordService extends BaseServiceImpl<IntegralRecordEntity, IntegralRecordMapper> {
   @Resource
   private ActivityService activityService;
   @Resource
   private AllUserService allUserService;
   @Resource
   private ProductService productService;

   @Resource
   private SpikeService spikeService;
   @Resource
   private IntegralRecordService integralRecordService;
   @Resource
   private OrdersService ordersService;
    @Resource
    private MerchantService merchantService;
    @Resource
    private ProjectService projectService;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private CommunityClassMapper communityClassMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private ReportCadreMapper reportCadreMapper;
    @Resource
    private UnitMapper unitMapper;
    @Resource
    private CommunityClassService communityClassService;
    @Resource
    private UnitService unitService;
    /**
     * 志愿活动积分记录
     * @param aid  活动id
     * @return
     */
    @Transactional
    public IntegralRecordEntity getIntegralRecordEntity(Long aid,Long userId){
        //志愿活动积分记录
        ActivityEntity activityEntity = activityService.queryById(aid);
        AllUserEntity allUserEntity = allUserService.queryById(userId);
        allUserEntity.setIntegral(allUserEntity.getIntegral()+activityEntity.getIntegral());
        allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+activityEntity.getIntegral());
        allUserService.update(allUserEntity);
      IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
      integralRecordEntity.setUserId(userId);
      integralRecordEntity.setIntegral(activityEntity.getIntegral());
      integralRecordEntity.setSource("参与活动");
      integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
        IntegralRecordEntity integralRecordEntity1 = super.update(integralRecordEntity);
        return integralRecordEntity1;
    }

    /**
     * 项目积分记录
     * @param pid  项目id
     * @return
     */
    @Transactional
    public IntegralRecordEntity getIntegralRecordEntity1(Long pid,Long userId){
        //志愿活动积分记录
        ProjectEntity projectEntity = projectService.queryById(pid);
        AllUserEntity allUserEntity = allUserService.queryById(userId);
        allUserEntity.setIntegral(allUserEntity.getIntegral()+projectEntity.getIntegral());
        allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+projectEntity.getIntegral());
        allUserService.update(allUserEntity);
        IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
        integralRecordEntity.setUserId(userId);
        integralRecordEntity.setIntegral(projectEntity.getIntegral());
        integralRecordEntity.setSource("参与项目");
        integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
        IntegralRecordEntity integralRecordEntity1 = super.update(integralRecordEntity);
        return integralRecordEntity1;
    }

    /**
     * 积分兑换商品
     * @param pid 商品id
     * @param userId  用户id
     * @return
     */
    @Transactional
    public String getIntegralRecordEntityBySpike(Long pid,Long userId){

        String msg;
        //商品兑换积分记录
       ProductEntity productEntity = productService.queryById(pid);
       AllUserEntity allUserEntity = allUserService.queryById(userId);
       SpVo spvo =  spikeService.getSpikeEntityByPid(pid);
       if(spvo.getType().equals(TypeEnum1.TYPE_ENUM_ONE.getValue())){
           if(spvo.getNumber() > spvo.getRedeemedAmpunt()){
               if(allUserEntity.getRemainingPoints() >= spvo.getPrice()){
                   //更新用户积分
                   allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()-spvo.getPrice());
                   allUserEntity.setPointsConsumption(allUserEntity.getPointsConsumption()+Integer.parseInt(spvo.getPrice().toString()));
                   allUserService.update(allUserEntity);
                   //更新商品已购买数量
                   productEntity.setRedeemedAmpunt(productEntity.getRedeemedAmpunt()+1);
                   productService.update(productEntity);
                   //插入积分记录
                   IntegralRecordEntity integralRecordEntity  = new IntegralRecordEntity();
                   integralRecordEntity.setType(TypeEnum.TYPE_ENUM_TWO.getValue());
                   integralRecordEntity.setUserId(userId);
                   integralRecordEntity.setSource("兑换商品");
                   integralRecordEntity.setIntegral(Integer.parseInt(spvo.getPrice().toString()));
                   integralRecordService.update(integralRecordEntity);
                   //插入订单记录
                   OrdersEntity ordersEntity = new OrdersEntity();
                   ordersEntity.setUserId(userId);
                   ordersEntity.setProductId(pid);
                   ordersEntity.setNumber(1);
                   ordersEntity.setStatus(StatusOrderEnum.STATUS_ORDER_ENUM_ONE.getValue());
                   ordersService.update(ordersEntity);
               }else {
                   Integer integral = spvo.getPrice();
                   msg = "对不起您的积分不足，无法兑换！(您需"+integral+"积分才能兑换）";
                   return msg;
               }


           }else {
              msg = "库存不足无法兑换";
            return msg;
           }

       }else {
           if(spvo.getNumber() > spvo.getRedeemedAmpunt()){
            int count =  ordersMapper.count(pid,userId,spvo.getStartTime(),spvo.getEndTime());
            if(spvo.getLimitNumber() > count){
                if(allUserEntity.getRemainingPoints() >= spvo.getPrice()){
                    //更新用户积分
                    allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()-spvo.getPrice());
                    allUserEntity.setPointsConsumption(allUserEntity.getPointsConsumption()+Integer.parseInt(spvo.getPrice().toString()));
                    allUserService.update(allUserEntity);
                    //更新商品已购买数量
                    SpikeEntity spikeEntity = new SpikeEntity();
                    spikeEntity.setId(spvo.getId());
                    spikeEntity.setRedeemedAmpunt(spvo.getRedeemedAmpunt()+1);
                    spikeEntity.setNumberPeopleExchanged(spvo.getNumberPeopleExchanged()+1);
                    spikeService.update(spikeEntity);
                    //插入积分记录
                    IntegralRecordEntity integralRecordEntity  = new IntegralRecordEntity();
                    integralRecordEntity.setType(TypeEnum.TYPE_ENUM_TWO.getValue());
                    integralRecordEntity.setUserId(userId);
                    integralRecordEntity.setSource("秒杀商品");
                    integralRecordEntity.setIntegral(Integer.parseInt(spvo.getPrice().toString()));
                    integralRecordService.update(integralRecordEntity);
                    //插入订单记录
                    OrdersEntity ordersEntity = new OrdersEntity();
                    ordersEntity.setUserId(userId);
                    ordersEntity.setProductId(pid);
                    ordersEntity.setNumber(1);
                    ordersEntity.setStatus(StatusOrderEnum.STATUS_ORDER_ENUM_ONE.getValue());
                    ordersService.update(ordersEntity);

                }else {
                    Integer integral = spvo.getPrice();
                    msg = "对不起您的积分不足，无法兑换！(您需"+integral+"积分才能兑换）";
                    return msg;
                }
            }else {
                msg = "您已超出最大限制不能再进行兑换";
                return msg;
            }



           }else {
               msg = "库存不足无法兑换";
               return msg;
           }

       }
       return null;
    }


    /**
     * 查询用户列表
     * @param
     * @param userId
     * @return
     */
   public Page<Map<String,Object>> getHomeList(Long userId){

       Page page = LayuiPageFactory.defaultPage();
      return super.mapper.getHomeList(page,userId);
    }

    /**
     *
     * @param mid 商家id
     * @return
     */
    @Transactional
    public String allUser(Long mid,Integer integral,Long userId){

        String msg = null;

     MerchantEntity merchantEntity = merchantService.queryById(mid);
    AllUserEntity allUserEntity1 =   allUserService.queryById(userId);
    if(merchantService.getById(mid) != null){
        if(allUserEntity1.getRemainingPoints() >= integral){
            allUserEntity1.setRemainingPoints(allUserEntity1.getRemainingPoints()-integral);
            allUserEntity1.setPointsConsumption(allUserEntity1.getPointsConsumption()+integral);
            allUserService.update(allUserEntity1);
            IntegralRecordEntity integralRecordEntity1 = new IntegralRecordEntity();
            integralRecordEntity1.setIntegral(integral);
            integralRecordEntity1.setUserId(allUserEntity1.getId());
            integralRecordEntity1.setSource("商铺兑换");
            integralRecordEntity1.setMid(mid);
            integralRecordEntity1.setType(TypeEnum.TYPE_ENUM_TWO.getValue());
            this.update(integralRecordEntity1);
            AllUserEntity allUserEntity =  allUserService.queryById(merchantEntity.getUserId());
           /* Integer i = allUserEntity.getIntegral();
            if(i== null){
                i=0;
            }
            Integer j = allUserEntity.getRemainingPoints();
            if(j== null){
                j=0;
            }
            allUserEntity.setIntegral(i+integral);
            allUserEntity.setRemainingPoints(j+integral);
            allUserService.update(allUserEntity);*/
//            IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
//            integralRecordEntity.setIntegral(1);
//            integralRecordEntity.setUserId(allUserEntity.getId());
//            integralRecordEntity.setSource("商铺兑换");
//            integralRecordEntity.setMid(mid);
//            integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
//            this.update(integralRecordEntity);
            IntegralRecordEntity integralRecordEntity2 = new IntegralRecordEntity();
            integralRecordEntity2.setIntegral(1);
            integralRecordEntity2.setUserId(allUserEntity.getId());
            integralRecordEntity2.setSource("商铺兑换奖励积分");
            integralRecordEntity2.setMid(mid);
            integralRecordEntity2.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
            this.update(integralRecordEntity2);
        }else {
            msg = "您的积分不足";
            return msg;
        }
    }else {
        msg = "此商铺不存在无法兑换";
        return msg;
    }
     return msg;
    }



    /**

     * @param page
     * @return
     */
    public Page<Map<String,Object>> FindIntergralList(Page<Long> page){
        return super.mapper.FindIntergralList(page);
    }

    /**

     * @param page
     * @return
     */
    public Page<Map<String,Object>> FindIntergralListByUser(Page<Long> page){
        return super.mapper.FindIntergralListByUser(page);
    }

    public Map<String,Object> FindIntergralListByUserId(Long userId){
        return super.mapper.FindIntergralListByUserId(userId);
    }

    public Page<Map<String,Object>> FindIntergralListByOrganization(Page<Long> page,Integer isUnit){
        return super.mapper.FindIntergralListByOrganization(page,isUnit);
    }


    public Map<String,Object> FindOrganizationIntergralListByUserId(Long id){
        return super.mapper.FindOrganizationIntergralListByUserId(id);
    }




    public Page<Map<String,Object>> FindIntergralListByCommunity(Page<Long> page){
        return super.mapper.FindIntergralListByCommunity(page);
    }


    public Map<String,Object> FindCommunityByCommunityId(Long id){
        return super.mapper.FindCommunityByCommunityId(id);
    }

    public Page<Map<String,Object>> FindIntergralAVGListByCommunity(Page<Long> page){
        return super.mapper.FindIntergralAVGListByCommunity(page);
    }


    public Map<String,Object> FindCommunityAVGByCommunityId(Long id){
        return super.mapper.FindCommunityAVGByCommunityId(id);
    }

    /**
     * 社区 积分
     */
    public void updateIntegral(){
    List<CommunityClassEntity> communityClassEntityList =  communityClassMapper.getList();

        for (CommunityClassEntity communityClassEntity :communityClassEntityList) {
            int integral= 0;
          MemberVo memberVo = memberMapper.getIntegral(communityClassEntity.getId());
          integral = integral + memberVo.getRemainingPoints();
          ReportCadreVo reportCadreVo = reportCadreMapper.getIntegral(communityClassEntity.getId());
          integral = integral + reportCadreVo.getRemainingPoints();
          UnitListVo unitListVo = unitMapper.getIntegral(communityClassEntity.getId());
          integral = integral + unitListVo.getRemainingPoints();
         communityClassEntity.setIntegral(integral);
         communityClassService.update(communityClassEntity);
        }

    }

    /**
     * 单位积分
     */
    public void updateIntegral1(){
        List<UnitEntity> unitEntityList = unitMapper.getList1();
        for (UnitEntity unitEntity : unitEntityList) {
            int integral= 0;
            MemberVo memberVo = memberMapper.getIntegral1(unitEntity.getId());
            integral = integral + memberVo.getRemainingPoints();
            ReportCadreVo reportCadreVo = reportCadreMapper.getIntegral1(unitEntity.getId());
            integral = integral + reportCadreVo.getRemainingPoints();
            UnitListVo unitListVo = unitMapper.getIntegral1(unitEntity.getUserId());
            integral = integral + unitListVo.getRemainingPoints();
            unitEntity.setIntegral(integral);
            unitService.update(unitEntity);
        }

    }

    /**
     * 查询商家获取积分和
     */

    public int getIntegral (Long mid){
        return super.mapper.getIntegral(mid);
    }




    /**
     * 商铺兑换记录
     * @param
     * @return
     */
   public Page<Map<String,Object>> getRecord(Long mid){
        Page page  = LayuiPageFactory.defaultPage();
        return super.mapper.getRecord(page,mid);

    }


    /**
     *查询所用用户数
     * @return
     */
    public int count(){
        return super.mapper.count();
    }

    /**
     * 通过用户id和积分来源查询积分记录
     * @param userId
     * @param source
     * @return
     */
   public IntegralRecordEntity  getIntegralRecordByUserSource( Long userId, String source,Integer integral){

        return super.mapper.getIntegralRecordByUserSource(userId,source,integral);
    }

    public List<IntegralRecordEntity> getEntityByUser( Long userId){
       return super.mapper.getEntityByUser(userId);
    }


    /**
     * 消耗总积分数
     */
    public Integer getconsumption(){
        return super.mapper.getconsumption();
    }

    /**
     * 商铺总积分数
     */
    public Integer getMerchantsIntegral(){
        return super.mapper.getMerchantsIntegral();
    }

    /**
     * 查询某年公益积分
     *
     */
    public int sumIntegral( Long userId, String year, Integer type){
        return super.mapper.sumIntegral(userId,year,type);
    }
    /**
     * 新用户注册送5积分
     */
    public void sendInteger(Long user){

        IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
        integralRecordEntity.setEnable(Constants.ENABLE_TRUE);
        integralRecordEntity.setIntegral(5);
        integralRecordEntity.setCreateTime(new Date());
        integralRecordEntity.setUpdateTime(new Date());
        integralRecordEntity.setUserId(user);
        integralRecordEntity.setSource("新用户注册赠送积分");
        integralRecordEntity.setType(1);
        integralRecordService.update(integralRecordEntity);
    }

}