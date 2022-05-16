package cn.px.sys.modular.wx.entity;

import cn.hutool.core.io.FileUtil;
import cn.px.base.core.AbstractController;
import cn.px.base.support.file.SftpHelper;
import cn.px.base.support.file.vo.FileVo;
import cn.px.sys.modular.system.controller.file.Uploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lp
 * @date: 2020/6/7 15:47
 * @Version: V 1.0
 */
public class WxFile extends AbstractController {
    @Value("${sftp.tempDir}")
    public String path;
    @Value("${sftp.enable:false}")
    public boolean ftpEnable;


    public FileVo imageUp(MultipartFile file, HttpServletRequest request, String type, SftpHelper sftpHelper) throws Exception {
      //生成二维码上传图片地址互换
      // String savePath = "E:\\" + type;
        String savePath = "/" + type;
        FileUtil.mkdir(savePath);
        Uploader up = new Uploader(request);
        up.setSavePath(savePath);
        up.setMaxSize(20000); // 单位KB
        up.upload(file, type);
        FileVo fv = sftpHelper.upload(up.getUfile(), "files/img/" + type, true, true, true);
        fv.setName(up.getOriginalName());
        //拼接路径
        fv.setPath(sftpHelper.getUrl() + fv.getPath());
        fv.setSimPath(sftpHelper.getUrl() + fv.getSimPath());
        return fv;
    }

}
