package com.wizard.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class User implements Serializable
{
	private String firstName, middleName, lastName, phone, mobile, email;
	private Date birthDate;
	private Address address;
	
	public User()
	{
		address = new Address();
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getMobile()
	{
		return mobile;
	}

	public String getEmail()
	{
		return email;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}
}