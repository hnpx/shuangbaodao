package cn.px.sys.modular.spike.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 兑换订单(Orders)
 *
 * @author
 * @since 2020-09-09 09:25:52
 */
@TableName("orders")
public class OrdersEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -44652707803686627L;
    /**
     * 订单编号
     */
    @TableField("numbering")
    private String numbering;
    /**
     * 兑换人id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 兑换产品id
     */
    @TableField("product_id")
    private Long productId;
    /**
     * 兑换数量
     */
    @TableField("number")
    private Integer number;
    /**
     * 兑换状态
     */
    @TableField("status")
    private Integer status;


    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}