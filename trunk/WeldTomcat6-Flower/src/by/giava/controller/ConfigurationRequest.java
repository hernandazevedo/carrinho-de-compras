package by.giava.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.model.Configuration;
import by.giava.repository.ConfigurationSession;

@Named
@ConversationScoped
public class ConfigurationRequest implements Serializable {

	private Configuration configuration;

	@Inject
	private Conversation conversation;

	public ConfigurationRequest() {
		System.out.println("configurationRequest start");
	}

	@Inject
	ConfigurationSession configurationSession;

	public void readConfiguration() {
		this.configuration = configurationSession.readConfiguration();
	}

	public Configuration getConfiguration() {
		if (this.configuration == null)
			readConfiguration();
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String startConversation() {
		conversation.begin();
		return "";
	}

	public String stopConversation() {
		conversation.end();
		return "";
	}
}
