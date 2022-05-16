package cn.px.sys.modular.spike.vo;

import cn.px.base.core.BaseModel;
import cn.px.sys.modular.merchant.entity.MerchantEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
/**
 * 查询积分兑换VO
 */
public class ProductVO  implements Serializable {
    private static final long serialVersionUID = 242089219038575317L;

    @ApiModelProperty("产品名字")
    private String name;

    @ApiModelProperty("产品头图")
    private String headFigure;

    @ApiModelProperty("产品数量")
    private Integer remainingAmount;

    @ApiModelProperty("已兑换数量")
    Integer redeemedAmpunt;

    @ApiModelProperty("产品积分")
    private Integer integral;

    @ApiModelProperty("产品Id")
    Long id;
}