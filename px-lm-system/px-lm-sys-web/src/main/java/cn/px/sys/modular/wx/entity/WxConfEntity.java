package cn.px.sys.modular.wx.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (WxConf)
 *
 * @author
 * @since 2020-08-31 11:37:25
 */
@TableName("wx_conf")
public class WxConfEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -19618498680474537L;
    /**
     * appid
     */
    @TableField("appid")
    private String appid;
    /**
     * secret
     */
    @TableField("secret")
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