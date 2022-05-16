package cn.px.sys.modular.expertise.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (ExpertiseClass)
 *
 * @author
 * @since 2020-10-10 11:21:47
 */
@TableName("expertise_class")
public class ExpertiseClassEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 488516070580025510L;
    /**
     * 活动名称
     */
    @TableField("name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}