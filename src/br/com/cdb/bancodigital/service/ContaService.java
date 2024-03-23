package br.com.cdb.bancodigital.service;


import br.com.cdb.bancodigital.entity.Conta;

import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.dao.ContaDAO;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;
import br.com.cdb.bancodigital.entity.Cliente.TipoCliente;
import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Seguro;
import br.com.cdb.bancodigital.entity.SeguroViagem;
import br.com.cdb.bancodigital.entity.SeguroFraude;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.Scanner;

public class ContaService {
	private ContaDAO contaDAO;
	
	public ContaService(ContaDAO contaDAO) {
		this.contaDAO = contaDAO;
	}
	
	public void depositar(Conta conta, double valor) {
		conta.depositar(valor);		
	}
	
	public void sacar(Conta conta, double valor) {
		conta.sacar(valor);
	}
	
	public void transferir(Conta origem, Conta destino, double valor) {
		origem.sacar(valor);
		destino.depositar(valor);
	}
	
	public void exibirSaldo(Conta conta) {
		System.out.println("Saldo da conta: R$ " + conta.getSaldo());
	}
	
	public void exibirExtrato(Conta conta) {
		double saldo = conta.getSaldo();
		conta.exibirExtrato();
		System.out.println("Saldo: R$ " + saldo);
	}

	public ContaCorrente criarContaCorrente(String cpf, Cliente titular, double saldoInicial) {
		ContaCorrente conta = new ContaCorrente(cpf, titular, saldoInicial);
		titular.setConta(conta);
		contaDAO.salvar(conta);
		return conta;
		
	}
	
	public ContaPoupanca criarContaPoupanca(String cpf, Cliente titular, double saldoInicial) {
		ContaPoupanca conta = new ContaPoupanca(cpf, titular, saldoInicial);
		titular.setConta(conta);
		contaDAO.salvar(conta);
		return conta;
		
	}
	
	public Conta buscarContaPorNumero(String numeroConta) {
		return contaDAO.buscar(numeroConta);
	}
	
	public void exibiDadosConta(Conta conta) {
		System.out.println("Dados da conta:");
		System.out.println("Número: " + conta.getNumeroConta());
		System.out.println("Titular: " + conta.getTitular().getNome());
		System.out.println((conta instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança"));
		System.out.println("Saldo atual: " + conta.getSaldo());
	}
	
	public CartaoCredito emitirCartaoCredito(Conta conta) {
		
		if(conta.getCartaoCredito() != null) {
			System.out.println("Essa conta já possui um cartão de crédito vinculado.");
			return null;
		}
		
		Cliente titular = conta.getTitular();
		double limite;
		TipoCliente tipoCliente = titular.getTipo();
		
		switch (tipoCliente) {
			case COMUM:
				limite = 1000.0;
				break;
			case SUPER:
				limite = 5000.0;
				break;
			case PREMIUM:
				limite = 10000.0;
				break;
			default:
				limite = 0.0;
		}
		
		CartaoCredito cartaoCredito = new CartaoCredito(conta, limite);
		conta.setCartaoCredito(cartaoCredito);
		
		if(titular.getTipo() == TipoCliente.PREMIUM) {
			cartaoCredito.setSeguro(new SeguroViagem());
		} else {
			cartaoCredito.setSeguro(new SeguroFraude());
		}
				
		return cartaoCredito;
	}
	
	private final String gerarNumeroCartao() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	public CartaoDebito emitirCartaoDebito(Conta conta, double limiteDiario) {
		
		if (conta.getCartaoDebito() != null) {
			System.out.println("Essa conta já possui um cartão de débito vinculado.");
			return null;
		}
		
		String numeroCartaoDebito = gerarNumeroCartao();
		
		Scanner input = new Scanner(System.in);
		
		CartaoDebito cartaoDebito = new CartaoDebito(numeroCartaoDebito, conta);
		conta.setCartaoDebito(cartaoDebito);
		
		return cartaoDebito;
	}
	
	public boolean realizarPagamentoCartaoCredito (CartaoCredito cartaoCredito, double valor) {
		if (cartaoCredito == null) {
			System.out.println("Cartão de crédito não encontrado.");
			return false;
		}
		
		double limiteDisponivel = cartaoCredito.getLimite() - cartaoCredito.getFaturaAtual();
		double taxaUso = cartaoCredito.calcularTaxaUso();
		
		if(valor > limiteDisponivel + taxaUso) {
			System.out.println("Transação não realizada. Limite do cartão excedido");
			return false;
		}
		
		cartaoCredito.aumentarFatura(valor);
		System.out.println("Pagamento realizado com sucesso. Valor R$ " + valor);
		return true;
	}
	
	public void alterarSenhaCartaoCredito (CartaoCredito cartaoCredito, String novaSenha) {
		if (cartaoCredito == null) {
			System.out.println("Cartão de crédito não localizado");
			return;
		}
		
		cartaoCredito.setSenha(novaSenha);
		System.out.println("Senha alterada com sucesso.");
	}
	
	public boolean realizarPagamentoCartaoDebito(CartaoDebito cartaoDebito, double valor) {
		if(cartaoDebito == null) {
			System.out.println("Cartão de débito não encontrado.");
			return false;
		}
		
		Conta conta = cartaoDebito.getConta();
		
		if(conta == null) {
			System.out.println("Conta associada ao cartão de débito não encontrada.");
			return false;
		}
		
		if(!cartaoDebito.isAtivo()) {
			System.out.println("Cartão de débito desativado.");
			return false;
		}
		
		if (valor > conta.getSaldo()) {
			System.out.println("Transação não realizada. Saldo insuficiente.");
			return false;
		}
		
		if(valor > cartaoDebito.getLimiteDiario()) {
			System.out.println("Transação não efetuada. Valor superior ao limite diário.");
			return false;
		}
		
		cartaoDebito.realizarPagamento(valor);
		System.out.println("Pagamento de R$ " + valor + " realizado com sucesso via cartão de débito");
		return true;
		
	}
	
		
	
}
