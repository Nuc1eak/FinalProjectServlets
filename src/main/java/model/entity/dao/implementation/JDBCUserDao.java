package model.entity.dao.implementation;

import model.entity.User;
import model.entity.dao.UserDao;
import model.entity.dao.mappers.UserMapper;
import model.exceptions.InvalidInputException;
import model.exceptions.UserAlreadyExistException;
import model.exceptions.UserNotFoundException;
import util.QueryManager;

import java.sql.*;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private final Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findByLogin(String login) {
        final String query = QueryManager.getProperty("user.findByLogin");
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, login);
            return getUser(ps);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws UserNotFoundException {
        final String query = QueryManager.getProperty("user.findByLoginAndPassword");
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, login);
            st.setString(2, password);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return getUser(st);
            } else {
                throw new InvalidInputException("Invalid name or password");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public User create(User entity) {
        final String insertUserInDatabase = QueryManager.getProperty("user.create.insertUser");
        try {
            connection.setAutoCommit(false);
            PreparedStatement insertUserToTable = connection.prepareStatement(insertUserInDatabase, Statement.RETURN_GENERATED_KEYS);

            insertUserToTable.setString(1, entity.getLogin());
            insertUserToTable.setString(2, entity.getPassword());
            insertUserToTable.setString(3, entity.getFirstName());
            insertUserToTable.setString(4, entity.getSecondName());
            insertUserToTable.setString(5, entity.getRole().toString());

            insertUserToTable.executeUpdate();
            insertUserToTable.executeBatch();
            insertUserToTable.close();

            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            throw new UserAlreadyExistException("register.userExist");
        }
        return entity;
    }

    @Override
    public User findById(int id) {
        final String query = QueryManager.getProperty("user.findById");
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setInt(1, id);
            return getUser(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }

    private User getUser(PreparedStatement st) throws SQLException {
        User findingUser = new User();
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            findingUser = new UserMapper().extractFromResultSet(rs);
        }
        return findingUser;
    }
}
