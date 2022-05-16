package cn.px.sys.modular.mechanism.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 机构管理(Mechanism)
 *
 * @author
 * @since 2020-09-01 11:26:05
 */
@TableName("mechanism")
public class MechanismEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 568617874633352485L;
    /**
     * 机构名称
     */
    @TableField("name")
    private String name;
    /**
     * 机构头图
     */
    @TableField("head_figure")
    private String headFigure;
    /**
     * 机构简介
     */
    @TableField("introduction")
    private String introduction;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadFigure() {
        return headFigure;
    }

    public void setHeadFigure(String headFigure) {
        this.headFigure = headFigure;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}