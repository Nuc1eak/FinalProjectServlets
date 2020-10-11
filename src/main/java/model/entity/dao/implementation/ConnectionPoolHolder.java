package model.entity.dao.implementation;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
    private static BasicDataSource dataSource;

    public static DataSource getDataSource() {

        ResourceBundle bundle = ResourceBundle.getBundle("sql");
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    dataSource = new BasicDataSource();
                    dataSource.setDriverClassName("database.driver.class");
                    dataSource.setUrl("database.url");
                    dataSource.setUsername("database.user");
                    dataSource.setPassword("database.password");
                    dataSource.setMaxOpenPreparedStatements(100);
                }
            }
        }
        return dataSource;
    }
}
