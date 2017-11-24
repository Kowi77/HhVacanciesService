package kov.develop.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/*import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;*/

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//@Configuration
/*@EnableTransactionManagement
@ComponentScan("kov.develop")
@PropertySource("classpath:db/db.properties")*/
//@EnableJpaRepositories("kov.develop.repository")
public class DataConfig {

    private static final Logger log = LogManager.getLogger(DataConfig.class);

    private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";
    private static final String PROP_HIBERNATE_DIALECT = "db.hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "db.hibernate.show_sql";
    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "db.entitymanager.packages.to.scan";
    private static final String PROP_HIBERNATE_LAZY_LOAD = "hibernate.enable_lazy_load_no_trans";
    private static final String PROP_HIBERNATE_SQL_COMMENT = "hibernate.use_sql_comments";

   /* @javax.annotation.Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        System.out.println(PROP_DATABASE_DRIVER);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        properties.put(PROP_HIBERNATE_LAZY_LOAD, env.getRequiredProperty(PROP_HIBERNATE_LAZY_LOAD));
        properties.put(PROP_HIBERNATE_SQL_COMMENT, env.getRequiredProperty(PROP_HIBERNATE_SQL_COMMENT));
        return properties;
    }

    @Value("classpath:db/schema.sql")
    private Resource dbschemaSqlScript;
    *//*@Value("classpath:db/data.sql")
    private Resource testDataSqlScript;*//*

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        try {
            DriverManager.getConnection(env.getRequiredProperty(PROP_DATABASE_URL),
                    env.getRequiredProperty(PROP_DATABASE_USERNAME),
                    env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        } catch (SQLException e) {
            log.error("Cannot connecting to DB by {} URL!", env.getRequiredProperty(PROP_DATABASE_URL));
        }
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource());
        initializer.setDatabasePopulator(getDatabasePopulator());
        return initializer;
    }

    private DatabasePopulator getDatabasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSqlScriptEncoding("utf-8");
        populator.addScript(dbschemaSqlScript);
       // populator.addScript(testDataSqlScript);
        return populator;
    }*/
}
