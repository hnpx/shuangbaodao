package cn.px.sys.modular.system.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.px.base.core.BaseModel;

/**
 * (SysPrinter)
 *
 * @author 
 * @since 2020-07-16 09:05:49
 */
@TableName("sys_printer")
public class SysPrinterEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 160907262221044721L;
                                            /**
        * 商铺id
        */ @TableField("sid")
    private Long sid;
                /**
        * 打印机名字
        */ @TableField("name")
    private String name;
                /**
        * 应用ID(易联云获得)
        */ @TableField("client_id")
    private String clientId;
                /**
        * 应用密钥（易联云获得）
        */ @TableField("client_secret")
    private String clientSecret;
                /**
        * 终端号
        */ @TableField("machine_code")
    private String machineCode;
                /**
        * 密钥
        */ @TableField("msign")
    private String msign;
                /**
        * access_token
        */ @TableField("access_token")
    private String accessToken;
                /**
        * 机器编号
        */ @TableField("member_code")
    private String memberCode;
                /**
        * 是否启用（1.启用2.不启用）
        */ @TableField("status")
    private int status;
                 @TableField("data")
    private String data;
                /**
        * 品牌
        */ @TableField("brand")
    private String brand;
                /**
        * 型号
        */ @TableField("model")
    private String model;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
                    
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
                    
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
                    
    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }
                    
    public String getMsign() {
        return msign;
    }

    public void setMsign(String msign) {
        this.msign = msign;
    }
                    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
                    
    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

                    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
                    
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
                    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
}