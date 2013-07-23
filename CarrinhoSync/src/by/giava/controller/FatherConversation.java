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
import by.giava.repository.FatherRepository;

@Named
@ConversationScoped
public class FatherConversation implements Serializable {

	private static final String VIEW = "/viewFather.xhtml";
	public static final String LIST = "/listFathers.xhtml";
	private static final String MOD = "/addModFather.xhtml";

	@Inject
	private Conversation conversation;

	@Inject
	FatherRepository fatherRepository;

	private Father father;
	private DataModel<Father> model;

	public FatherConversation() {
		System.out.println("FatherConversation start");
	}

	public String startConversation() {
		System.out.println("START CONVERSATION");
		conversation.begin();
		return LIST;
	}

	public String stopConversation() {
		System.out.println("STOP CONVERSATION");
		conversation.end();
		return LIST;
	}

	public Father getFather() {
		return father;
	}

	public void setFather(Father father) {
		this.father = father;
	}

	public DataModel<Father> getModel() {
		if (model == null) {
			List<Father> list = fatherRepository.getList();
			this.model = new ListDataModel<Father>(list);
		}
		return model;
	}

	public void setModel(DataModel<Father> model) {
		this.model = model;
	}

	public String view() {
		this.father = getModel().getRowData();
		return VIEW;
	}

	public String modify() {
		this.father = getModel().getRowData();
		return MOD;
	}

	public String add() {
		this.father = new Father();
		return MOD;
	}

	public String save() {
		fatherRepository.save(father);
		this.model = null;
		return LIST;
	}

	public String update() {
		fatherRepository.merge(father);
		this.model = null;
		return LIST;
	}

	public String delete() {
		fatherRepository.delete(father);
		this.model = null;
		return LIST;
	}

}
