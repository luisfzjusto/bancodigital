package br.com.cdb.bancodigital.entity;

public interface Seguro {
	public abstract double calcularCustoMensal();
	public abstract String gerarApolice(Cliente cliente, CartaoCredito cartao);

}
