package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.Cliente.TipoCliente;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.dao.ContaDAO;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;
import br.com.cdb.bancodigital.entity.Cliente;

public class SeguroViagem implements Seguro {

	private static final double custoMensal = 50.0;
	private static final String tipo = "Seguro Viagem";
	
	@Override
	public double calcularCustoMensal() {
		return custoMensal;
	}

	@Override
	public String gerarApolice(Cliente cliente, CartaoCredito cartao) {
		String nomeCliente = cliente.getNome();
		String numeroCartao = cartao.getNumero();
		Conta conta = cartao.getConta();
		
		StringBuilder apoliceViagem = new StringBuilder(); 
		apoliceViagem.append("Apólice do Seguro Viagem:\n");
		apoliceViagem.append("Cliente: ").append(nomeCliente).append("\n");
		apoliceViagem.append("Número do Cartão de Crédito: ").append(numeroCartao).append("\n");
		apoliceViagem.append("Conta Associada: ").append(conta.getNumeroConta()).append("\n");
		apoliceViagem.append("Esse seguro oferece cobertura de até R$ 50.000,00 em gastos com despesas médicas no exterior.");
		
		if(cliente.getTipo() == TipoCliente.PREMIUM) {
			apoliceViagem.append("Cobertura gratuita para clientes PREMIUM\n");
		} else {
			apoliceViagem.append("Cobertura opcional. Custo mensal de R$ 50,00.\n");
		}
		
		return apoliceViagem.toString();
	}

}
