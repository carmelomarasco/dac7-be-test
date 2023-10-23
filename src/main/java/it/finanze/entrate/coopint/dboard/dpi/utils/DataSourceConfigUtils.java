package it.finanze.entrate.coopint.dboard.dpi.utils;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;

public class DataSourceConfigUtils {


    public static DriverManagerDataSource getInstance() {
        return new DriverManagerDataSource();
    }

    public static LocalContainerEntityManagerFactoryBean setEntityManager(Environment env, HikariDataSource dataSource, String[] packagesToScan, String persistenceUnitName) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName(persistenceUnitName);
        em.setPackagesToScan(packagesToScan);
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
        properties.put("hibernate.cache.use_query_cache", env.getProperty("hibernate.cache.use_query_cache"));
        properties.put("hibernate.id.new_generator_mappings", env.getProperty("hibernate.id.new_generator_mappings"));
        properties.put("hibernate.jdbc.fetch_size", env.getProperty("hibernate.jdbc.fetch_size"));
        properties.put("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));


        em.setJpaPropertyMap(properties);
        return em;
    }


}
