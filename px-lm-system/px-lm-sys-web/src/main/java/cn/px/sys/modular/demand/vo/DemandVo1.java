package cn.px.sys.modular.demand.vo;

import cn.px.base.core.BaseModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class DemandVo1 extends BaseModel implements Serializable {
    /**
     * 申请人
     */
    private String name1;
    /**
     * 申请电话
     */
    private String phone1;
    /**
     * 需求分类
     */
    private Long demandClass1;
    /**
     * 需求内容
     */
    private String content1;

}
