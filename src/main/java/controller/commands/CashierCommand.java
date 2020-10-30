package controller.commands;

import model.entity.Check;
import model.entity.Product;
import model.entity.User;
import model.service.CashierService;
import util.QueryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CashierCommand implements Command{

    CashierService service = new CashierService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int offset = Integer.parseInt(QueryManager.getProperty("select.limit"));
        int cashierId = getUserId(request);

        Check check = service.getCheckByAccountId(cashierId);
        if (check.getId() == 0) {
            handleDataToInsert(check, request);
            check = service.createNewCheck(check);
        }

        service.updateTotalPrice(check.getId());

        List<Product> products = service.getAllProductsFromCheckId(check.getId());

        setTotalCheckPages(request, products, offset);

        handleCheckPageNumber(request, products, offset);

        handleCheckInfo(request, check);

        return "/WEB-INF/cashier/check.jsp";
    }

    private int getUserId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.getId();
    }

    private void handleDataToInsert(Check check, HttpServletRequest request) {
        check.setTotalPrice(0);
        check.setLocalDate(LocalDate.now());
        check.setStatus(Check.Status.newMade);
        check.setCashier((User) request.getSession().getAttribute("user"));
    }

    private void handleCheckPageNumber(HttpServletRequest request, List<Product> productList, int offset) {
        int page = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("1"));
        int start = (page - 1) * offset;
        int end = Math.min(start + offset, productList.size());
        request.setAttribute("productsFromCheck", productList.subList(start, end));
        request.setAttribute("currentCheckPage", page);
    }

    private void handleCheckInfo(HttpServletRequest request, Check check) {
        double totalPrice = check.getTotalPrice();
        request.setAttribute("totalPrice", totalPrice / 100);
        request.setAttribute("checkNumber", check.getId());
    }

    private void setTotalCheckPages(HttpServletRequest request, List<Product> productList, int offset) {
        int totalPages = (int) Math.ceil((float) productList.size()/offset);
        request.setAttribute("totalCheckPages", totalPages);
    }



}
