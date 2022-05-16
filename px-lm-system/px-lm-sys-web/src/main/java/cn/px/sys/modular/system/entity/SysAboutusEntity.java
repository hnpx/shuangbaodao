package cn.px.sys.modular.system.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.px.base.core.BaseModel;

/**
 * (SysAboutus)
 *
 * @author makejava
 * @since 2020-06-09 14:35:07
 */
@TableName("sys_aboutus")
public class SysAboutusEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -57387393178501147L;
                                            /**
        * 图片
        */ @TableField("image")
    private String image;
                /**
        * 公司简介
        */ @TableField("description")
    private String description;
                /**
        * 邮箱
        */ @TableField("email")
    private String email;
                /**
        * 电话
        */ @TableField("phone")
    private String phone;
                /**
        * 地址
        */ @TableField("address")
    private String address;
    
                                                                                
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
                    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
                    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
                    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
                    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}