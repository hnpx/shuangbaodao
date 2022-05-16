package cn.px.sys.modular.resources.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (ResourcesUser)
 *
 * @author
 * @since 2020-09-04 15:41:00
 */
@TableName("resources_user")
public class ResourcesUserEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 976285161189062944L;
    /**
     * 资源id
     */
    @TableField("resources_id")
    private Long resourcesId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 名字
     */
    @TableField("name")
    private String name;
    /**
     * 电话
     */
    @TableField("phone")
    private String phone;


    public Long getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}