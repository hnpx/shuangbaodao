package cn.px.sys.modular.system.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.px.base.core.BaseModel;

/**
 * 热词(SysConfigHotWord)
 *
 * @author makejava
 * @since 2020-07-22 21:50:49
 */
@TableName("sys_config_hot_word")
public class SysConfigHotWordEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 301021299451163405L;
                                        /**
        * 跳转链接
        */ @TableField("link")
    private String link;
                    /**
        * 热词
        */ @TableField("name")
    private String name;
                /**
        * 热度
        */ @TableField("hot_num")
    private Integer hotNum;
    
                                                                        
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
                            
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
                    
    public Integer getHotNum() {
        return hotNum;
    }

    public void setHotNum(Integer hotNum) {
        this.hotNum = hotNum;
    }
    
}