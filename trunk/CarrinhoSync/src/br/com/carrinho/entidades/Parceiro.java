package br.com.carrinho.entidades;

import java.io.Serializable;

public class Parceiro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer parceiroId;
	private String nome;
	private String receiverPaypal;
	public Integer getParceiroId() {
		return parceiroId;
	}
	public void setParceiroId(Integer parceiroId) {
		this.parceiroId = parceiroId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getReceiverPaypal() {
		return receiverPaypal;
	}
	public void setReceiverPaypal(String receiverPaypal) {
		this.receiverPaypal = receiverPaypal;
	}
	
	

}
