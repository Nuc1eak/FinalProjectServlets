package controller.commands;

import model.entity.Product;
import model.service.CashierService;
import model.service.ExpertService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CashierAddProductCommand implements Command {

    ExpertService expertService = new ExpertService();
    CashierService cashierService = new CashierService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Product productToAdd = findProductInDatabase(request);
        int check_id = Integer.parseInt(request.getParameter("check_id"));
        if (productToAdd.getId() == 0) {
            exceptionInForm(request, "Wrong name or code");
        } else if (checkAmountOfProduct(request, Integer.parseInt(productToAdd.getAmount()))){
            exceptionInForm(request, "Wrong amount or there is less in storage");
        } else {
            cashierService.addProductToCheck(
                    check_id,
                    productToAdd.getId(),
                    Integer.parseInt(request.getParameter("product_amount")));
        }

        return "redirect: /app/cashier";
    }

    private Product findProductInDatabase(HttpServletRequest request) {
        String nameOrCode = request.getParameter("product_code_or_name");
        return nameOrCode.matches("[0-9]{4}")
                ? expertService.findProductByCode(Integer.valueOf(nameOrCode))
                : expertService.findProductByName(nameOrCode);
    }

    private void exceptionInForm(HttpServletRequest request, String message) {
        request.setAttribute("exceptionInForm", message);
    }

    private boolean checkAmountOfProduct(HttpServletRequest request, int amountOfProduct) {
        String amountFromSite = request.getParameter("product_amount");
        return amountFromSite.matches("[0-9]{1,3}") && amountOfProduct < Integer.parseInt(amountFromSite);
    }
}
