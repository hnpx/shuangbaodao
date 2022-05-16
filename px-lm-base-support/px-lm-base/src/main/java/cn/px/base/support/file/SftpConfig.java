package cn.px.base.support.file;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SftpConfig {
	@Value("${sftp.host}")
	private String host;
	@Value("${sftp.port}")
	private Integer port;
	@Value("${sftp.username}")
	private String username;
	@Value("${sftp.password}")
	private String password;
	@Value("${sftp.timeout}")
	private Integer timeout;
	@Value("${sftp.aliveMax}")
	private Integer aliveMax;
	@Value("${sftp.baseDir}")
	private String baseDir;
	@Value("${sftp.rootDir}")
	private String rootDir;// FTP根目录，非注入路径，需要根据需求写入
	@Value("${sftp.url}")
	private String url;

	@Bean
	public SftpHelper sftp() {
		SftpHelper sh = new SftpHelper(host, port, username,password, timeout, aliveMax, baseDir, rootDir,this.url);
		return sh;
	}

}
