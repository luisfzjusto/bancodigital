package br.com.cdb.bancodigital.entity;

public interface Cartao {
	void realizarPagamento(double valor);
	void criarSenha(String senha);
	void alterarSenha(String senha);
	void ativar();
	void desativar();
	void ajustarLimite(double novoLimite);
	
	Conta getConta();
	
}
