package br.com.cdb.bancodigital.entity;

import java.util.List;

import br.com.cdb.bancodigital.entity.Cliente.TipoCliente;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Random;

public class CartaoCredito implements Cartao{
	private Conta conta;
	private double limite;
	private double limiteUtilizado;
	private boolean ativo;
	private String senha;
	private String numeroCartao;
	private double faturaAtual;
	private Seguro seguro;
	private Cliente titular;
	private TipoCliente tipoCliente;
	
	public CartaoCredito(Conta conta, double limite) {
		this.conta = conta;
		this.titular = conta.getTitular();
		this.tipoCliente = titular.getTipo();
		this.limite = definirLimite(tipoCliente);
		this.limiteUtilizado = 0;
		this.ativo = true;
		this.senha = "";
		this.numeroCartao = gerarNumeroCartao();
		this.faturaAtual = 0;
		this.seguro = null;
		}
	
	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}
	
	public void setSenha(String senha) {
	    this.senha = senha;
	}

	public String getSenha() {
	    return senha;
	}
	
	@Override
	public void realizarPagamento(double valor) {
		if (ativo && valor <= limite - limiteUtilizado) {
			limiteUtilizado += valor;
			System.out.println("Pagamento de R$ " + valor + " realizado com sucesso.");
			
			if (faturaAtual >= limite * 0.8) {
				double taxa = faturaAtual * 0.05;
				faturaAtual += taxa;
				System.out.println("Sua fatura foi taxada em R$ " + taxa);
			}
		} else {
			System.out.println("Pagamento não autorizado. Limite excedido ou cartão desativado.");
		}
	}

	@Override
	public void criarSenha(String senha) {
		if (this.senha.isEmpty()) {
			this.senha = senha;
			System.out.println("Senha criada com sucesso.");
		} else {
			System.out.println("Senha já foi criada. Caso deseje, selecione a opção Alterar Senha no menu.");
		}
		
	}

	@Override
	public void alterarSenha(String senha) {
		if(!this.senha.isEmpty()) {
			this.senha = senha;
			System.out.println("Senha alterada com sucesso.");
		} else {
			System.out.println("Senha ainda não foi definida. Acesse a opção Criar Senha no menu.");
		}
		
	}

	@Override
	public void ativar() {
		if(!ativo) {
			ativo = true;
			System.out.println("Cartão ativado com sucesso.");
		} else {
			System.out.println("Cartão já está liberado para utilização.");
		}
		
	}

	@Override
	public void desativar() {
		if(ativo) {
			ativo = false;
			System.out.println("Cartão desativado com sucesso.");
		} else {
			System.out.println("Cartão já foi desativado.");
		}
		
	}
	
	public boolean isAtivo() {
	    return ativo;
	}

	@Override
	public void ajustarLimite(double novoLimite) {
		limite = novoLimite;
		System.out.println("Limite do cartão alterado para R$ " + novoLimite + ".");		
	}
	
		public double getFaturaAtual() {
			return faturaAtual;
		}
	
	public void aumentarFatura(double valor) {
		this.faturaAtual += valor;
		System.out.println("Fatura atualizada. Novo valor: R$ " + this.faturaAtual);
	}
	
	public double calcularTaxaUso() {
		if (faturaAtual > limite * 0.8) {
			double taxaUso = faturaAtual * 0.05;
			System.out.println("Taxa aplicada de R$ " + taxaUso);
			return taxaUso;
		}
		System.out.println("Não foi aplicada nenhuma taxa relativa ao uso do cartão.");
		return 0.0;
	}
	
	public double getLimite() {
		return limite;
	}
	
	public String getNumero() {
        return numeroCartao;
    }
	
	private final String gerarNumeroCartao() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	@Override
	public Conta getConta() {
		return conta;
	}
	
	public double definirLimite (TipoCliente tipoCliente) {
		switch(tipoCliente) {
		case COMUM:
			return 1000.0;
		case SUPER:
			return 5000.0;
		case PREMIUM:
			return 10000.0;
		default:
			return 0.0;
		}
	
		
	}
		
}

	
