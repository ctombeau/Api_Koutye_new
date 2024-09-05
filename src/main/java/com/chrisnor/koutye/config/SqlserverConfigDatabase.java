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
@EnableJpaRepositories(entityManagerFactoryRef = "SqlserverEntityManager",
transactionManagerRef = "SqlserverTransactionManager",
basePackages="com.chrisnor.koutye.repository")
@EnableAutoConfiguration
public class SqlserverConfigDatabase {
  
	 @Value("${spring.ds.sqlserver.driver-class-name}")
	 private String driver;
	 
	 @Value("${spring.ds.sqlserver.url}")
	 private String url;
	    
	 @Value("${spring.ds.sqlserver.username}")
	 private String username;
	    
	 @Value("${spring.ds.sqlserver.password}")
	 private String password;
	    
	 @Value("${entitymanager.packages.to.scan}")
	 private String packageScan;
	 
	 @Autowired
	 private Environment env;
	 
	 @Primary
	 @Bean(name = "SqlserverDataSource")
	 @ConfigurationProperties(prefix="spring.ds.sqlserver")
	public DataSource SqlserverDataSource() { 
	    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(); 
	    dataSourceBuilder.driverClassName(driver);
	    dataSourceBuilder.url(url);
	    dataSourceBuilder.username(username); 
	    dataSourceBuilder.password(password); // System.out.println ("Le mot de passe est :"+ password);
	    return dataSourceBuilder.build(); 
	}
	@Bean(name = "SqlserverEntityManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean SqlserverEntityManager() {
     LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
     em.setDataSource(SqlserverDataSource());
     em.setPackagesToScan("com.chrisnor.koutye.model");
     HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
     em.setJpaVendorAdapter(vendorAdapter);
     
     HashMap<String, Object> properties = new HashMap<>();
     properties.put("hibernate.hbm2ddl.auto", "update");
     properties.put("hibernate.dialect",
       env.getProperty("hibernate.dialect"));
     em.setJpaPropertyMap(properties);

    return em;
 
 }
 
  //@Primary
  @Bean(name = "SqlserverTransactionManager")
  public PlatformTransactionManager SqlserverTransactionManager( @Qualifier("SqlserverEntityManager") EntityManagerFactory SqlserverEntityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(SqlserverEntityManager().getObject());
    return transactionManager;
   
 }
 
 //@Primary
 @Bean(name = "SqlserverSessionFactory")
 public LocalSessionFactoryBean SqlserverSessionFactory() {
     LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
     sessionFactoryBean.setDataSource(SqlserverDataSource());
   
     sessionFactoryBean.setHibernateProperties(hibernateProperties());
     return sessionFactoryBean;
}

 private Properties hibernateProperties() {
     Properties properties = new Properties();
     properties.put("hibernate.jdbc.time_zone", "UTC");
     properties.put("hibernate.hbm2ddl.auto","none");
     properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
     properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
     properties.put("hibernate.show_sql","true");
     properties.put("hibernate.format_sql","true");
     properties.put("entitymanager.packages.to.scan",packageScan);
     
     return properties;
 }	 

}
