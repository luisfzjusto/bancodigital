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

public class ContaCorrente implements Conta {
	
	private LocalDate dataCriacaoConta;
	private LocalDate dataUltimaDeducao;
	private static final double taxaManutencaoComum = 12.0;
	private static final double taxaManutencaoSuper = 8.0;
	private String numeroConta;
	private Cliente titular;
	private double saldo;
	private List<String> extrato;
	
	public ContaCorrente(String numeroConta, Cliente titular, double saldoInicial) {
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
		if (dataUltimaDeducao == null || LocalDate.now().minusDays(30).isAfter(dataUltimaDeducao)) {
			deduzirTaxaManutencao();
		}
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
		if (dataUltimaDeducao == null || LocalDate.now().minusDays(30).isAfter(dataUltimaDeducao)) {
			deduzirTaxaManutencao();
		}
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
		if (dataUltimaDeducao == null || LocalDate.now().minusDays(30).isAfter(dataUltimaDeducao)) {
			deduzirTaxaManutencao();
		}
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
	
	public void exibirSaldo() {
		System.out.println("O saldo em conta é de R$ " + saldo);
		System.out.println();
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
	
	private void deduzirTaxaManutencao() {
		double taxaManutencao = calcularTaxaManutencao();
		saldo -= taxaManutencao;
		dataUltimaDeducao = LocalDate.now();
	}
	
	private double calcularTaxaManutencao() {
		double taxaManutencao = 0.0;
		if (getTitular().getTipo() == TipoCliente.COMUM) {
			taxaManutencao = taxaManutencaoComum;
		} else if (getTitular().getTipo() == TipoCliente.SUPER) {
			taxaManutencao = taxaManutencaoSuper;
		} else if (getTitular().getTipo() == TipoCliente.PREMIUM) {
			taxaManutencao = 0.0;
		}
		return taxaManutencao;
	}
	
	private CartaoCredito cartaoCredito;
	private CartaoDebito cartaoDebito;

	@Override
	public CartaoDebito getCartaoDebito() {
		return cartaoDebito;
	}

	@Override
	public CartaoCredito getCartaoCredito() {
		return cartaoCredito;
	}

	@Override
	public void setCartaoCredito(CartaoCredito cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}

	@Override
	public void setCartaoDebito(CartaoDebito cartaoDebito) {
		this.cartaoDebito = cartaoDebito;
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
