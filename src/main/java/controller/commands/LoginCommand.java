package controller.commands;

import model.entity.User;
import model.exceptions.InvalidInputException;
import model.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

public class LoginCommand implements Command {
    private Security security = new Security();
    private LoginService service = new LoginService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logout(request);
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            User user = checkLoginAndPassword(request, login, password);
            return getRedirectPath(user.getRole());
        } catch (InvalidInputException ex) {
            return informAboutWrongInput(request, ex.getMessage());
        }
    }

    private User checkLoginAndPassword(HttpServletRequest request, String login, String password) {
        if (login == null || login.equals("") || password == null || password.equals("")) {
            throw new InvalidInputException("Invalid name or password");
        }
        Optional<User> user = service.validateUser(login, password);
        if (user.isPresent()) {
            logIn(request, user.get());
            return user.get();
        }
        return null;
    }

    private void logIn(HttpServletRequest request, User user) {
        Map<Integer, HttpSession> loggedUsers = security.getLoggedUsers();
        int userId = user.getId();
        destroyPreviousSession(loggedUsers, userId);
        loggedUsers.put(userId, request.getSession());
        security.setLoggedUsers(loggedUsers);
        sessionSetup(request, user);
    }

    private void logout(HttpServletRequest request) {
        Optional.ofNullable(request.getSession().getAttribute("user"))
                .ifPresent(x -> security.logOut(request.getSession()));
    }

    private void sessionSetup(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        user.setPassword(null);
        session.setAttribute("user", user);
    }

    private void destroyPreviousSession(Map<Integer, HttpSession> loggedUsers, int userId) {
        if (loggedUsers.containsKey(userId)) {
            loggedUsers.get(userId).invalidate();
        }
    }

    private String informAboutWrongInput(HttpServletRequest request, String message) {
        request.setAttribute("info", message);
        return "/login.jsp";
    }

    private String getRedirectPath(User.ROLE role) {
        if (role == User.ROLE.cashier) {
            return "redirect: /app/cashier";
        } else if (role == User.ROLE.superCashier) {
            return "redirect: /app/cashier";
        } else if (role == User.ROLE.expert) {
            return "redirect: /app/expert";
        } else return "/login.jsp";
    }
}
