package cn.px.sys.modular.system.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SysFaqVo {
    private String phone;
    private List<Map<String,String>> questions;
}
