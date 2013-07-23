package com.wizard.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Address implements Serializable
{
	private String street, number, postalCode, city, country;

	public String getStreet()
	{
		return street;
	}

	public String getNumber()
	{
		return number;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	public String getCity()
	{
		return city;
	}

	public String getCountry()
	{
		return country;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}
}