package com.chrisnor.koutye.config;

import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "MysqlEntityManager",
transactionManagerRef = "MysqlTransactionManager",
basePackages="com.chrisnor.koutye.repository2")
@EnableAutoConfiguration
public class MysqlConfigDatabase {

	 @Value("${spring.ds.mysql.driver-class-name}")
	 private String driver;
	 
	 @Value("${spring.ds.mysql.url}")
	 private String url;
	    
	 @Value("${spring.ds.mysql.username}")
	 private String username;
	    
	 @Value("${spring.ds.mysql.password}")
	 private String password;
	    
	 @Value("${entitymanager.packages.to.scan}")
	 private String packageScan;
	 
	 @Autowired
	 private Environment env;
	 
	 //@Primary
	 @Bean(name = "MysqlDataSource")
	 @ConfigurationProperties(prefix="spring.ds.mysql")
	public DataSource MysqlDataSource() { 
	    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(); 
	    dataSourceBuilder.driverClassName(driver);
	    dataSourceBuilder.url(url);
	    dataSourceBuilder.username(username); 
	    dataSourceBuilder.password(password); // System.out.println ("Le mot de passe est :"+ password);
	    return dataSourceBuilder.build(); 
	}
	 
	@Bean(name = "MysqlEntityManager")
	//@Primary
	public LocalContainerEntityManagerFactoryBean MysqlEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(MysqlDataSource());
    em.setPackagesToScan("com.chrisnor.koutye.modelmysql");
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    
    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.dialect",
      env.getProperty("hibernate.dialect"));
    em.setJpaPropertyMap(properties);

   return em;

}

 @Primary
 @Bean(name = "MysqlTransactionManager")
 public PlatformTransactionManager MysqlTransactionManager( @Qualifier("MysqlEntityManager") EntityManagerFactory MysqlEntityManagerFactory) {
   JpaTransactionManager transactionManager = new JpaTransactionManager();
   transactionManager.setEntityManagerFactory(MysqlEntityManager().getObject());
   return transactionManager;
  
}

 //@Primary
@Bean(name = "MysqlSessionFactory")
public LocalSessionFactoryBean MysqlSessionFactory() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(MysqlDataSource());
  
    sessionFactoryBean.setHibernateProperties(hibernateProperties());
    return sessionFactoryBean;
}

private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.jdbc.time_zone", "UTC");
    properties.put("hibernate.hbm2ddl.auto","none");
    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
    properties.put("hibernate.show_sql","true");
    properties.put("hibernate.format_sql","true");
    properties.put("hibernate.ddl-auto","create");
    properties.put("entitymanager.packages.to.scan",packageScan);
    
    return properties;
}	 
}
