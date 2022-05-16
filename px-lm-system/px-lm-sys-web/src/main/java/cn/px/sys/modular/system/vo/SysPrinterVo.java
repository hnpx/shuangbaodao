package cn.px.sys.modular.system.vo;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysPrinterVo extends BaseModel implements Serializable  {

    /**
     * 商铺id
     */
    private Long sid;
    /**
     * 打印机名字
     */
    private String name;
    /**
     * 应用ID(易联云获得)
     */
    private String clientId;
    /**
     * 应用密钥（易联云获得）
     */
    private String clientSecret;
    /**
     * 终端号
     */
    private String machineCode;
    /**
     * 密钥
     */
    private String msign;
    /**
     * access_token
     */
    private String accessToken;
    /**
     * 机器编号
     */
    private String memberCode;
    /**
     * 是否启用（1.启用2.不启用）
     */
    private int status;

    private String data;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 型号
     */
    private String model;
    /**
     * 商铺名称
     */
    private String  shopsName;
}
