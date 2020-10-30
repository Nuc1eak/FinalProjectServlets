package controller.commands;

import model.service.CashierService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CashierDeleteProductCommand implements Command {

    CashierService service = new CashierService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int check_id = Integer.parseInt(request.getParameter("check_id"));
        int product_id = Integer.parseInt(request.getParameter("product_delete_id"));
        service.deleteProductFromCheck(check_id, product_id);
        return "redirect: /app/cashier";
    }
}
