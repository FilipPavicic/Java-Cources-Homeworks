package hr.fer.oprpp2.hw04.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.oprpp2.hw04.Kljucevi;

@WebListener
public class PromatracWebAplikacije implements ServletContextListener {

	public PromatracWebAplikacije() {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute(Kljucevi.RUNNING_TIME, System.currentTimeMillis());
		sce.getServletContext().log("Aplikacija za pogađanje je inicijalizirana.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute(Kljucevi.RUNNING_TIME);
		sce.getServletContext().log("Aplikacija za pogađanje završava s radom.");
	}
	
}
