package cn.px.main.modular.form.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.pojo.page.LayuiPageInfo;
import cn.px.main.modular.form.entity.EgForm;
import cn.px.main.modular.form.model.EgFormParam;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;


/**
 * Guns复杂表单的示例控制器
 *
 * @author PXHLT
 * @Date 2019-02-18 21:37:43
 */
@Controller
@RequestMapping("/egForm")
public class EgFormController extends BaseController {

    private String PREFIX = "/form";

    /**
     * 跳转到主页面
     *
     * @author PXHLT
     * @Date 2019-02-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/egForm.html";
    }

    /**
     * 新增页面
     *
     * @author PXHLT
     * @Date 2019-02-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/egForm_add.html";
    }

    /**
     * 新增接口
     *
     * @author PXHLT
     * @Date 2019-02-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(EgFormParam egFormParam) {
        System.out.println(egFormParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author PXHLT
     * @Date 2019-02-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(EgFormParam egFormParam) {
        System.out.println(egFormParam);
        return ResponseData.success();
    }

    /**
     * 查询列表
     *
     * @author PXHLT
     * @Date 2019-02-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(EgFormParam egFormParam) {

        ArrayList<EgForm> records = new ArrayList<>();
        EgForm egForm = new EgForm();
        egForm.setName("高级表单");
        egForm.setSingleTime("2019-03-27 14:16:03");
        egForm.setBetweenTime("2019-02-07 - 2019-03-14");
        egForm.setFenzuSelect("fenzuSelect");
        egForm.setFormId(111L);
        egForm.setLongText("xxxxxx");
        egForm.setSex("M");
        egForm.setSimpleSelect("111");
        egForm.setMultiSelectHidden("shiro,mybatis-puls");

        records.add(egForm);
        records.add(egForm);

        Page<EgForm> egFormPage = new Page<>();
        egFormPage.setSize(10);
        egFormPage.setTotal(2);
        egFormPage.setRecords(records);

        return LayuiPageFactory.createPageInfo(egFormPage);
    }

}


