package br.com.carrinho.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(schema="masterkey",name="produto")
@Entity
public class Produto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_barras")
	private String codigoBarras;
	
	@Column(name="nome_produto")
	private String nome;
	
	@Column(name="url_image")
	private String urlImagem;
	
	@Column(name="preco")
	private Double preco;
	
	@Column(name="parceiro_id")
	private Long parceiroId;
	
	@Column(name="data_alteracao")
	private Date dataAlteracao;
	
	@Column(name="data_criacao")
	private Date dataCriacao;

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Long getParceiroId() {
		return parceiroId;
	}

	public void setParceiroId(Long parceiroId) {
		this.parceiroId = parceiroId;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
	
}
