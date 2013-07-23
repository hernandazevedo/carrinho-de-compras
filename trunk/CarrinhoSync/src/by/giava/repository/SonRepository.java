package by.giava.repository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.transaction.TransactionPropagation;
import org.jboss.seam.transaction.Transactional;

import by.giava.model.Configuration;
import by.giava.model.Father;
import by.giava.model.Son;

@Named
@ConversationScoped
public class SonRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	public SonRepository() {
		System.out.println("start SonRepository");
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public Son find(Long id) {
		try {
			return em.find(Son.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public Son merge(Son son) {
		try {
			return em.merge(son);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public void delete(Son son) {
		try {
			Son sonLoc = find(son.getId());
			em.remove(sonLoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public void save(Son son) {
		try {
			em.persist(son);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public List<Son> getList() {
		try {
			return em.createQuery("select c from Son c order by surname asc")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
