package br.com.mac.funcionario.config;

import com.zaxxer.hikari.HikariConfig;
import java.util.Properties;

public class HikariConfigBlue extends HikariConfig {

  protected final HikariPropertiesBlue hikariPropertiesBlue;

  protected final String PERSISTENCE_UNIT_NAME = "blue";

  protected final Properties JPA_BLUE_PROPERTIES = new Properties();
  //{{
//        put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
////        put("hibernate.hbm2ddl.auto", "update");
////        put("hibernate.ddl-auto", "update");
//        put("show-sql", "true");
//    }};

  protected HikariConfigBlue(HikariPropertiesBlue hikariPropertiesBlue) {
    this.hikariPropertiesBlue = hikariPropertiesBlue;
    setPoolName(this.hikariPropertiesBlue.getPoolName());
    setMinimumIdle(this.hikariPropertiesBlue.getMinimumIdle());
    setMaximumPoolSize(this.hikariPropertiesBlue.getMaximumPoolSize());
    setIdleTimeout(this.hikariPropertiesBlue.getIdleTimeout());
    setConnectionTimeout(this.hikariPropertiesBlue.getConnectionTimeout());
    setTransactionIsolation(this.hikariPropertiesBlue.getTransactionIsolation());

    JPA_BLUE_PROPERTIES.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
    JPA_BLUE_PROPERTIES.put("show-sql", "true");

  }
}
