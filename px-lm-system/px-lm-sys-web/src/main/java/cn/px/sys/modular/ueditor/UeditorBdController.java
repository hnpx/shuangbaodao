package cn.px.sys.modular.ueditor;




import cn.hutool.core.io.FileUtil;
import cn.px.base.support.context.Resources;
import cn.px.base.support.file.SftpHelper;
import cn.px.base.support.file.vo.FileVo;
import cn.px.base.support.http.HttpCode;
import cn.px.sys.core.util.ResponseUtils;
import cn.px.sys.modular.system.controller.file.Uploader;
import com.alibaba.fastjson.JSON;
import com.sun.jersey.api.client.Client;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ueditorbd")
@Api(value = "ueditor富文本编辑器上传文件")
public class UeditorBdController {
    public static final String UPLOAD_PATH = "F:\\profile\\upload";
    @Value("${sftp.tempDir}")
    public String path;
    @Value("${sftp.enable:false}")
    public boolean ftpEnable;
    @Autowired
    private SftpHelper sftpHelper;
    

    @ResponseBody
    @RequestMapping(value="/config", method={RequestMethod.GET, RequestMethod.POST})
    public void editorUpload(HttpServletRequest request, HttpServletResponse response, String action) {
        response.setContentType("application/json");

        try {
            if("config".equals(action)){    //如果是初始化
                //Resource resource = new ClassPathResource("config.json");
                String path = System.getProperty("user.dir") + "/config.json";
                File file = new File(path);
               // File file = resource.getFile();
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String exec = stringBuilder.toString();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
            }else if("uploadimage".equals(action) || "uploadvideo".equals(action) || "uploadfile".equals(action)){    //如果是上传图片、视频、和其他文件
                try {
                    StandardMultipartHttpServletRequest standard = new StandardMultipartHttpServletRequest(request);
                    MultiValueMap<String, MultipartFile> files = standard.getMultiFileMap();//得到文件map对象

                    for(List<MultipartFile> fileList: files.values()){
                        for(MultipartFile pic : fileList){
                            JSONObject jo = new JSONObject();
                            long size = pic.getSize();    //文件大小
                            String originalFilename = pic.getOriginalFilename();  //原来的文件名
                            Map<String, String> map = this.imageUp(pic, request, action);
                            if("200".equals(map.get("code"))){    //如果上传成功
                                jo.put("state", "SUCCESS");
                                jo.put("original", originalFilename);//原来的文件名
                                jo.put("size", size);//文件大小
                                jo.put("title", originalFilename);//随意，代表的是鼠标经过图片时显示的文字
                                jo.put("type", FilenameUtils.getExtension(pic.getOriginalFilename()));//文件后缀名
                                jo.put("url", map.get("url"));//这里的url字段表示的是上传后的图片在图片服务器的完整地址（http://ip:端口/***/***/***.jpg）
                            }else{    //如果上传失败
                            }
                            ResponseUtils.renderJson(response, jo.toString());
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Map<String, String> imageUp(MultipartFile file, HttpServletRequest request, String type) {
        Long startTs = System.currentTimeMillis();
        Map<String, String> result = new HashMap<>();
        String savePath = path + "/upload/" + type;
        FileUtil.mkdir(savePath);
        Uploader up = new Uploader(request);
        up.setSavePath(savePath);
        up.setMaxSize(20000); // 单位KB
        try {
            up.upload(file, type);
            FileVo fv = this.sftpHelper.upload(up.getUfile(), "files/" + type,startTs + file.getOriginalFilename(), true, this.ftpEnable, true);
            fv.setName(up.getOriginalName());
            //拼接路径
            fv.setPath(this.sftpHelper.getUrl()+fv.getPath());
            fv.setSimPath(this.sftpHelper.getUrl()+fv.getSimPath());
            result.put("code", "200");
            result.put("url", fv.getSimPath());
            result.put("fileName", up.getOriginalName());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "201");
            return result;
        }
    }
}
