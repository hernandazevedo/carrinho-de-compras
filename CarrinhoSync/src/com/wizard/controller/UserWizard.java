package com.wizard.controller;

import java.io.Serializable;
import java.util.*;

import javax.enterprise.context.ConversationScoped;
import javax.inject.*;
import com.wizard.model.User;
import com.wizard.outcome.Outcome;
import com.wizard.service.UserService;
import com.wizard.viewmanager.View;

@Named("wizard")
@ConversationScoped
public class UserWizard extends AbstractWizard implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;
	
	private User user = new User();

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	protected List<View> prepareViews()
	{
		List<View> views = new ArrayList<View>();
		views.add(new View("First step", Outcome.FIRST_STEP));
		views.add(new View("Second step", Outcome.SECOND_STEP));
		views.add(new View("Third step", Outcome.THIRD_STEP));
		views.add(new View("Summary", Outcome.SUMMARY));
		
		return views;
	}

	protected String complete()
	{
		userService.processUserData(user);
		
		return Outcome.WIZARD_FINISH;
	}

	protected String clean()
	{
		return Outcome.WIZARD_CANCEL;
	}
}