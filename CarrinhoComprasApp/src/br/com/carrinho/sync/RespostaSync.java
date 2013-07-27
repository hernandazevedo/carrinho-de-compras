package br.com.carrinho.sync;

import java.io.Serializable;
import java.util.List;

import br.com.carrinho.model.Parceiro;
import br.com.carrinho.model.Produto;


public class RespostaSync implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer codigoStatus;
	private String messageStatus;
	
	List<Produto> listaProduto;
	List<Parceiro> listaParceiro;

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

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public List<Parceiro> getListaParceiro() {
		return listaParceiro;
	}

	public void setListaParceiro(List<Parceiro> listaParceiro) {
		this.listaParceiro = listaParceiro;
	}
	

}

