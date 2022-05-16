package cn.px.sys.modular.doubleReport.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (ReportIntegral)
 *
 * @author
 * @since 2020-09-11 17:53:08
 */
@TableName("report_integral")
public class ReportIntegralEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -19950988496543087L;
    /**
     * 报道积分
     */
    @TableField("integral")
    private Integer integral;


    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

}