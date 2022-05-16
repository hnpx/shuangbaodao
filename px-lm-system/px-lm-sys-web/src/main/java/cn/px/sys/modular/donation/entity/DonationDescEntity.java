package cn.px.sys.modular.donation.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (DonationDesc)
 *
 * @author
 * @since 2020-09-02 18:39:06
 */
@TableName("donation_desc")
public class DonationDescEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 362575549268503105L;
    /**
     * 捐赠类型（1.捐赠物品2.捐赠现金）
     */
    @TableField("type")
    private Integer type;
    /**
     * 描述
     */
    @TableField("description")
    private String description;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}