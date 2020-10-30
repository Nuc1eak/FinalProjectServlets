package model.entity.dao.implementation;

import model.entity.dao.CheckDao;
import model.entity.dao.DaoFactory;
import model.entity.dao.ProductDao;
import model.entity.dao.UserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() { return new JDBCUserDao(getConnection()); }

    @Override
    public ProductDao createProductDao() { return new JDBCProductDao(getConnection()); }

    @Override
    public CheckDao createCheckDao() { return new JDBCCheckDao(getConnection()); }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            throw new RuntimeException(e);
        }
    }
}
