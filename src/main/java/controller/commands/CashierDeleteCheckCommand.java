package controller.commands;

import model.service.CashierService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CashierDeleteCheckCommand implements Command {

    CashierService service = new CashierService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int check_id = Integer.parseInt(request.getParameter("check_id"));
        return "redirect: /app/cashier";
    }
}
