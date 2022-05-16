package cn.px.sys.modular.system.controller.file;

import cn.hutool.core.io.FileUtil;
import cn.px.base.core.AbstractController;
import cn.px.base.support.context.Resources;
import cn.px.base.support.file.SftpHelper;
import cn.px.base.support.file.vo.FileVo;
import cn.px.base.support.http.HttpCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/file/upload")
public class FileUploadController extends AbstractController {

	@Value("${sftp.tempDir}")
	public String path;
	@Value("${sftp.enable:false}")
	public boolean ftpEnable;

	@Autowired
	private SftpHelper sftpHelper;
	@ApiOperation(value = "上传图片")
	@PostMapping(value = "/image/{type}")
	public Object imageUp(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response,
						  org.springframework.ui.Model modelMap, @PathVariable("type") String type) {
		String savePath = path + "/upload/" + type;
		FileUtil.mkdir(savePath);
		Uploader up = new Uploader(request);
		up.setSavePath(savePath);
		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
		up.setAllowFiles(fileType);
		up.setMaxSize(20000); // 单位KB
		try {
			up.upload(file, type);
			FileVo fv = this.sftpHelper.upload(up.getUfile(), "files/img/" + type, true, this.ftpEnable, true);
			fv.setName(up.getOriginalName());
			//拼接路径
			fv.setPath(this.sftpHelper.getUrl()+fv.getPath());
			fv.setSimPath(this.sftpHelper.getUrl()+fv.getSimPath());
			return super.setSuccessModelMap(fv);
		} catch (Exception e) {
			e.printStackTrace();
			return super.setModelMap(HttpCode.CONFLICT, Resources.getMessage("FILE.UPLOAD.ERROR"));
		}
	}


	@ApiOperation(value = "上传图片")
	@PostMapping(value = "/imageLayui/{type}")
	public Map imageUpLayui(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response,
						  org.springframework.ui.Model modelMap, @PathVariable("type") String type) {
		String savePath = path + "/upload/" + type;
		FileUtil.mkdir(savePath);
		Uploader up = new Uploader(request);
		up.setSavePath(savePath);
		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
		up.setAllowFiles(fileType);
		up.setMaxSize(20000); // 单位KB
		try {
			up.upload(file, type);
			FileVo fv = this.sftpHelper.upload(up.getUfile(), "files/img/" + type, true, this.ftpEnable, true);
			fv.setName(up.getOriginalName());
			//拼接路径
			fv.setPath(this.sftpHelper.getUrl()+fv.getPath());
			fv.setSimPath(this.sftpHelper.getUrl()+fv.getSimPath());

			Map map=new HashMap();
			Map map2=new HashMap();

			map.put("code",0);
			map.put("msg","");
			map.put("data",map2);
			map2.put("src",fv.getSimPath());
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@ApiOperation(value = "上传文件")
	@PostMapping(value = "/doc/{type}")
	public Object docUp(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response,
						  org.springframework.ui.Model modelMap, @PathVariable("type") String type) {
		String savePath = path + "/upload/" + type;
		FileUtil.mkdir(savePath);
		Uploader up = new Uploader(request);
		up.setSavePath(savePath);
		String[] fileType = { ".doc", ".docs", ".xls", ".xlsx", ".txt" };
		up.setAllowFiles(fileType);
		up.setMaxSize(100000); // 单位KB
		try {
			up.upload(file, type);
			FileVo fv = this.sftpHelper.upload(up.getUfile(), "files/img/" + type, true, this.ftpEnable, true);
			fv.setName(up.getOriginalName());
			//拼接路径
			fv.setPath(this.sftpHelper.getUrl()+fv.getPath());
			fv.setSimPath(this.sftpHelper.getUrl()+fv.getSimPath());
			return super.setSuccessModelMap(fv);
		} catch (Exception e) {
			e.printStackTrace();
			return super.setModelMap(HttpCode.CONFLICT, Resources.getMessage("FILE.UPLOAD.ERROR"));
		}
	}
}
