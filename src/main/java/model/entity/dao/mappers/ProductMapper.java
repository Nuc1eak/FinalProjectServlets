package model.entity.dao.mappers;

import model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ProductMapper implements ObjectMapper<Product>{
    @Override
    public Product extractFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("product_id"));
        product.setCode(resultSet.getString("product_code"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getString("price"));
        product.setAmount(resultSet.getString("amount"));
        product.setMeasure(Product.Measure.valueOf(resultSet.getString("measure")));
        return product;
    }

    @Override
    public Product makeUnique(Map<Integer, Product> cache, Product user) {
        return null;
    }
}
