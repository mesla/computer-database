package com.excilys.cdb.webapp.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.excilys.cdb.persistence.DbConnector;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
@ComponentScan(basePackages = { "com.excilys.cdb.persistence", "com.excilys.cdb.binding.mapper",
		"com.excilys.cdb.service", "com.excilys.cdb.webapp.security",
		"com.excilys.cdb.core.model", "com.excilys.cdb.binding.validator" })
public class RootConfiguration {

	@PersistenceContext
	EntityManager em;
	
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
	
	@Bean("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:locale/content");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		txManager.setNestedTransactionAllowed(true);

		return txManager;
	}

	@Bean("sessionFactory")
	public LocalSessionFactoryBean sessionFactory(DbConnector dbConnector) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dbConnector.getDataSource());
		sessionFactory.setPackagesToScan(new String[] {"com.excilys.cdb.core.model"});
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	public JPAQueryFactory queryFactory() {
		return new JPAQueryFactory(em);
	}
}
