package model.entity.dao;

import model.entity.User;
import model.exceptions.UserNotFoundException;

public interface UserDao extends GenericDao<User> {
    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password) throws UserNotFoundException;
}
