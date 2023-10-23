package it.finanze.entrate.coopint.dboard.dpi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ChainedTransactionManagerConfig {

	@Bean(name = "chainedTransactionManager")
	public ChainedTransactionManager chainedTransactionManager(
            @Autowired @Qualifier(value = "nationalTransactionManager") PlatformTransactionManager nationalTransactionManager,			
			@Autowired @Qualifier(value = "residentiTransactionManager") PlatformTransactionManager residentiTransactionManager,
			@Autowired @Qualifier(value = "nonResidentiTransactionManager") PlatformTransactionManager nonResidentiTransactionManager
			) {

		return new ChainedTransactionManager(
                nationalTransactionManager, 
				residentiTransactionManager,
				nonResidentiTransactionManager);
	}

}