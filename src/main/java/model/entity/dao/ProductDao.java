package model.entity.dao;

import model.entity.Product;

public interface ProductDao extends GenericDao<Product> {
    Product findByNameAndCode(String name, Integer code);
    void updateByCode(Integer amount, Integer code);
    Product findByCode(Integer code);
    Product findByName(String name);
}
