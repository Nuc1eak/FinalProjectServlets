package model.service;

import model.entity.Product;
import model.entity.dao.DaoFactory;
import model.entity.dao.ProductDao;
import model.exceptions.ProductAlreadyExistException;

import java.util.List;
import java.util.Optional;

public class ExpertService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Product> getAllProducts() {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return productDao.findAll();
        }
    }

    public boolean ifProductExist(String name, Integer code) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            Product product = productDao.findByNameAndCode(name, code);
            return product != null;
        } catch (ProductAlreadyExistException ex) {
            return false;
        }
    }

    public Product findProductByCode(Integer code) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return Optional.ofNullable(productDao.findByCode(code)).orElse(new Product());
        }
    }

    public Product findProductByName(String name) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return Optional.ofNullable(productDao.findByName(name)).orElse(new Product());
        }
    }

    public void createNewProduct(Product product) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            productDao.create(product);
        }
    }

    public void updateAmountOfProduct(Integer amount, Integer code) {
        try (ProductDao productDao = daoFactory.createProductDao()) {
            productDao.updateByCode(amount, code);
        }
    }
}
