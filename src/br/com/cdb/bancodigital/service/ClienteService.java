package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Cliente.TipoCliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import br.com.cdb.bancodigital.dao.ClienteDAO;

public class ClienteService {
	
	private ClienteDAO clienteDAO;
	
	public ClienteService(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	
	//CADASTRO DO CLIENTE
	public void cadastrarCliente(String cpf, String nome, String dataNascimento, String endereco, TipoCliente tipo) {
		if (!validarCPF(cpf)) {
			System.out.println("CPF inválido!");
			return;
		}
		
		if (!cpfEhUnico(cpf)) {
			System.out.println("CPF já cadastrado!");
			return;
		}
		
		if (!validarNome(nome)) {
			System.out.println("Formato de nome inválido!");
			return;
		}
		
		if (!validarDataNascimento(dataNascimento)) {
			System.out.println("Data de nascimento em formato inválido ou menor de idade!");
			return;
		}
		
		String[] partesDoEndereco = endereco.split(",");
		if (partesDoEndereco.length != 6) {
			System.out.println("Endereço incompleto!");
			return;
		}
		
		String rua = partesDoEndereco[0].trim();
		String numero = partesDoEndereco[1].trim();
		String complemento = partesDoEndereco[2].trim();
		String cidade = partesDoEndereco[3].trim();
		String estado = partesDoEndereco[4].trim();
		String cep = partesDoEndereco[5].trim();
		
		if (!validarEndereco(rua, numero, complemento, cidade, estado, cep)) {
			System.out.println("Endereço inválido!");
			return;
		}		
		
		Cliente novoCliente = new Cliente (cpf, nome, dataNascimento, endereco);
		novoCliente.setTipo(tipo);
		clienteDAO.salvarCliente(novoCliente);
		System.out.println("Cliente cadastrado com sucesso!");
	}
	
	//CLASSIFICAR COMO SUPER
	public void classificarSuper(String cpf) {
		Cliente cliente = clienteDAO.encontrarClientePorCPF(cpf);
		if (cliente != null) {
			cliente.setTipo(TipoCliente.SUPER);
		}
	}
	
	//CLASSIFICAR COMO PREMIUM
		public void classificarPremium(String cpf) {
			Cliente cliente = clienteDAO.encontrarClientePorCPF(cpf);
			if (cliente != null) {
				cliente.setTipo(TipoCliente.PREMIUM);
			}
		}
		
	//ENCONTRAR POR CPF
		public Cliente encontrarClientePorCPF(String cpf) {
			for (Cliente cliente : clienteDAO.getListaDeClientes()) {
				if (cliente.getCpf().equals(cpf)) {
					return cliente;
				}
			}
			return null;
		}
		
	//VERIFICAR SE O CPF É VÁLIDO
		private boolean validarCPF(String cpf) {
			cpf = cpf.replaceAll("[^0-9]", "");
			
			if(cpf.length() != 11) {
				return false;
			}
			
			int soma = 0;
			for (int i=0; i < 9; i++) {
				soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
			}
			
			int resto = soma % 11;
			int digitoVerificador1 = resto < 2 ? 0 : 11 - resto;
			
			if (Character.getNumericValue(cpf.charAt(9)) != digitoVerificador1) {
				return false;
			}
			
			soma = 0;
			for (int i = 0; i < 10; i++) {
				soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
			}
			resto = soma % 11;
			int digitoVerificador2 = resto < 2 ? 0 : 11 - resto;
			
			return Character.getNumericValue(cpf.charAt(10)) == digitoVerificador2;
		}
		
	//VERIFICAR SE O CPF É ÚNICO
		private boolean cpfEhUnico(String cpf) {
			for (Cliente cliente : clienteDAO.getListaDeClientes()) {
				if (cliente.getCpf().equals(cpf)) {
					return false;
				}
			}
			return true;
		}
		
	//VALIDAÇÃO DO NOME
		private boolean validarNome(String nome) {
			return Pattern.matches("[a-zA-ZÀ-ú ]{2,100}", nome);
		}
		
	//VALIDAÇÃO DA DATA DE NASCIMENTO E IDADE
		private boolean validarDataNascimento(String dataNascimento) {
			try {
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate dn = LocalDate.parse(dataNascimento, formato);
				
				LocalDate hoje = LocalDate.now();
				return dn.plusYears(18).isBefore(hoje) || dn.plusYears(18).isEqual(hoje);				
			} catch (Exception e) {
				return false;
			}
		}
	
	//VALIDAÇÃO DO CEP
		private boolean validarCEP(String cep) {
			return Pattern.matches("\\d{5}-\\d{3}", cep);
		}
		
	//VALIDAÇÃO DO ENDEREÇO COMPLETO
		private boolean validarEndereco(String rua, String numero, String complemento, String cidade, String estado, String cep) {
			return rua != null && !rua.isEmpty() &&
					numero != null && !numero.isEmpty() &&
					cidade != null && !cidade.isEmpty() &&
					estado != null && !estado.isBlank() &&
					cep != null && !cep.isEmpty() &&
					validarCEP(cep);
 		}
		
	//OBTENÇÃO DOS CLIENTES CADASTRADOS
		public Collection<Cliente> getClientes(){
			return clienteDAO.getListaDeClientes();
		}
		
	
		
	}


	
	


