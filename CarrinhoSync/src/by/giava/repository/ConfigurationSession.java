package by.giava.repository;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.transaction.TransactionPropagation;
import org.jboss.seam.transaction.Transactional;

import by.giava.model.Configuration;

@Named
@ConversationScoped
public class ConfigurationSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	public ConfigurationSession() {
		System.out.println("start configurationSession");
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public Configuration readConfiguration() {
		Configuration c = null;
		try {
			c = getEm().find(Configuration.class, 1L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c == null) {
			c = new Configuration();
			getEm().persist(c);
		}
		return c;
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public Configuration merge(Configuration configuration) {
		try {
			return em.merge(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
