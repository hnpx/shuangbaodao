package cn.px.sys.modular.system.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.px.base.core.BaseModel;

/**
 * 首页广告(SysConfigHomeBanners)
 *
 * @author makejava
 * @since 2020-07-22 21:50:35
 */
@TableName("sys_config_home_banners")
public class SysConfigHomeBannersEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 590107664603140766L;
    /**
     * 跳转链接
     */
    @TableField("link")
    private String link;
    /**
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * 图片
     */
    @TableField("pic_url")
    private String picUrl;
    /**
     * 内容
     */
    @TableField("info")
    private String info;
    /**
     * 下架时间，上架状态时根据下架时间定时下架
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 状态：1、下架；2、上架
     */
    @TableField("status")
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