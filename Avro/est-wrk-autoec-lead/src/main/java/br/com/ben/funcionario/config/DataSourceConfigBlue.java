package br.com.mac.funcionario.config;

import br.com.mac.funcionario.repository.support.JpaSpecificationExecutorWithProjectionImpl;
import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConfigurationProperties("spring.datasource-blue")
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactoryBlue",
    transactionManagerRef = "transactionManagerBlue",
    basePackages = {"br.com.mac.funcionario.repository.blue"},
    repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class
)
public class DataSourceConfigBlue extends HikariConfigBlue {

  public DataSourceConfigBlue(HikariPropertiesBlue hikariPropertiesBlue) {
    super(hikariPropertiesBlue);
  }

  @Bean
  public HikariDataSource dataSourceBlue() {
    return new HikariDataSource(this);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBlue(
      final HikariDataSource dataSourceBlue) {

    LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
    result.setDataSource(dataSourceBlue);
    result.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    result.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
    result.setPackagesToScan("br.com.mac.funcionario.blue.domain");
    result.setJpaProperties(JPA_BLUE_PROPERTIES);
    result.setJpaDialect(jpaDialectBlue());

    return result;
  }

  @Bean
  public PlatformTransactionManager transactionManagerBlue(
      EntityManagerFactory entityManagerFactoryBlue) {
    return new JpaTransactionManager(entityManagerFactoryBlue);
  }

  @Bean
  public JpaDialect jpaDialectBlue() {
    return new CustomHibernateJpaDialect();
  }
}
