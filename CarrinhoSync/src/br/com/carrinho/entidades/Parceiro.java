package br.com.carrinho.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(schema="masterkey",name="parceiro")
@Entity
public class Parceiro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="parceiro_id")
	private Long parceiroId;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="receiver_paypal")
	private String receiverPaypal;
	
	@Column(name="data_alteracao")
	private Date dataAlteracao;
	
	@Column(name="data_criacao")
	private Date dataCriacao;
	
	public Long getParceiroId() {
		return parceiroId;
	}
	public void setParceiroId(Long parceiroId) {
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
