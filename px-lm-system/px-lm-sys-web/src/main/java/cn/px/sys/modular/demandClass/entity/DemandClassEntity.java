package cn.px.sys.modular.demandClass.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 需求分类(DemandClass)
 *
 * @author
 * @since 2020-08-27 15:45:47
 */
@TableName("demand_class")
public class DemandClassEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -16936133716178201L;
    @TableField("name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}