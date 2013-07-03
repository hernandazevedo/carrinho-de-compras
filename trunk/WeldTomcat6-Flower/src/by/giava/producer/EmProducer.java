package by.giava.producer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.solder.core.ExtensionManaged;

public class EmProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExtensionManaged
	@Produces
	@PersistenceUnit(unitName = "pu")
	@ConversationScoped
	EntityManagerFactory em;

	public EmProducer() {
		System.out.println("start em producer");
	}

}
