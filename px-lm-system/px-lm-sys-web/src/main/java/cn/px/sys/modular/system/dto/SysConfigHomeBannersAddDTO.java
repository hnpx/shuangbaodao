package cn.px.sys.modular.system.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Api("添加轮播图DTO")
public class SysConfigHomeBannersAddDTO  implements Serializable {
    private static final long serialVersionUID = 590107664603140766L;
                                        /**
        * 
        */ @ApiModelProperty("跳转链接")
    private String link;
                    /**
        *
        */ @ApiModelProperty("标题")
    private String title;
                /**
        *
        */ @ApiModelProperty("图片")
    private String picUrl;
                /**
        *
        */ @ApiModelProperty("内容")
    private String info;
                /**
        * ，上架状态时根据下架时间定时下架
        */ @ApiModelProperty("下架时间")
    private Date endTime;
                /**
        *
        */ @ApiModelProperty("状态：1、下架；2、上架")
    private Integer status;
    
                                                                        
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
                            
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
                    
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
                    
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
                    
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
                    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}