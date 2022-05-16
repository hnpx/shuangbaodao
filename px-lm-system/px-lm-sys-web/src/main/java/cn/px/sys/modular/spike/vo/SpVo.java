package cn.px.sys.modular.spike.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpVo {
    private Long id;
    private Integer price;
    private Integer number;
    private Integer redeemedAmpunt;
    private Integer limitNumber;
    private  Integer numberPeopleExchanged;
    /**
     * 1.普通商品2.秒杀商品
     */
    private Integer type;

    private Date startTime;
    private Date endTime;
}
