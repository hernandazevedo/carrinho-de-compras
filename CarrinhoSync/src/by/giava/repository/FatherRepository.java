package by.giava.repository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.transaction.TransactionPropagation;
import org.jboss.seam.transaction.Transactional;

import by.giava.model.Father;

@Named
@ConversationScoped
public class FatherRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	public FatherRepository() {
		System.out.println("start FatherRepository");
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public Father find(Long id) {
		try {
			return em.find(Father.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public Father merge(Father father) {
		try {
			return em.merge(father);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public void delete(Father father) {
		try {
			Father fatherLocal = find(father.getId());
			em.remove(fatherLocal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public void save(Father father) {
		try {
			em.persist(father);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(TransactionPropagation.REQUIRED)
	public List<Father> getList() {
		try {
			return em
					.createQuery("select c from Father c order by surname asc")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
