package controller;

import controller.commands.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@WebServlet(urlPatterns = "/app/*", loadOnStartup = 1)
public class Servlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(Servlet.class);

    private static ServletContext context;
    private static Map<String, Command> commands;

    public static ServletContext getContext() { return context; }

    @Override
    public void init(ServletConfig servletConfig) {
        context = servletConfig.getServletContext();
        commands = new ConcurrentHashMap<>();

        commands.put("index", new IndexCommand());
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("expert", new ExpertCommand());
        commands.put("expert/newProduct", new ExpertAddProductCommand());
        commands.put("expert/changeAmount", new ExpertChangeProductAmountCommand());

        log.info("commands initialised");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = getCommand(req);
        String page = null;
        try {
            page = command.execute(req, resp);
        } catch (Exception e) {
            System.err.println("Exception in Servlet");
            e.printStackTrace();
        }
        if (page != null) {
            if (page.contains("redirect: ")) {
                resp.sendRedirect(req.getContextPath() + page.replaceAll("redirect: ", ""));
                log.info("redirect :" + page);
            } else {
                req.getRequestDispatcher(page).forward(req, resp);
                log.info("forward :" + page);
            }
        }
    }

    private Command getCommand(HttpServletRequest request) {
        String path = request.getRequestURI();
        log.info("path=" + path);
        path = path.replaceAll(".*/app/", "").replaceAll(".*/app", "");
        return commands.getOrDefault(path, (r, j) -> "/app/index");
    }
}
