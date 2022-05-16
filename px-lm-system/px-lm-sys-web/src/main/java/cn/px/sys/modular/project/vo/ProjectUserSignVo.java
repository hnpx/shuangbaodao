package cn.px.sys.modular.project.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;
@Data
public class ProjectUserSignVo {
    @Excel(name = "时间",width = 20)
    private String createTime;
    @Excel(name = "服务内容",width = 20)
    private String name;
    @Excel(name = "服务地点",width = 20)
    private String address;
    @Excel(name = "服务时长",width = 20)
    private String serviceTime;
    @Excel(name = "积分",width = 20)
    private Integer integral;
    @Excel(name = "认证签字",width = 20)
    private String  sign;
}
