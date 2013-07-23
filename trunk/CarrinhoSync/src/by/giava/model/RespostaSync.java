package by.giava.model;

import java.io.Serializable;
import java.util.List;

public class RespostaSync implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer codigoStatus;
	private String messageStatus;
	
	List<Track> lista;

	public Integer getCodigoStatus() {
		return codigoStatus;
	}

	public void setCodigoStatus(Integer codigoStatus) {
		this.codigoStatus = codigoStatus;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public List<Track> getLista() {
		return lista;
	}

	public void setLista(List<Track> lista) {
		this.lista = lista;
	}
	
	

}
