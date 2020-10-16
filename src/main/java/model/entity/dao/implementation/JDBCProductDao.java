package model.entity.dao.implementation;

import model.entity.Product;
import model.entity.dao.ProductDao;
import model.entity.dao.mappers.ProductMapper;
import model.exceptions.InvalidInputException;
import model.exceptions.ProductAlreadyExistException;
import util.QueryManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JDBCProductDao implements ProductDao {
    private Connection connection;

    public JDBCProductDao(Connection connection) {this.connection = connection;}

    @Override
    public Product create(Product entity) {
        final String insertProductInDatabase = QueryManager.getProperty("product.create");
        try {
            connection.setAutoCommit(false);
            PreparedStatement insertProductToTable = connection.prepareStatement(insertProductInDatabase, Statement.RETURN_GENERATED_KEYS);

            insertProductToTable.setInt(1, Integer.parseInt(entity.getCode()));
            insertProductToTable.setString(2, entity.getName());
            insertProductToTable.setInt(3, Integer.parseInt(entity.getPrice()));
            insertProductToTable.setInt(4, Integer.parseInt(entity.getAmount()));
            insertProductToTable.setString(5, entity.getMeasure().toString());

            insertProductToTable.executeUpdate();
            insertProductToTable.executeBatch();
            insertProductToTable.close();

            connection.commit();
        } catch (SQLException throwable) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            throw new ProductAlreadyExistException("Product already exist");
        }
        return entity;
    }

    @Override
    public Product findByNameAndCode(String name, Integer code) {
        final String query = QueryManager.getProperty("product.findByNameOrCode");
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, code);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return getProduct(ps);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateByCode(Integer amount, Integer code) {
        final String query = QueryManager.getProperty("product.update");
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, amount);
            ps.setInt(2, code);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new CopyOnWriteArrayList<>();
        final String query = QueryManager.getProperty("product.findAll");
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            return getProducts(products, rs);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> getProducts(List<Product> productsList, ResultSet resultSet) throws SQLException {
        ProductMapper productMapper = new ProductMapper();
        while(resultSet.next()) {
            Product product = productMapper.extractFromResultSet(resultSet);
            productsList.add(product);
        }
        return new ArrayList<>(productsList);
    }

    private Product getProduct(PreparedStatement ps) throws SQLException {
        Product product = new Product();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            product = new ProductMapper().extractFromResultSet(rs);
        }
        return product;
    }
}
