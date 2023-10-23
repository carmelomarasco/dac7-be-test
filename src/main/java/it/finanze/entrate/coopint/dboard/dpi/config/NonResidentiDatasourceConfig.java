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
        basePackages =  {"it.finanze.entrate.coopint.dboard.dpi.nr.command.repository","it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.lazy"},
        entityManagerFactoryRef = "nonResidentiEntityManager",
        transactionManagerRef = "chainedTransactionManager")
public class NonResidentiDatasourceConfig {

    @Autowired
    Environment env;

    @Bean
    public HikariDataSource nonResidentiDatasource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setAutoCommit(false);
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.non-residenti.driver-class-name")));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.non-residenti.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.non-residenti.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.non-residenti.password"));
        dataSource.setMaximumPoolSize(10);
        log.info("############# Configurazione datasource NON RESIDENT --> OK #############");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean nonResidentiEntityManager() {
        log.info("############# Configurazione LocalContainerEntityManagerFactoryBean non residenti entity --> OK ");
        return DataSourceConfigUtils.setEntityManager(
                env,
                nonResidentiDatasource(),
                new String[]{
                        "it.finanze.entrate.coopint.dboard.dpi.nr.command.entity",
                        "it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy",
                        "it.finanze.entrate.coopint.dboard.dpi.common.entity"
                },
                "DBOARD_DPI_NR");
    }

    @Bean
    public PlatformTransactionManager nonResidentiTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(nonResidentiEntityManager().getObject());
        return transactionManager;
    }

}
