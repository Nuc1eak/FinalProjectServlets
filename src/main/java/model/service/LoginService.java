package model.service;

import model.entity.User;
import model.entity.dao.DaoFactory;
import model.entity.dao.UserDao;

import java.util.Optional;

public class LoginService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    public Optional<User> validateUser(String login, String password) {
        try(UserDao userDao = daoFactory.createUserDao()) {
            return Optional.ofNullable(userDao.findByLoginAndPassword(login, password));
        }
    }
}
