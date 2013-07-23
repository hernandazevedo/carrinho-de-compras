package com.wizard.viewmanager;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
public class ViewManager implements Serializable
{
	private static final int START_INDEX = -1;
	private static final int FIRST_INDEX = 0;
	
	private List<View> views;
	private int currentIndex, lastIndex;
	
	public void setViews(List<View> views)
	{
		this.views = views;
		
		currentIndex = START_INDEX;
		lastIndex = views.size() - 1;
	}
	
	public List<View> getViews()
	{
		return views;
	}
	
	public View getViewAt(int index)
	{
		currentIndex = index;
		
		return views.get(index);
	}

	public View getNextView()
	{
		return views.get(++currentIndex);
	}
	
	public View getPreviousView()
	{
		return views.get(--currentIndex);
	}
	
	public boolean isFirst()
	{
		return currentIndex == FIRST_INDEX;
	}
	
	public boolean isLast()
	{
		return currentIndex == lastIndex;
	}
}