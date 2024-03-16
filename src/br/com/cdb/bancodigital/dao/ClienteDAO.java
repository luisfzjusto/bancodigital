package br.com.cdb.bancodigital.dao;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;

import br.com.cdb.bancodigital.entity.Cliente;

public class ClienteDAO {

	private HashMap<String, Cliente> listaDeClientes;
	
	public ClienteDAO() {
		this.listaDeClientes = new HashMap<>();
	}
	
	//MÉTODO PARA SALVAR CLIENTE NO DAO
	public void salvarCliente(Cliente cliente) {
		listaDeClientes.put(cliente.getCpf(), cliente);
	}
	
	//MÉTODO PARA LOCALIZAR CLIENTE PELO CPF
	public Cliente encontrarClientePorCPF(String cpf) {
		return listaDeClientes.get(cpf);
	}
	
	//MÉTODO PARA OBTER A LISTA DE CLIENTES
	public Collection<Cliente> getListaDeClientes(){
		return listaDeClientes.values();
	}
	
		
	}
	

