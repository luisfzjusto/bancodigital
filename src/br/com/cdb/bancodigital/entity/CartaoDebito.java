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
	private String numeroCartao;
	private double novoLimite;
	
	public CartaoDebito(String numeroCartao, Conta conta) {
		this.conta = conta;
		this.limiteDiario = 1000.0;
		this.numeroCartao = gerarNumeroCartao();
		this.ativo = true;
	}
	
	@Override
	public void realizarPagamento(double valor) {
		if(!ativo) {
			System.out.println("Cartão de débito desativado.");
			return;
		}
		
		if (valor > limiteDiario) {
			System.out.println("Transação não realizada. Valor superior ao limite diário.");
			return;
		} 
		
		if (valor > conta.getSaldo()) {
			System.out.println("Transação não realizada. Saldo insuficiente.");
			return;
		}
		
		conta.sacar(valor);
		System.out.println("Pagamento de R$ " + valor + "realizada via cartão de débito.");
		
				
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
		this.limiteDiario = novoLimite;
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
    
    public boolean isAtivo() {
	    return ativo;
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
	
	public double getLimiteDiario() {
		return limiteDiario;
	}
	
	public void setLimiteDiario(double novoLimite) {
		this.limiteDiario = novoLimite;		
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}
	




