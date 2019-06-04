package com.excilys.cdb.webapp.config;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.excilys.cdb.persistence.DbConnector;
import com.excilys.cdb.webapp.servlet.ExceptionResolver;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource({ "classpath:config.properties" })
@ComponentScan(basePackages = { "com.excilys.cdb.persistence", "com.excilys.cdb.binding.mapper", "com.excilys.cdb.service",
		"com.excilys.cdb.webapp.servlet", "com.excilys.cdb.core.model", "com.excilys.cdb.binding.validator" })
public class WebMvcConfig implements WebMvcConfigurer {

	@PersistenceContext
	EntityManager em;

	@Bean
	public JPAQueryFactory queryFactory() {
		return new JPAQueryFactory(em);
	}
	
	
	@Bean("sessionFactory")
	public LocalSessionFactoryBean sessionFactory(DbConnector dbConnector) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dbConnector.getDataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.excilys.cdb.core.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		txManager.setNestedTransactionAllowed(true);

		return txManager;
	}

	@Bean
	public InternalResourceViewResolver resolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/");
	}

	@Bean("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:locale/content");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		return localeResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new ExceptionResolver());
	}

	Properties hibernateProperties() {
		return new Properties() {
			private static final long serialVersionUID = 1L;

			{
				setProperty("hibernate.hbm2ddl.auto", "validate");
				setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
				setProperty("hibernate.globally_quoted_identifiers", "true");
			}
		};
	}

}