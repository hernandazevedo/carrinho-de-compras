package br.giava.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;


public class BootstrapListener   implements javax.servlet.ServletContextListener , 
													javax.servlet.ServletRequestListener ,  
													javax.servlet.http.HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//TODO implementar a configuracao do sistema no boostrap do servidor de aplicacao.
		System.out.println("Listener contextInitialized");
		
		
		
	}


}
