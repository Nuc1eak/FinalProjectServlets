package controller.commands;

import model.service.ExpertService;
import util.ThreadLocalWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ExpertChangeProductAmountCommand implements Command{

    ExpertService expertService = new ExpertService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer product_code = Integer.parseInt(request.getParameter("changed_code"));
        String changed_amount_str = request.getParameter("change_amount");

        if (!validateAmount(request, changed_amount_str)) {
            return "redirect: /app/expert";
        }

        Integer amount = Integer.valueOf(changed_amount_str);

        expertService.updateAmountOfProduct(amount, product_code);

        return "redirect: /app/expert";
    }

    private boolean validateAmount(HttpServletRequest request, String amount) {
        ResourceBundle regexBundle = ResourceBundle.getBundle("regex");
        Pattern amountPattern = Pattern.compile(regexBundle.getString("product.amount"));

        Locale locale = ThreadLocalWrapper.getLocale();
        ResourceBundle errorsBundle = ResourceBundle.getBundle("errors", locale);

        if (!amountPattern.matcher(amount).matches()) {
            request.setAttribute("wrongChangedAmount", errorsBundle.getString("product.amount"));
            return false;
        }
         return true;
    }

}
