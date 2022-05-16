package cn.px.base.core.config.configurations;

import lombok.Data;

@Data
public class JyMyBatisConfiguration {
	private String typeAliasesPackage;
	private String mapperLocations;
	private String mapperBasePackage;
	private String idType;
	private String dialectType;

}
