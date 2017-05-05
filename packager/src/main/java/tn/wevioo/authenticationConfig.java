package tn.wevioo;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "authenticationEntityManagerFactory", transactionManagerRef = "authenticationTransactionManager", basePackages = {
		"tn.wevioo.authentication.dao", "tn.wevioo.authentication.dao.impl" })
public class authenticationConfig {

	@Bean(name = "authenticationDatasource")
	@ConfigurationProperties(prefix = "authentication.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "authenticationEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean authenticationEntityManagerFactory(
			org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder builder,
			@Qualifier("authenticationDatasource") DataSource authenticationDataSource) {
		return builder.dataSource(authenticationDataSource).packages("tn.wevioo.authentication.entities").build();
	}

	@Bean(name = "authenticationTransactionManager")
	public PlatformTransactionManager authenticationTransactionManager(
			@Qualifier("authenticationEntityManagerFactory") EntityManagerFactory authenticationEntityManagerFactory) {
		return new JpaTransactionManager(authenticationEntityManagerFactory);
	}
}
