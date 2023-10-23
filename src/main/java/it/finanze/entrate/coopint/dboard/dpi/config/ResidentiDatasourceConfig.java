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
        basePackages = "it.finanze.entrate.coopint.dboard.dpi.res.command.repository",
        entityManagerFactoryRef = "residentiEntityManager",
        transactionManagerRef = "chainedTransactionManager")
public class ResidentiDatasourceConfig {

    @Autowired
    Environment env;

    @Bean
    public HikariDataSource residentiDatasource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setAutoCommit(false);
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.residenti.driver-class-name")));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.residenti.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.residenti.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.residenti.password"));
        dataSource.setMaximumPoolSize(10);
        log.info("############# Configurazione datasource RESIDENT --> OK #############");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean residentiEntityManager() {
        log.info("############# Configurazione LocalContainerEntityManagerFactoryBean residenti entity --> OK #############");
        return DataSourceConfigUtils.setEntityManager(
                env,
                residentiDatasource(),
                new String[]{
                        "it.finanze.entrate.coopint.dboard.dpi.res.command.entity",
                        "it.finanze.entrate.coopint.dboard.dpi.common.entity"
                }, "DBOARD_DPI_R");
    }

    @Bean
    public PlatformTransactionManager residentiTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(residentiEntityManager().getObject());
        return transactionManager;
    }

}
