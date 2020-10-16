package controller.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class ContentListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context  = servletContextEvent.getServletContext();
        context.setAttribute("loggedUsers", new ConcurrentHashMap<Integer, HttpSession>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context  = servletContextEvent.getServletContext();
        context.removeAttribute("loggedUsers");
    }
}
