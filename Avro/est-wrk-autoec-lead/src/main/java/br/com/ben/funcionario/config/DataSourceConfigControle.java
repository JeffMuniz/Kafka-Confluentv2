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
@ConfigurationProperties("spring.datasource-controle")
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactoryControle",
    transactionManagerRef = "transactionManagerControle",
    basePackages = {"br.com.mac.funcionario.repository.controleAcesso"},
    repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class
)
public class DataSourceConfigControle extends HikariConfigControle {

  public DataSourceConfigControle(HikariPropertiesControle hikariPropertiesControle) {
    super(hikariPropertiesControle);
  }

  @Bean
  public HikariDataSource dataSourceControle() {
    return new HikariDataSource(this);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryControle(
      final HikariDataSource dataSourceControle) {

    LocalContainerEntityManagerFactoryBean ret = new LocalContainerEntityManagerFactoryBean();

    ret.setDataSource(dataSourceControle);
    ret.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    ret.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
    ret.setPackagesToScan("br.com.mac.funcionario.controleAcesso.domain");
    ret.setJpaProperties(JPA_CONTROLE_PROPERTIES);
    ret.setJpaDialect(jpaDialectControle());

    return ret;
  }

  @Bean
  public PlatformTransactionManager transactionManagerControle(
      EntityManagerFactory entityManagerFactoryControle) {
    return new JpaTransactionManager(entityManagerFactoryControle);
  }

  @Bean
  public JpaDialect jpaDialectControle() {
    return new CustomHibernateJpaDialect();
  }
}
