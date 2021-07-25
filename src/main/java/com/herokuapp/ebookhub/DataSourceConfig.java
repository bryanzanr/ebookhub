package com.herokuapp.ebookhub;

// import org.json.simple.JSONException;
// import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.annotation.PropertySource;

import org.springframework.core.env.Environment;
// import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@PropertySource("application.properties")//Finds the properties file in the classpath
public class DataSourceConfig {

    @Autowired
    Environment environment;

    //   @Value("${spring.datasource.url}")
//  @Value("${SPRING_DATASOURCE_URL}")
  private String dbUrl;
  //   @Value("${spring.datasource.username}")
  //  @Value("${SPRING_DATASOURCE_USERNAME}")
    private String dbUser;
  //   @Value("${spring.datasource.password}")
  //  @Value("${SPRING_DATASOURCE_PASSWORD}")
    private String dbPass;

	private Environment firebaseEnvironment;

	public DataSourceConfig() {}

    public Environment getEnvironment() {
		return this.firebaseEnvironment;
    }

    @Bean
    public DataSource dataSource() throws SQLException, IOException {
        // System.out.println("MASUK" + dbUrl);
        dbUrl = environment.getProperty("spring.datasource.url");
        dbUser = environment.getProperty("spring.datasource.username");
        dbPass = environment.getProperty("spring.datasource.password");
        if (dbUrl == null || dbUrl.isEmpty()) {
//        return new HikariDataSource();
            return DataSourceBuilder.create().build();
        } else {
//      HikariConfig config = new HikariConfig();
//      config.setDriverClassName("com.mysql.jdbc.Driver");
//      config.setJdbcUrl(dbUrl);
//      config.setUsername(dbUser);
//      config.setPassword(dbPass);
//      return new HikariDataSource(config);
			// FirebaseConfig firebaseConfig = new FirebaseConfig(this.environment);
			// firebaseConfig.getFirebaseToken();
			firebaseEnvironment = this.environment;
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
            dataSourceBuilder.url(dbUrl);
            dataSourceBuilder.username(dbUser);
            dataSourceBuilder.password(dbPass);
            return dataSourceBuilder.build();
        }
    }
}