package cn.px.sys.modular.homeClass.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (HomeClass)
 *
 * @author
 * @since 2020-08-27 16:15:47
 */
@TableName("home_class")
public class HomeClassEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -87606016302943229L;
    /**
     * 小区名称
     */
    @TableField("name")
    private String name;
    /**
     * 社区id
     */
    @TableField("community_id")
    private Long communityId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

}