package cn.px.base.support.file;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.thread.ThreadUtil;
import cn.px.base.support.file.vo.FileVo;
import cn.px.base.util.DataUtil;
import com.jcraft.jsch.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Properties;

/**
 * SFTP的配置信息VO，通过注入写入进来
 *
 * @author ShuoYuan
 */
@Slf4j
@Data
public class SftpHelper {

    private String host;
    private Integer port;
    private String susername;
    private String spassword;
    private Integer timeout;
    private Integer aliveMax;
    private String baseDir;
    private String rootDir;// FTP根目录，非注入路径，需要根据需求写入
    private String url;
    private Session session;
    /**
     * FTP服务类
     */
    private ChannelSftp sftp;

    public SftpHelper() {
    }

    public SftpHelper(String host, Integer port, String susername, String spassword, Integer timeout, Integer aliveMax,
                      String baseDir, String rootDir, String url) {
        super();
        this.host = host;
        this.port = port;
        this.susername = susername;
        this.spassword = spassword;
        this.timeout = timeout;
        this.aliveMax = aliveMax;
        this.baseDir = baseDir;
        this.rootDir = rootDir;
        this.port = this.port == null ? 22 : this.port;
        this.url = url;
//		this.login();
    }

    private void login() {
        try {
            JSch jsch = new JSch();

            session = jsch.getSession(this.susername, this.host, this.port);

            if (DataUtil.isNotEmpty(this.spassword)) {
                session.setPassword(this.spassword);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dPath  文件本地路径
     * @param toPath 上传FTP路径
     * @param sFlag  压缩标记
     */
    public FileVo upload(String dPath, String toPath, boolean sFlag) {
        File file = new File(dPath);
        return this.upload(file, toPath, sFlag, true, sFlag);
    }

    public FileVo upload(File file, String toPath, String fileName, boolean sFlag, boolean ftpEnable, boolean synFlag) {
        FileVo fvo = new FileVo();

        try {
            // 上传原图
            fvo.setPath(upload(file, toPath, fileName, ftpEnable, synFlag));
            if (sFlag) {// 压缩图片
                ImgUtil.scale(file, file, 0.3F);
                fvo.setSimPath(upload(file, toPath + "/sim", fileName, ftpEnable, synFlag));
            }
            // 清除本地图片缓存
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fvo;
    }

    /**
     * @param file
     * @param toPath
     * @param sFlag     压缩标记
     * @param ftpEnable 是否使用Ftp标记
     * @param synFlag   是否同步上传
     * @return
     */
    public FileVo upload(File file, String toPath, boolean sFlag, boolean ftpEnable, boolean synFlag) {
        FileVo fvo = new FileVo();

        String fileType = FileTypeUtil.getType(file);
        String fileName = UUID.fastUUID().toString(true) + "." + fileType;
        try {
            // 上传原图
            fvo.setPath(upload(file, toPath, fileName, ftpEnable, synFlag));
            if (sFlag) {// 压缩图片
                ImgUtil.scale(file, file, 0.3F);
                fvo.setSimPath(upload(file, toPath + "/sim", fileName, ftpEnable, synFlag));
            }
            // 清除本地图片缓存
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fvo;
    }

    /**
     * 上传文件
     *
     * @param file      原文件
     * @param toPath    目标路径，文件夹
     * @param fileName  文件名称
     * @param ftpEnable 是否通过ftp上传
     * @param synFlag   是否同步上传，异步上传会先返回图片存储地址，通过新开线程上传文件，默认异步上传
     * @return
     */
    private String upload(File file, String toPath, String fileName, boolean ftpEnable, boolean synFlag) {
        if (!file.isFile()) {
            return null;
        }
        // 如果路径不存在
//		if (!this.sftp.exist(toPath)) {
//			this.sftp.mkDirs(toPath);
//		}
        String destPath = this.baseDir + "/" + this.rootDir + "/" + toPath + "/";
        try {
            if (ftpEnable) {
                // TODO 缺少上传重试机制，如果上传错误，提供重新提交的方法，多次重试任然有问题后，抛出异常信息
                if (synFlag) {
                    sftpUpload(file.getPath(), destPath, fileName);
//					FileUtil.del(file);
                } else {
                    log.info("开始上传文件：" + file.getPath() + " 到：" + destPath);
                    ThreadUtil.execute(() -> {
                        try {
                            sftpUpload(file.getPath(), destPath, fileName);
//							FileUtil.del(file);
                            log.info("上传结束：" + destPath + "/" + fileName);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            e.printStackTrace();
                        }
                    });
                }
            } else {
                // 如果不通过FTP，则通过文件复制处理
                log.info("开始上传文件：" + file.getPath() + " 到：" + destPath + fileName);
                if (synFlag) {
                    FileUtil.copy(file.getPath(), destPath + fileName, true);
//					FileUtil.del(file);
                } else {
                    ThreadUtil.execute(() -> {
                        FileUtil.copy(file.getPath(), destPath + fileName, true);
//						FileUtil.del(file);
                        log.info("上传结束：" + destPath + fileName);
                    });
                }
            }
        } catch (Exception e) {// 传输异常
            e.printStackTrace();
            return null;
        }
        return this.rootDir + "/" + toPath + "/" + fileName;
    }

    private synchronized void sftpUpload(String in, String to, String fileName) throws SftpException {
        this.login();
        try {
            sftp.cd(to);
        } catch (SftpException e) {
            // 目录不存在，则创建文件夹
            String[] dirs = to.split("/");
            String tempPath = "";
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) {
                    continue;
                }
                tempPath += "/" + dir;
                try {
                    sftp.cd(tempPath);
                } catch (SftpException ex) {
//					ex.printStackTrace();
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
        sftp.put(in, fileName);
        this.logout();
    }

    /**
     * 上传文件
     *
     * @param file
     * @param toPath
     * @return
     */
    private String upload(File file, String toPath) {
        String fileType = FileTypeUtil.getType(file);
        String fileName = UUID.fastUUID().toString(true) + "." + fileType;
        return this.upload(file, toPath, fileName, true, true);
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    @Override
    public String toString() {
        return "{\"host\":\"" + host + "\",\"port\":\"" + port + "\",\"susername\":\"" + susername
                + "\",\"spassword\":\"" + spassword + "\",\"timeout\":\"" + timeout + "\",\"aliveMax\":\"" + aliveMax
                + "\",\"baseDir\":\"" + baseDir + "\",\"rootDir\":\"" + rootDir + "\"}  ";
    }

}
