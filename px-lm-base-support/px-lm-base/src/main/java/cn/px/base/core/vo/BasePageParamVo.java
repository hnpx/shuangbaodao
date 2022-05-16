package cn.px.base.core.vo;

import lombok.Data;

/**
 * 分页查询基础VO
 */
@Data
public class BasePageParamVo {
    private Integer pageNo;
    private Integer pageSize;
}
