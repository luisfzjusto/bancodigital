package br.com.cdb.bancodigital.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.cdb.bancodigital.entity.Conta;

public class ContaDAO {
	
	private HashMap<String, Conta> contas;
	
	public ContaDAO(){
		contas = new HashMap<>();
	}
	
	public void salvar (Conta conta) {
		contas.put(conta.getNumeroConta(), conta);
	}
	
	public Conta buscar (String numeroConta) {
		return contas.get(numeroConta);
	}
	
	public void atualizar (Conta conta) {
		contas.put(conta.getNumeroConta(), conta);
	}
	
	public void excluir (String numeroConta) {
		contas.remove(numeroConta);
	}
	
	

}
