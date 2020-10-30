package model.service;

import model.entity.Check;
import model.entity.Product;
import model.entity.dao.CheckDao;
import model.entity.dao.DaoFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CashierService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    public Check getCheckByAccountId(Integer id) {
        try (CheckDao checkDao = daoFactory.createCheckDao()) {
            return Optional.ofNullable(checkDao.findByAccountId(id)).orElse(new Check());
        }
    }

    public Check createNewCheck(Check check) {
        try (CheckDao checkDao = daoFactory.createCheckDao()) {
            return checkDao.create(check);
        }
    }

    public List<Product> getAllProductsFromCheckId(Integer id) {
        try (CheckDao checkDao = daoFactory.createCheckDao()) {
            return Optional.ofNullable(checkDao.findAllProductsFromCheck(id)).orElse(new ArrayList<>());
        }
    }

    public void addProductToCheck(Integer check_id, Integer product_id, Integer amount) {
        try (CheckDao checkDao = daoFactory.createCheckDao()) {
            if (!checkDao.addProductToCheckIfItDoesntHave(check_id, product_id, amount)) {
                checkDao.changeAmountOfProduct(check_id, product_id, amount);
            }
        }
    }

    public void updateTotalPrice(Integer check_id) {
        try (CheckDao checkDao = daoFactory.createCheckDao()) {
            checkDao.updateTotalPrice(check_id);
        }
    }

    public void deleteProductFromCheck(Integer check_id, Integer product_id) {
        try (CheckDao checkDao = daoFactory.createCheckDao()) {
            checkDao.deleteProductFromCheck(check_id, product_id);
        }
    }
}
