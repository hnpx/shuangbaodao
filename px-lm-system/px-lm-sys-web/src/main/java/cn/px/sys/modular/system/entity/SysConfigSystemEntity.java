package cn.px.sys.modular.system.entity;

import java.util.Date;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.px.base.core.BaseModel;

/**
 * 系统-配置-系统信息(SysConfigSystem)
 *
 * @author makejava
 * @since 2020-05-30 15:54:21
 */
@TableName("sys_config_system")
public class SysConfigSystemEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -43220818439062046L;
                                            /**
        * 系统名称
        */ @TableField("sys_name")
    private String sysName;
                /**
        * 系统代码
        */ @TableField("sys_code")
    private String sysCode;
                /**
        * 系统简称
        */ @TableField("sys_abbreviation")
    private String sysAbbreviation;
                /**
        * logo图片
        */ @TableField("sys_logo")
    private String sysLogo;
                /**
        * 系统访问地址
        */ @TableField("sys_url")
    private String sysUrl;
                /**
        * 系统访问白名单
        */ @TableField("white_list")
    private String whiteList;
    
                                                                                
    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
                    
    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }
                    
    public String getSysAbbreviation() {
        return sysAbbreviation;
    }

    public void setSysAbbreviation(String sysAbbreviation) {
        this.sysAbbreviation = sysAbbreviation;
    }
                    
    public String getSysLogo() {
        return sysLogo;
    }

    public void setSysLogo(String sysLogo) {
        this.sysLogo = sysLogo;
    }
                    
    public String getSysUrl() {
        return sysUrl;
    }

    public void setSysUrl(String sysUrl) {
        this.sysUrl = sysUrl;
    }
                    
    public String getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    public Map<String,String> getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("image",this.getSysLogo());
        map.put("title",this.getSysName());
        map.put("href",this.getSysUrl());
        return map;
    }

}