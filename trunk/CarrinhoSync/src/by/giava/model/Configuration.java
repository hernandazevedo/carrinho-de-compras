package by.giava.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Configurazione")
public class Configuration implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String pop;
	private String smtp;
	private String serverType;
	private int serverPort;
	private String username;
	private String password;
	private boolean withAuth;
	private boolean withSsl;
	private boolean withDebug;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPop() {
		return pop;
	}

	public void setPop(String pop) {
		this.pop = pop;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isWithAuth() {
		return withAuth;
	}

	public void setWithAuth(boolean withAuth) {
		this.withAuth = withAuth;
	}

	public boolean isWithSsl() {
		return withSsl;
	}

	public void setWithSsl(boolean withSsl) {
		this.withSsl = withSsl;
	}

	public boolean isWithDebug() {
		return withDebug;
	}

	public void setWithDebug(boolean withDebug) {
		this.withDebug = withDebug;
	}

}