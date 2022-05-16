package cn.px.sys.modular.communityClass.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (CommunityClass)
 *
 * @author
 * @since 2020-08-27 16:03:07
 */
@TableName("community_class")
public class CommunityClassEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -95374544765942582L;
    /**
     * 社区名称
     */
    @TableField("name")
    private String name;

    /**
     * 账号
     * @return
     */

    @TableField("account")
    private String account;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    @TableField("user_id")
    private Long userId;
    @TableField("integral")
    private int integral;

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}