package cn.px.sys.modular.system.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class WxConfigEntity extends BaseModel implements Serializable {
    /**
     * 小程序id
     */
    @TableField("appid")
    @NotNull(message = "appid不能为空")
    private String appid;
    /**
     * 小程序密匙
     */
    @TableField("secret")
    @NotNull(message = "secret不能为空")
    private String secret;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
