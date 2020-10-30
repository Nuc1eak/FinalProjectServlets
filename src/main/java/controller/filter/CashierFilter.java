package controller.filter;

import model.entity.User;
import model.exceptions.PageAccessException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = "/app/cashier/*")
public class CashierFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) servletRequest).getSession();

        User.ROLE role = getUserRole(httpSession);

        if (!checkPermission(role)) {
            throw new PageAccessException("You don`t have permission to visit expert page");
        }

        chain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    /**
     * @param role user role that try to access to some page
     * @return true if user is allowed to visit page
     */
    private boolean checkPermission(User.ROLE role) {
        return role.toString().equals("cashier") | role.toString().equals("superCashier");
    }

    private User.ROLE getUserRole(HttpSession session) {
        return session.getAttribute("user") != null ?
                ((User) session.getAttribute("user")).getRole() : User.ROLE.guest;
    }
}
