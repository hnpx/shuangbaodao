package cn.px.base.consts;

/**
 * 缓存KEY常量表
 */
public interface CacheConstant {

    public interface Area{
        public String AREA_INFO_NAME="AREA_NAME";
        public String AREA_INFO="AREA_INFO";
    }

    /**
     * 商品相关的缓存key
     */
    public interface Goods{
        /**
         * 商品的产品信息
         */
        public String GOODS_PRODUCT_INFO_ENTITY="GOODS_PRODUCT_INFO_ENTITY";
        public String GOODS_ATTRS_ENTITY="GOODS_ATTRS_ENTITY";

    }

    /**
     * 订单相关
     */
    public interface Order{
        /**
         * 订单信息常量
         */
        public String ORDER_INFO_DETAIL="ORDER_INFO_DETAIL";
        /**
         * 订单简要信息常量
         */
        public String ORDER_SIMPLE_INFO_DETAIL="ORDER_SIMPLE_INFO_DETAIL";
        /**
         * 订单商品常量
         */
        public String ORDER_GOODS_INFO_DETAIL="ORDER_GOODS_INFO_DETAIL";
        /**
         * 某个订单的所有商品信息
         */
        public String ORDER_GOODS_INFO_DETAIL_BY_ORDER="ORDER_GOODS_INFO_DETAIL_BY_ORDER";
        /**
         * 订单支付信息
         */
        public String ORDER_PAY_INFO="ORDER_PAY_INFO";
    }

    public interface Supplier{
        /**
         * 供应商下的商铺信息
         */
        public String SUPPLIER_SHOPS_INFOS_2ENTITY="SUPPLIER_SHOPS_INFOS_2ENTITY";
        public String SUPPLIER_SHOPS_INFOS_2SIMPLE="SUPPLIER_SHOPS_INFOS_2SIMPLE";
       /* public String SUPPLIER_SHOPS_INFOS_SELECTSUPPLIERSHOPS="SUPPLIER_SHOPS_INFOS_SELECTSUPPLIERSHOPS";
        public String SUPPLIER_ASSISTANT_SELECTSUPPLIERASSISTANT="SUPPLIER_ASSISTANT_SELECTSUPPLIERASSISTANT";
        public String SUPPLIER_ASSISTANT_ROLE_SELECTSUPPLIERASSISTANT="SUPPLIER_ASSISTANT_ROLE_SELECTSUPPLIERASSISTANT";
        public String SUPPLIER_ASSISTANT_SHOPS_ROLE_SELECTSUPPLIERASSISTANTSHOPSROLE="SUPPLIER_ASSISTANT_SHOPS_ROLE_SELECTSUPPLIERASSISTANTSHOPSROLE";*/
     /*   public String SUPPLIER_INFO_SELECTSUPPLIER="SUPPLIER_INFO_SELECTSUPPLIER";
        public String SUPPLIER_SHOPS_GETSHOPSBYASSISTANT="SUPPLIER_SHOPS_GETSHOPSBYASSISTANT";*/
        /**
         * 微信配置
         */
        public String SUPPLIER_WX_CONFIG="SUPPLIER_WX_CONFIG";
    }
    public interface SysSendTime{

        /**
         * 配送时长
         */
        public String SYS_SEND_TIME_GETSYSSENDTIME="SYS_SEND_TIME_GETSYSSENDTIME";
    }
}
