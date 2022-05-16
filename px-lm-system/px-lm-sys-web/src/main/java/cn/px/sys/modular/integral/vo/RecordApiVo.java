package cn.px.sys.modular.integral.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Map;

@Data
public class RecordApiVo {

    Page<Map<String,Object>> page;

    Map<String,?> map;
    private int count;

}
