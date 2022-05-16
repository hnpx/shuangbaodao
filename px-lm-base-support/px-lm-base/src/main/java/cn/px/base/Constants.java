package cn.px.base;

import cn.px.base.support.cache.CacheKey;
import cn.px.base.util.InstanceUtil;

import java.util.Map;


/**
 * 常量表
 *
 * @author PXHLT
 * @version $Id: Constants.java, v 0.1 2014-2-28 上午11:18:28 PXHLT Exp $
 */
public interface Constants {
	/**
	 * 异常信息统一头信息<br>
	 * 非常遗憾的通知您,程序发生了异常
	 */
	static final String EXCEPTION_HEAD = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
	/** 缓存键值 */
	static final Map<Class<?>, CacheKey> CACHEKEYMAP = InstanceUtil.newHashMap();
	/** 操作名称 */
	static final String OPERATION_NAME = "OPERATION_NAME";
	/** 客户端语言 */
	static final String USERLANGUAGE = "userLanguage";
	/** 客户端主题 */
	static final String WEBTHEME = "webTheme";
	/** 当前用户 */
	static final String CURRENT_USER = "CURRENT_USER";
	/** 客户端信息 */
	static final String USER_AGENT = "USER-AGENT";
	/** 客户端信息 */
	static final String USER_IP = "USER_IP";
	/** 登录地址 */
	static final String LOGIN_URL = "/login.html";
	/** 缓存命名空间 */
	static final String CACHE_NAMESPACE = "PXHLTZB:";

	static final String ENGTEAM_NAMESPACE = "ENGTEAM_NAMESPACE";
	static final String COMPANY_PROJECT = "COMPANY_PROJECT";
	static final String COMPANY_PROJECT1 = "COMPANY_PROJECT1";
	static final String EXPERT = "EXPERT";
	static final String WORKER = "WORKER";
	static final String WORKER1 = "WORKER1";
	/** 缓存命名空间 */
	static final String ENTITY_CACHE_NAMESPACE = "E:PXHLTZB:";
	/** 缓存命名空间 */
	static final String SYSTEM_CACHE_NAMESPACE = "S:PXHLTZB:";
	/** 缓存命名空间 */
	static final String CACHE_NAMESPACE_LOCK = "L:PXHLTZB:";
	/** 上次请求地址 */
	static final String PREREQUEST = CACHE_NAMESPACE + "PREREQUEST";
	/** 上次请求时间 */
	static final String PREREQUEST_TIME = CACHE_NAMESPACE + "PREREQUEST_TIME";
	/** 非法请求次数 */
	static final String MALICIOUS_REQUEST_TIMES = CACHE_NAMESPACE + "MALICIOUS_REQUEST_TIMES";
	/** 在线用户数量 */
	static final String ALLUSER_NUMBER = SYSTEM_CACHE_NAMESPACE + "ALLUSER_NUMBER";
	/** TOKEN */
	static final String TOKEN_KEY = SYSTEM_CACHE_NAMESPACE + "TOKEN_KEY:";
	/** shiro cache */
	static final String REDIS_SHIRO_CACHE = SYSTEM_CACHE_NAMESPACE + "SHIRO-CACHE:";
	/** SESSION */
	static final String REDIS_SHIRO_SESSION = SYSTEM_CACHE_NAMESPACE + "SHIRO-SESSION:";
	/** CACHE */
	static final String MYBATIS_CACHE = "D:PXHLTZB:MYBATIS:";
	/** 默认数据库密码加密key */
	static final String DB_KEY = "20190511";
	/** 临时目录 */
	static final String TEMP_DIR = "/temp/";
	/** 请求报文体 */
	static final String REQUEST_BODY = "PXHLTZB.requestBody";

	/**删除标记 正常*/
	static final Integer ENABLE_TRUE = 1;
	/**删除标记 删除*/
	static final Integer ENABLE_FALSE = 2;

	/**供应商状态 正常*/
	static final Integer STATUS_TRUE = 1;
	/**供应商状态 禁用*/
	static final Integer STATUS_FALSE = 2;

	static final Integer ZERO = 0;
	/** 日志表状态 */
	interface JobState {
		/**
		 * 日志表状态，初始状态，插入
		 */
		static final String INIT_STATS = "I";
		/**
		 * 日志表状态，成功
		 */
		static final String SUCCESS_STATS = "S";
		/**
		 * 日志表状态，失败
		 */
		static final String ERROR_STATS = "E";
		/**
		 * 日志表状态，未执行
		 */
		static final String UN_STATS = "N";
	}

	/** 短信验证码类型 */
	public interface MsgChkType {
		/** 注册 */
		static final String REGISTER = CACHE_NAMESPACE + "REGISTER:";
		/** 登录 */
		static final String LOGIN = CACHE_NAMESPACE + "LOGIN:";
		/** 修改密码验证码 */
		static final String CHGPWD = CACHE_NAMESPACE + "CHGPWD:";
		/** 身份验证验证码 */
		static final String VLDID = CACHE_NAMESPACE + "VLDID:";
		/** 信息变更验证码 */
		static final String CHGINFO = CACHE_NAMESPACE + "CHGINFO:";
		/** 活动确认验证码 */
		static final String AVTCMF = CACHE_NAMESPACE + "AVTCMF:";
	}

	public interface Times {
		/** 一秒 */
		static final long SECOND = 1000;
		/** 一分钟 */
		static final long MINUTE = SECOND * 60;
		/** 一小时 */
		static final long HOUR = MINUTE * 60;
		/** 一天 */
		static final long DAY = HOUR * 24;
		/** 一周 */
		static final long WEEK = DAY * 7;
		/** 一年 */
		static final long YEAR = DAY * 365;
	}
}
