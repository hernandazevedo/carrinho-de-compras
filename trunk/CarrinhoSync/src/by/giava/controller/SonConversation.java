package by.giava.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.model.Father;
import by.giava.model.Son;
import by.giava.repository.FatherRepository;
import by.giava.repository.SonRepository;

@Named
@ConversationScoped
public class SonConversation implements Serializable {

	public static final String LIST = "/listFathers.xhtml";
	private static final String MOD = "/addModSon.xhtml";

	private Long idModify;
	
	public Long getIdModify() {
		return idModify;
	}
	public void setIdModify(Long idModify) {
		this.idModify = idModify;
	}
	
	@Inject
	SonRepository sonRepository;

	@Inject
	FatherConversation fatherConversation;

	private Son son;
	private DataModel<Son> model;

	public SonConversation() {
		System.out.println("FatherConversation start");
	}

	public Son getSon() {
		return son;
	}

	public void setSon(Son son) {
		this.son = son;
	}

	public DataModel<Son> getModel() {
		if (model == null) {
			List<Son> list = sonRepository.getList();
			this.model = new ListDataModel<Son>(list);
		}
		return model;
	}

	public void setModel(DataModel<Son> model) {
		this.model = model;
	}

	public String modifyById() {
		
		return modify(getIdModify());
	}
	
	public String modify(Long id) {
		System.out.println("ID SON: " + id);
		this.son = sonRepository.find(id);
		if (this.son == null)
			System.out.println("son not found");
		return MOD;
	}

	public String add() {
		this.son = new Son();
		this.son.setFather(fatherConversation.getFather());
		fatherConversation.getFather().getSons().add(this.son);
		return MOD;
	}

	public String save() {
		sonRepository.save(son);
		return LIST;
	}

	public String update() {
		sonRepository.merge(this.son);
		fatherConversation.setModel(null);
		return LIST;
	}

	public String delete() {
		sonRepository.delete(this.son);
		return LIST;
	}

}
