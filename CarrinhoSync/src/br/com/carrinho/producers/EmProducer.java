package br.com.carrinho.producers;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.solder.core.ExtensionManaged;
@ApplicationScoped
public class EmProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExtensionManaged
	@Produces
	@PersistenceUnit(unitName = "pu")
	@RequestScoped
	EntityManagerFactory em;


}
