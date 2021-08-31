package com.gabriel.main.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gabriel.main.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {

	private static final long serialVersionUID = 1L;
	private Integer nmroParcelas;

	public PagamentoComCartao() {

	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer nmroParcelas) {
		super(id, estado, pedido);
		this.nmroParcelas = nmroParcelas;
	}

	public Integer getNmroParcelas() {
		return nmroParcelas;
	}

	public void setNmroParcelas(Integer nmroParcelas) {
		this.nmroParcelas = nmroParcelas;
	}

}
