package cn.px.sys.modular.evaluation.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.evaluation.entity.EvaluationOfImgEntity;
import cn.px.sys.modular.evaluation.service.EvaluationOfImgService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/evaluationOfImg")
@Api(value = "(EvaluationOfImg)管理")
public class EvaluationOfImgApiController extends BaseController<EvaluationOfImgEntity, EvaluationOfImgService> {

    private static final String PREFIX = "/modular/evaluationOfImg";
    @Autowired
    private HttpServletRequest request;



    @ApiOperation("查询")
    @PutMapping("/read/getimgurl")
    @ResponseBody
    public Object query() {
        return setSuccessModelMap(super.service.getImgUrl());
    }
}
