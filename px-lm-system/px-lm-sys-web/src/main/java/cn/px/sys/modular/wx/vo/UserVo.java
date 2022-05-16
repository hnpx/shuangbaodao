package cn.px.sys.modular.wx.vo;

import lombok.Data;

@Data
public class UserVo {
    private String name;
    private Long belongingCommunity;
    private Long belongingUnit;
}
