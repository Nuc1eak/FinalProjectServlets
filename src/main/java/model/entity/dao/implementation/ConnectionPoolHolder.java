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
                    dataSource.setDriverClassName(bundle.getString("database.driver.class"));
                    dataSource.setUrl(bundle.getString("database.url"));
                    dataSource.setUsername(bundle.getString("database.user"));
                    dataSource.setPassword(bundle.getString("database.password"));
                    dataSource.setMaxOpenPreparedStatements(100);
                }
            }
        }
        return dataSource;
    }
}
