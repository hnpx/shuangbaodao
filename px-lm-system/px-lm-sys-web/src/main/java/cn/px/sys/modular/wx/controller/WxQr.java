package cn.px.sys.modular.wx.controller;

import cn.px.base.core.AbstractController;
import cn.px.base.support.file.SftpHelper;
import cn.px.base.support.file.vo.FileVo;
import cn.px.sys.modular.wx.entity.WxFile;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*import shaded.org.apache.commons.codec.binary.Base64;*/

/**
 * 微信二维码生成
 * @author cq
 */
public class WxQr extends AbstractController {

    @Value("${sftp.tempDir}")
    public String path;
    @Value("${sftp.enable:false}")
    public boolean ftpEnable;
    @Autowired
    private SftpHelper sftpHelper;

    /**
     * (一) 获取 accessToken
     *
     * @return 返回的是JSON类型 ; 获取accessToken:get("access_token");
     */
    public  JSONObject getAccessToken(String appid,String secret) {
        JSONObject json = null;

        try {
            URL url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.connect(); // 获取连接
            InputStream is = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuffer bs = new StringBuffer();
            String str = null;
            while ((str = buffer.readLine()) != null) {
                bs.append(str);
            }
            json = (JSONObject) JSONObject.parse(bs.toString());
            System.out.println("------------" + json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * (二) 二维码生成：通过该接口生成的小程序码，永久有效，数量暂无限制
     *
     * @param
     * @param accessToken 密匙
     * @return
     */
    public  Object getminiqrQrTwo(Long cid, String accessToken, HttpServletRequest request, String type, SftpHelper sftpHelper) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //?access_token=" + accessToken
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
            Map<String, Object> param = new HashMap<String, Object>();
            // param.put("scene", "a1wJgLz0Dcg,sw_001");// 输入参数 最大32字符
            // param.put("access_token",accessToken);
            param.put("scene", cid);// 输入参数 最大32字符
            param.put("page", "pages/home/activity/detail");// 路径 如果没有默认跳转到首页面微信小程序发布后才可以使用不能添加参数
            param.put("width", "430");// 二维码尺寸
            param.put("is_hyaline", false); // 是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码 参数仅对小程序码生效
            param.put("auto_color", false); // 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调 参数仅对小程序码生效
            // 颜色 auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"}
            // 十进制表示
            Map<String, Object> line_color = new HashMap<String, Object>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            System.out.println("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            // 头部信息
            List<String> list = new ArrayList<String>();
            list.add("Content-Type");
            list.add("application/json");
            headers.put("header", list);
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            System.out.println("调用信永久小程序码小程序生成微URL接口返回结果:" + entity.getBody());
            byte[] result = entity.getBody();
//            System.out.println(Base64.encodeBase64String(result));
            inputStream = new ByteArrayInputStream(result);
            File file = new File("/1.png");// 这里返回的是生成的二维码
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(result);
            fop.flush();
            fop.close();
            MultipartFile multipartFile = new MockMultipartFile("1.png","1.png","image/png", inputStream);
//            System.out.println("++++++++++++++++"+multipartFile);

            WxFile wxFile = new WxFile();
            FileVo fv=wxFile.imageUp(multipartFile,request,type,sftpHelper);
            System.out.println(fv.getPath());
            return fv.getPath();
        } catch (Exception e) {
            System.out.println("调用小程序生成微信永久小程序码URL接口异常" + e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public  Object getminiqrQrTwo1(Long id, String accessToken, HttpServletRequest request, String type, SftpHelper sftpHelper) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //?access_token=" + accessToken
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
            Map<String, Object> param = new HashMap<String, Object>();
            // param.put("scene", "a1wJgLz0Dcg,sw_001");// 输入参数 最大32字符
            // param.put("access_token",accessToken);
            param.put("scene", id);// 输入参数 最大32字符
            param.put("page", "pages/home/yizhan/detail");// 路径 如果没有默认跳转到首页面微信小程序发布后才可以使用不能添加参数
            param.put("width", "430");// 二维码尺寸
            param.put("is_hyaline", false); // 是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码 参数仅对小程序码生效
            param.put("auto_color", false); // 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调 参数仅对小程序码生效
            // 颜色 auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"}
            // 十进制表示
            Map<String, Object> line_color = new HashMap<String, Object>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            System.out.println("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            // 头部信息
            List<String> list = new ArrayList<String>();
            list.add("Content-Type");
            list.add("application/json");
            headers.put("header", list);
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            System.out.println("调用信永久小程序码小程序生成微URL接口返回结果:" + entity.getBody());
            byte[] result = entity.getBody();
//            System.out.println(Base64.encodeBase64String(result));
            inputStream = new ByteArrayInputStream(result);
            File file = new File("/1.png");// 这里返回的是生成的二维码
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(result);
            fop.flush();
            fop.close();
            MultipartFile multipartFile = new MockMultipartFile("1.png","1.png","image/png", inputStream);
//            System.out.println("++++++++++++++++"+multipartFile);

            WxFile wxFile = new WxFile();
            FileVo fv=wxFile.imageUp(multipartFile,request,type,sftpHelper);
            System.out.println(fv.getPath());
            return fv.getPath();
        } catch (Exception e) {
            System.out.println("调用小程序生成微信永久小程序码URL接口异常" + e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public  Object getminiqrQrTwo2(Long pid, String accessToken, HttpServletRequest request, String type, SftpHelper sftpHelper) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //?access_token=" + accessToken
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
            Map<String, Object> param = new HashMap<String, Object>();
            // param.put("scene", "a1wJgLz0Dcg,sw_001");// 输入参数 最大32字符
            // param.put("access_token",accessToken);
            param.put("scene", pid);// 输入参数 最大32字符
            param.put("page", "pages/home/market/prodetail");// 路径 如果没有默认跳转到首页面微信小程序发布后才可以使用不能添加参数
            param.put("width", "430");// 二维码尺寸
            param.put("is_hyaline", false); // 是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码 参数仅对小程序码生效
            param.put("auto_color", false); // 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调 参数仅对小程序码生效
            // 颜色 auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"}
            // 十进制表示
            Map<String, Object> line_color = new HashMap<String, Object>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            System.out.println("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            // 头部信息
            List<String> list = new ArrayList<String>();
            list.add("Content-Type");
            list.add("application/json");
            headers.put("header", list);
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            System.out.println("调用信永久小程序码小程序生成微URL接口返回结果:" + entity.getBody());
            byte[] result = entity.getBody();
//            System.out.println(Base64.encodeBase64String(result));
            inputStream = new ByteArrayInputStream(result);
            File file = new File("/1.png");// 这里返回的是生成的二维码
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(result);
            fop.flush();
            fop.close();
            MultipartFile multipartFile = new MockMultipartFile("1.png","1.png","image/png", inputStream);
//            System.out.println("++++++++++++++++"+multipartFile);

            WxFile wxFile = new WxFile();
            FileVo fv=wxFile.imageUp(multipartFile,request,type,sftpHelper);
            System.out.println(fv.getPath());
            return fv.getPath();
        } catch (Exception e) {
            System.out.println("调用小程序生成微信永久小程序码URL接口异常" + e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
