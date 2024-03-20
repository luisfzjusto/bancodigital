package br.com.cdb.bancodigital.entity;

import java.util.List;


import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.dao.ContaDAO;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;
import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Cliente.TipoCliente;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Random;

public class ContaPoupanca implements Conta {
	
	private LocalDate dataCriacaoConta;
	private LocalDate dataUltimoCredito;
	private static final double taxaRendimentoComum = 0.1;
	private static final double taxaRendimentoSuper = 0.15;
	private static final double taxaRendimentoPremium = 0.20;
	private String numeroConta;
	private Cliente titular;
	private double saldo;
	private List<String> extrato;
	
	public ContaPoupanca(String numeroConta, Cliente titular, double saldoInicial) {
		this.numeroConta = gerarNumeroConta();
		this.titular = titular;
		this.saldo = saldoInicial;
		this.dataCriacaoConta = LocalDate.now();
		this.extrato = new ArrayList<>();
		registrarTransacao("Depósito inicial: R$ " + saldoInicial);
		
	}
	
	private String gerarNumeroConta() {
		Random random = new Random();
		int numeroGerado = random.nextInt(90000) + 10000;
		return String.format("%05d", numeroGerado);
	}

	@Override
	public String getNumeroConta() {
		return numeroConta;
	}

	@Override
	public Cliente getTitular() {
		return titular;
	}

	@Override
	public double getSaldo() {
		return saldo;
	}
	
	@Override
	public void transferir(Conta destino, double valor) {
		if (valor > 0 && valor <= getSaldo()) {
			if (destino instanceof ContaCorrente) {
				this.sacar(valor);
				((ContaCorrente) destino).depositar(valor);
				registrarTransacao("Transferência de R$ " + valor);
				System.out.println("Transação efetuada com sucesso.");
				System.out.println();
			} else {
				System.out.println("Transação não efetuada. Conta de destino inválida");
			}
		} else {
			System.out.println("Transação não realizada. Saldo Insuficiente.");
		}
		
	}

	@Override
	public void sacar(double valor) {
		double saldoAtual = getSaldo();
		if (valor > 0 && valor <= saldoAtual) {
			saldoAtual -= valor;
			this.saldo = saldoAtual;
			registrarTransacao("Saque de R$ " + valor);
			System.out.println("Transação efetuada com sucesso.");
			System.out.println();
		} else {
			System.out.println("Transação não efetuada. Saldo insuficiente.");
		}
	}

	@Override
	public void depositar(double valor) {
		if (valor > 0) {
			double saldoAtual = getSaldo();
			saldoAtual += valor;
			this.saldo = saldoAtual;
			registrarTransacao("Depósito de R$ " + valor);
			System.out.println("Transação efetuada com sucesso.");
			System.out.println();
		} else {
			System.out.println("Transação não efetuada. Valor inválido.");
		}
		
	}
	
	public void calcularRendimentoMensal() {
		LocalDate hoje = LocalDate.now();
		if (dataUltimoCredito == null || hoje.minusDays(30).isAfter(dataUltimoCredito)) {
			aplicarRendimento();
		}
	}
	
	private void aplicarRendimento() {
		double taxaRendimento = calcularTaxaRendimento();
		
		double rendimento = saldo * Math.pow(1 + taxaRendimento, 1.0 / 12) - saldo;
		saldo += rendimento;
		dataUltimoCredito = LocalDate.now();
	}
	
	private double calcularTaxaRendimento() {
		double taxaRendimentoAno = 0.0;
		if (getTitular().getTipo() == TipoCliente.COMUM) {
			taxaRendimentoAno = taxaRendimentoComum;
		} else if (getTitular().getTipo() == TipoCliente.SUPER) {
			taxaRendimentoAno = taxaRendimentoSuper;
		} else if (getTitular().getTipo() == TipoCliente.PREMIUM) {
			taxaRendimentoAno = taxaRendimentoPremium;
		}
		double taxaRendimentoMensal = taxaRendimentoAno / 12;
		return taxaRendimentoMensal;
	}
	
	public void exibirSaldo() {
		System.out.println("O saldo em conta é de R$ " + saldo);
	}

	@Override
	public void exibirExtrato() {
		System.out.println("***Extrato da Conta***");
		for (String transacao : extrato) {
			System.out.println(transacao);
		}
		System.out.println();
	}
	
	private void registrarTransacao(String transacao) {
		extrato.add(transacao);
	}
	
	private CartaoCredito cartaoCredito;
	private CartaoDebito cartaoDebito;

	@Override
	public void setCartaoCredito(CartaoCredito cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
		
	}

	@Override
	public void setCartaoDebito(CartaoDebito cartaoDebito) {
		this.cartaoDebito = cartaoDebito;
		
	}

	public CartaoCredito getCartaoCredito() {
		return cartaoCredito;
	}

	public CartaoDebito getCartaoDebito() {
		return cartaoDebito;
	}

	@Override
	public boolean debitar(double valor) {
		if(saldo >= valor) {
			saldo -= valor;
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
}
