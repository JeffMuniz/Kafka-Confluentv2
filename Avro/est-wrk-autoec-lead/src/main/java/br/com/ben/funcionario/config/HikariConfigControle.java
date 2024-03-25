package br.com.mac.funcionario.config;

import com.zaxxer.hikari.HikariConfig;
import java.util.Properties;

public class HikariConfigControle extends HikariConfig {

  protected final HikariPropertiesControle hikariPropertiesControle;

  protected final String PERSISTENCE_UNIT_NAME = "controle";

  protected Properties JPA_CONTROLE_PROPERTIES = new Properties();// {{
    /*
        put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
//        put("hibernate.hbm2ddl.auto", "update");
//        put("hibernate.ddl-auto", "update");
        put("show-sql", "true");
    }};
    */

  protected HikariConfigControle(HikariPropertiesControle hikariPropertiesControle) {
    this.hikariPropertiesControle = hikariPropertiesControle;
    setPoolName(this.hikariPropertiesControle.getPoolName());
    setMinimumIdle(this.hikariPropertiesControle.getMinimumIdle());
    setMaximumPoolSize(this.hikariPropertiesControle.getMaximumPoolSize());
    setIdleTimeout(this.hikariPropertiesControle.getIdleTimeout());
    setConnectionTimeout(this.hikariPropertiesControle.getConnectionTimeout());
    setTransactionIsolation(this.hikariPropertiesControle.getTransactionIsolation());

    JPA_CONTROLE_PROPERTIES.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
    JPA_CONTROLE_PROPERTIES.put("show-sql", "true");


  }
}
