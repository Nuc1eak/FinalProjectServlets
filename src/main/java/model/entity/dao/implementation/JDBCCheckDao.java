package model.entity.dao.implementation;

import model.entity.Check;
import model.entity.Product;
import model.entity.dao.CheckDao;
import model.entity.dao.mappers.CheckMapper;
import model.entity.dao.mappers.ProductMapper;
import org.apache.log4j.Logger;
import util.QueryManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JDBCCheckDao implements CheckDao {
    private static final Logger log = Logger.getLogger(JDBCCheckDao.class);
    private Connection connection;

    public JDBCCheckDao(Connection connection) {this.connection = connection;}
    @Override
    public Check create(Check entity) {
        final String query = QueryManager.getProperty("check.createNewCheck");
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            CheckMapper mapper = new CheckMapper();
            mapper.extractToStatement(statement, entity);
            int n = statement.executeUpdate();
            log.info("keys: " + n);
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    log.info("generated id: " + entity.getId());
                    connection.commit();
                    return entity;
                } else return null;
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error("Error", ex);
                throw new RuntimeException("cashier.insert.check.unknown");
            }
            log.error("Error", ex);
            throw new RuntimeException("cashier.insert.check.unknown");
        }

    }

    @Override
    public Check findById(int id) {
        return null;
    }

    @Override
    public List<Check> findAll() {
        return null;
    }

    @Override
    public void update(Check entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }

    @Override
    public Check findByAccountId(Integer id) {
        final String query = QueryManager.getProperty("check.findNewById");
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CheckMapper().extractFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAllProductsFromCheck(Integer check_id) {
        List<Product> productList = new CopyOnWriteArrayList<>();
        final String query = QueryManager.getProperty("check.findProductsFromCheck");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, check_id);
            ResultSet resultSet = statement.executeQuery();
            return getProducts(productList, resultSet);
        } catch (SQLException ex) {
            log.error("SQLException: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean addProductToCheckIfItDoesntHave(Integer ch_id, Integer pr_id, Integer amount) {
        final String query = QueryManager.getProperty("check.addProductToCheck");
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ch_id);
            preparedStatement.setInt(2, pr_id);
            preparedStatement.setInt(3, amount);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            log.info("Check already has this product" + ex);
            return false;
        }
    }

    @Override
    public void changeAmountOfProduct(Integer ch_id, Integer pr_id, Integer amount) {
        final String findQuery = QueryManager.getProperty("check.findAmountOfProductInCheck");
        final String addQuery = QueryManager.getProperty("check.changeAmountOfProductInCheck");
        int amountFromCheck;
        try (PreparedStatement statement = connection.prepareStatement(findQuery)) {
            statement.setInt(1, amount);
            statement.setInt(2, ch_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                amountFromCheck = resultSet.getInt("amount");
            } else throw new SQLException("There is no such product");
            try (PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {
                preparedStatement.setInt(1, amountFromCheck + amount);
                preparedStatement.setInt(2, ch_id);
                preparedStatement.setInt(3, pr_id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            log.error("Smth went wrong" + ex);
        }
    }

    @Override
    public void updateTotalPrice(Integer check_id) {
        final String query = QueryManager.getProperty("check.updateTotalPrice");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, check_id);
            statement.setInt(2, check_id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            log.error("Price update was nor successful with check id:" + check_id);
        }
    }

    @Override
    public void deleteProductFromCheck(Integer check_id, Integer product_id) {
        final String query = QueryManager.getProperty("check.deleteProductFromCheck");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, check_id);
            statement.setInt(2, product_id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            log.error("Unable to delete product from check" + ex);
        }
    }

    private List<Product> getProducts(List<Product> productList, ResultSet resultSet) throws SQLException {
        ProductMapper productMapper = new ProductMapper();
        while (resultSet.next()) {
            Product product = productMapper.extractFromResultSet(resultSet);
            productList.add(product);
        }
        return new ArrayList<>(productList);
    }


}
