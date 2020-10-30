package controller.commands;

import model.entity.Product;
import model.service.ExpertService;
import util.QueryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ExpertCommand implements Command {
    ExpertService service = new ExpertService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int offset = Integer.parseInt(QueryManager.getProperty("select.limit"));
        try {
            List<Product> productList = service.getAllProducts();

            setTotalProductPages(request, productList, offset);

            handleProductPageNumber(request, productList, offset);

            handleErrors(request);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return "/WEB-INF/expert/storage.jsp";
    }

    private void handleErrors(HttpServletRequest request) {
        Optional.ofNullable((String)request.getSession().getAttribute("productError")).ifPresent(x-> {
            request.getSession().removeAttribute("productError");
            request.setAttribute("productError", x);
        });
    }

    private void handleProductPageNumber(HttpServletRequest request, List<Product> productList, int offset) {
        int page = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("1"));
        int start = (page - 1) * offset;
        int end = Math.min(start + offset, productList.size());
        request.setAttribute("products", productList.subList(start, end));
        request.setAttribute("currentPage", page);
    }

    private void setTotalProductPages(HttpServletRequest request, List<Product> productList, int offset) {
        int totalPages = (int) Math.ceil((float) productList.size()/offset);
        request.setAttribute("totalProductPages", totalPages);
    }

}
