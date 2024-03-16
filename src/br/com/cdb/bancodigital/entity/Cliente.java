package br.com.cdb.bancodigital.entity;

public class Cliente {

	private String cpf;
	private String nome;
	private String dataNascimento;
	private String endereco;
	private TipoCliente tipo;
	
	//ENUM TIPOS DE CLIENTE
	public enum TipoCliente{
		COMUM, 
		SUPER,
		PREMIUM
	}
	
	
	//MÉTODO CONSTRUTOR
	public Cliente(String cpf, String nome, String dataNascimento, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.tipo = TipoCliente.COMUM;
	}

	//GETTERS E SETTERS
	public TipoCliente getTipo() {
		return tipo;
	}


	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo;
	}


	public String getCpf() {
		return cpf;
	}


	public String getNome() {
		return nome;
	}


	public String getDataNascimento() {
		return dataNascimento;
	}


	public String getEndereco() {
		return endereco;
	}
	
	@Override
	public String toString() {
		return "CPF: " + cpf + "\nNome: " + nome + "\nData de Nascimento: " + dataNascimento + "\nEndereço: " + endereco + "\nCategoria: " + tipo;
	}
	
	
	
	
}
