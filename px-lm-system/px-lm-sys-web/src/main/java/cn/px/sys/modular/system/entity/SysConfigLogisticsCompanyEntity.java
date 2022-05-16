package cn.px.sys.modular.system.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.px.base.core.BaseModel;

/**
 * (SysConfigLogisticsCompany)
 *
 * @author makejava
 * @since 2020-05-28 09:30:41
 *
 *
 */
@TableName("sys_config_logistics_company")
public class SysConfigLogisticsCompanyEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 349678490344406124L;
                                            /**
        * 公司名称
        */ @TableField("name")
    private String name;
                /**
        * 公司代码
        */ @TableField("code")
    private String code;
                /**
        * API接口地址
        */ @TableField("api_url")
    private String apiUrl;
                /**
        * Token
        */ @TableField("token")
    private String token;
                /**
        * Appid
        */ @TableField("appid")
    private String appid;
                /**
        * logo
        */ @TableField("logo_url")
    private String logoUrl;
    
                                                                                
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
                    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
                    
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
                    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
                    
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
                    
    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    
}