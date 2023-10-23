package it.finanze.entrate.coopint.dboard.dpi.config;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import it.finanze.entrate.coopint.dboard.dpi.utils.DataSourceConfigUtils;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Configuration
@EnableJpaRepositories(
        basePackages = "it.finanze.entrate.coopint.dboard.dpi.com.command.repository",
        entityManagerFactoryRef = "nationalEntityManager",
        transactionManagerRef = "chainedTransactionManager")
public class NationalDomainDatasourceConfig {

    @Autowired
    Environment env;

    @Bean
    public HikariDataSource nationalDatasource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setAutoCommit(false);
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.national.driver-class-name")));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.national.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.national.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.national.password"));
        dataSource.setMaximumPoolSize(10);
        log.info("############# Configurazione datasource NATIONAL --> OK #############");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean nationalEntityManager() {
        log.info("############# Configurazione LocalContainerEntityManagerFactoryBean national entity --> OK ");
        return DataSourceConfigUtils.setEntityManager(
                env,
                nationalDatasource(),
                new String[]{
                        "it.finanze.entrate.coopint.dboard.dpi.com.command.entity",
                        "it.finanze.entrate.coopint.dboard.dpi.common.entity"
                }, "DBOARD_DPI_NATIONAL");
    }

    @Bean
    public PlatformTransactionManager nationalTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(nationalEntityManager().getObject());
        return transactionManager;
    }

}
