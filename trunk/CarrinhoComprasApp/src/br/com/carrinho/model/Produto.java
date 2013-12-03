package br.com.carrinho.model;

import java.io.Serializable;

public class Produto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int imageID;
	private String codigoBarras;
	private String nomeProduto;
	private String urlImage;
	private String preco;
	private Integer parceiroId;
	private Integer quantidade = 1; //default
	
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public String getPreco() {
		return preco;
	}
	public void setPreco(String preco) {
		this.preco = preco;
	}
	public Integer getParceiroId() {
		return parceiroId;
	}
	public void setParceiroId(Integer parceiroId) {
		this.parceiroId = parceiroId;
	}
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
