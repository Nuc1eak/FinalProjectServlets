package controller.commands;

import model.entity.Product;
import model.service.ExpertService;
import util.ThreadLocalWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ExpertAddProductCommand implements Command {
    ExpertService service = new ExpertService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Product product = createProduct(request);

        if (!validateProduct(request, product)) {
            return "redirect: /app/expert";
        }
        if (service.ifProductExist(product.getName(), Integer.parseInt(product.getCode()))) {
            return informAboutWrongInput(request);
        }
        service.createNewProduct(product);

        return "redirect: /app/expert";
    }

    private Product createProduct(HttpServletRequest request) {
        Product product = new Product();
        product.setName(request.getParameter("product_name"));
        product.setCode(request.getParameter("product_code"));
        product.setAmount(request.getParameter("product_amount"));
        product.setMeasure(Product.Measure.valueOf(request.getParameter("product_measure")));
        product.setPrice(request.getParameter("product_price"));
        return product;
    }

    private boolean validateProduct(HttpServletRequest request, Product product) {
        boolean isValid = true;
        ResourceBundle regexBundle = ResourceBundle.getBundle("regex");
        Pattern namePattern = Pattern.compile(regexBundle.getString("product.name"));
        Pattern codePattern = Pattern.compile(regexBundle.getString("product.code"));
        Pattern amountPattern = Pattern.compile(regexBundle.getString("product.amount"));
        Pattern pricePattern = Pattern.compile(regexBundle.getString("product.price"));

        Locale locale = ThreadLocalWrapper.getLocale();
        ResourceBundle errorsBundle = ResourceBundle.getBundle("errors", locale);

        if (!namePattern.matcher(product.getName()).matches()) {
            request.setAttribute("wrongName", errorsBundle.getString("product.name"));
            isValid = false;
        }
        if (!codePattern.matcher(product.getCode()).matches()) {
            request.setAttribute("wrongCode", errorsBundle.getString("product.code"));
            isValid = false;
        }
        if (!amountPattern.matcher(product.getAmount()).matches()) {
            request.setAttribute("wrongAmount", errorsBundle.getString("product.amount"));
            isValid = false;
        }
        if (!pricePattern.matcher(product.getPrice()).matches()) {
            request.setAttribute("wrongPrice", errorsBundle.getString("product.price"));
            isValid = false;
        }
        return isValid;
    }

    private String informAboutWrongInput(HttpServletRequest request) {
        request.setAttribute("productExist", "This product already exist in system. Please pick other name or code.");
        return "redirect: /app/expert";
    }
}
