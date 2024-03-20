package br.com.cdb.bancodigital.entity;

public interface Conta {
	String getNumeroConta();
	Cliente getTitular();
	double getSaldo();
	
	public static final double saldo = 0;
		
	public CartaoDebito getCartaoDebito();
	public CartaoCredito getCartaoCredito();
	public boolean debitar(double valor);
	
	void transferir(Conta destino, double valor);
	void sacar(double valor);
	void depositar(double valor);
	void exibirExtrato();
	void setCartaoCredito(CartaoCredito cartaoCredito);
	void setCartaoDebito(CartaoDebito cartaoDebito);
	
	
}
