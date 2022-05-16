package cn.px.sys.modular.mechanism.vo;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class MechanismVo extends BaseModel implements Serializable {
    /**
     * 机构名称
     */
    private String name;
    /**
     * 机构头图
     */
    private String headFigure;
    /**
     * 机构简介
     */
    private String introduction;
}
