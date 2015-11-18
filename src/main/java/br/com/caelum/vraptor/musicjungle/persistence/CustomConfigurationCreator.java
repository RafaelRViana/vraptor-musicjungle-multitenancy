package br.com.caelum.vraptor.musicjungle.persistence;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import org.hibernate.cfg.Configuration;

import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.hibernate.ConfigurationCreator;
import br.com.caelum.vraptor.hibernate.MultiTenancyConfiguration;
import br.com.caelum.vraptor.musicjungle.model.Music;
import br.com.caelum.vraptor.musicjungle.model.User;

@Specializes
public class CustomConfigurationCreator extends ConfigurationCreator {

	/**
	 * @deprecated cdi eyes only
	 */	
	protected CustomConfigurationCreator() {
		this(null, null);	
	}
	
	@Inject
	public CustomConfigurationCreator(Environment environment, MultiTenancyConfiguration multiTenancyConfiguration) {
		super(environment, multiTenancyConfiguration);
	}
	
	@Override
	protected void extraConfigurations(Configuration configuration) {
		super.extraConfigurations(configuration);
	
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3307/" + configuration.getProperty("tenant_id") );
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "");
		configuration.setProperty("hibernate.connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
		configuration.setProperty("hibernate.c3p0.max_size", "20");
		configuration.setProperty("hibernate.show_sql", "false");
		configuration.setProperty("hibernate.format_sql", "true");
		
		configuration.addAnnotatedClass(Music.class);
		configuration.addAnnotatedClass(User.class);
	}
	
}