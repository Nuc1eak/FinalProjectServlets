package controller.commands;

import model.entity.User;
import model.service.RegisterService;
import util.ThreadLocalWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterCommand implements Command {
    RegisterService service = new RegisterService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = createUser(request);

        if (!validateFields(request, user)){
            return "/register.jsp";
        }
        if (service.ifUserExist(user.getLogin())){
            informAboutWrongInput(request);
        } else {
            user = service.registerNewUser(user);
            setUserToSession(request, user);
            return getRedirectPath(user.getRole());
        }

        return null;
    }

    private User createUser(HttpServletRequest request) {
        User user = new User();
        user.setFirstName(request.getParameter("first_name"));
        user.setSecondName(request.getParameter("second_name"));
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("confirm_password"));
        user.setCode(request.getParameter("cashier_code"));
        user.setRole(User.ROLE.valueOf(request.getParameter("role")));
        return user;
    }

    private boolean validateFields(HttpServletRequest request, User user) {
        boolean flag = true;
        ResourceBundle regexBundle = ResourceBundle.getBundle("regex");
        Pattern firstNamePattern = Pattern.compile(regexBundle.getString("register.firstName"));
        Pattern secondNamePattern = Pattern.compile(regexBundle.getString("register.secondName"));
        Pattern loginPattern = Pattern.compile(regexBundle.getString("register.login"));
        Pattern passwordPattern = Pattern.compile(regexBundle.getString("register.password"));
        Pattern codePattern = Pattern.compile(regexBundle.getString("register.code"));

        Locale locale = ThreadLocalWrapper.getLocale();
        ResourceBundle errorsBundle = ResourceBundle.getBundle("errors", locale);
        if (!firstNamePattern.matcher(user.getFirstName()).matches()) {
            request.setAttribute("wrongFirstName", errorsBundle.getString("register.firstName"));
            flag = false;
        }
        if (!secondNamePattern.matcher(user.getSecondName()).matches()) {
            request.setAttribute("wrongSecondName", errorsBundle.getString("register.secondName"));
            flag = false;
        }
        if (!loginPattern.matcher(user.getLogin()).matches()) {
            request.setAttribute("wrongLogin", errorsBundle.getString("register.login"));
            flag = false;
        }
        if (!passwordPattern.matcher(user.getPassword()).matches()) {
            request.setAttribute("wrongPassword", errorsBundle.getString("register.password"));
            flag = false;
        }
        if (!codePattern.matcher(user.getCode()).matches()) {
            request.setAttribute("wrongCode", errorsBundle.getString("register.code"));
        }
        return flag;
    }

    private void setUserToSession(HttpServletRequest request, User user) {
        user.setPassword(null);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("role", user.getRole());
    }

    private String informAboutWrongInput(HttpServletRequest request) {
        request.setAttribute("info", "This login already exist in system. Please log in or pick other username.");
        return "/register.jsp";
    }

    private String getRedirectPath(User.ROLE role) {
        if (role == User.ROLE.cashier) {
            return "redirect: /app/cashier";
        } else if (role == User.ROLE.superCashier) {
            return "redirect: /app/super_cashier";
        } else if (role == User.ROLE.expert) {
            return "redirect: /app/expert";
        } else return "/register.jsp";
    }
}
