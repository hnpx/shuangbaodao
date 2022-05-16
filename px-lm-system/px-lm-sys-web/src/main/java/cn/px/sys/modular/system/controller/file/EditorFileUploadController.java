package cn.px.sys.modular.system.controller.file;

import cn.hutool.core.io.FileUtil;
import cn.px.base.core.AbstractController;
import cn.px.base.support.file.SftpHelper;
import cn.px.base.support.file.vo.FileVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/editor/upload")
public class EditorFileUploadController extends AbstractController {

    @Value("${sftp.tempDir}")
    public String path;
    @Value("${sftp.enable:false}")
    public boolean ftpEnable;

    @Autowired
    private SftpHelper sftpHelper;

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/image/{type}")
    public String imageUp(MultipartFile upfile, HttpServletRequest request, HttpServletResponse response,
                          org.springframework.ui.Model modelMap, @PathVariable("type") String type) {
        String savePath = path + "/upload/" + type;
        FileUtil.mkdir(savePath);
        Uploader up = new Uploader(request);
        up.setSavePath(savePath);
        String[] fileType = {".gif", ".png", ".jpg", ".jpeg", ".bmp"};
        up.setAllowFiles(fileType);
        up.setMaxSize(10000); // 单位KB
        FileVo fv = null;
        try {
            up.upload(upfile, type);
            fv = this.sftpHelper.upload(up.getUfile(), "files/img/" + type, true, this.ftpEnable, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = "{\"name\":\"" + up.getFileName() + "\", \"originalName\": \"" + up.getOriginalName()
                + "\", \"size\": " + up.getSize() + ", \"state\": \"" + up.getState() + "\", \"type\": \""
                + up.getType() + "\", \"url\": \"/" + this.sftpHelper.getUrl() + fv.getPath() + "\"}";
        result = result.replaceAll("\\\\", "\\\\");

        return result;
    }
}
