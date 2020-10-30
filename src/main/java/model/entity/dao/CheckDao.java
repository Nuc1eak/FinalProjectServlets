package model.entity.dao;

import model.entity.Check;
import model.entity.Product;

import java.util.List;

public interface CheckDao extends GenericDao<Check> {
    Check findByAccountId(Integer id);
    List<Product> findAllProductsFromCheck(Integer check_id);
    boolean addProductToCheckIfItDoesntHave(Integer ch_id, Integer pr_id, Integer amount);
    void changeAmountOfProduct(Integer ch_id, Integer pr_id, Integer amount);
    void updateTotalPrice(Integer check_id);
    void deleteProductFromCheck(Integer check_id, Integer product_id);
}
