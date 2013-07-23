package com.wizard.outcome;

public final class Outcome
{
	private static final String WIZARD = "/wizard/";
	
	private static final String REDIRECT = "?faces-redirect=true";
	
	public static final String FIRST_STEP = WIZARD + "firstStep" + REDIRECT;

	public static final String SECOND_STEP = WIZARD + "secondStep" + REDIRECT;
	
	public static final String THIRD_STEP = WIZARD + "thirdStep" + REDIRECT;
	
	public static final String SUMMARY = WIZARD + "summary" + REDIRECT;
	
	public static final String WIZARD_FINISH = WIZARD + "wizardFinish" + REDIRECT;
	
	public static final String WIZARD_CANCEL = WIZARD + "wizardCancel" + REDIRECT;
}