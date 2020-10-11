package model.service;

import model.entity.User;
import model.entity.dao.DaoFactory;
import model.entity.dao.UserDao;
import model.exceptions.UserAlreadyExistException;

public class RegisterService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    public boolean ifUserExist(String login) {
        try(UserDao userDao = daoFactory.createUserDao()) {
            User userByLogin = userDao.findByLogin(login);
            return userByLogin.getLogin() != null;
        } catch (UserAlreadyExistException ex) {
            return false;
        }
    }

    public User registerNewUser(User user) {
        try(UserDao userDao = daoFactory.createUserDao()) {
            return userDao.create(user);
        }
    }
}
