package br.com.cdb.bancodigital.entity;

import java.util.List;

import br.com.cdb.bancodigital.entity.Cliente.TipoCliente;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Random;

public class CartaoDebito implements Cartao{
	private Conta conta;
	private double limiteDiario;
	private boolean ativo;
	private String senha;
	private String numeroCartao = gerarNumeroCartao();
	
	public CartaoDebito(String numeroCartao, Conta conta, double limiteDiario) {
		this.conta = conta;
		this.limiteDiario = limiteDiario;
		this.ativo = true;
		this.senha = "";
	}
	
	@Override
	public void realizarPagamento(double valor) {
		if (ativo && valor <= limiteDiario) {
			conta.sacar(valor);
			System.out.println("Pagamento de R$ " + valor + "realizado com sucesso.");
		} else {
			System.out.println("Pagamento não autorizado. Limite diário excedido ou cartão desativado.");
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

	@Override
	public void ajustarLimite(double novoLimite) {
		limiteDiario = novoLimite;
		System.out.println("Limite do cartão alterado para R$ " + novoLimite + ".");		
	}
	
	public String getNumero() {
        return numeroCartao;
    }
	
	public void setSenha(String senha) {
	    this.senha = senha;
	}

    public String getSenha() {
        return senha;
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
}
	




