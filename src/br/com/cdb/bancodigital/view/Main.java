package br.com.cdb.bancodigital.view;

import br.com.cdb.bancodigital.dao.ClienteDAO;
import br.com.cdb.bancodigital.service.ClienteService;
import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Cliente.TipoCliente;

import java.util.Scanner;

public class Main {
	
	private static ClienteService clienteService;
	private static Scanner scanner;

	public static void main(String[] args) {
		
		clienteService = new ClienteService(new ClienteDAO());
		
		scanner = new Scanner(System.in);
		
		exibirMenu();
	}
	
	private static void exibirMenu() {
		
		int opcao;
		
		do {
			System.out.println("===== BEM-VINDO AO CDBANK =====");
			System.out.println("DIGITE A OPÇÃO DESEJADA:");
			System.out.println("1 - Cadastrar novo cliente");
			System.out.println("2 - Listar clientes cadastrados");
			System.out.println("3 - Sair");
			opcao = scanner.nextInt();
			
			switch (opcao) {
				case 1:
					cadastrarCliente();
					break;
				case 2:
					listarClientes();
					break;
				case 3:
					System.out.println("Encerrando a sessão. O CDBank agradece a sua visita!");
					break;
				default:
					System.out.println("Opção inválida. Por favor, escolha novamente.");
			}
		} while (opcao != 3);
	}
	
	private static void cadastrarCliente() {
		scanner.nextLine();
		System.out.println("Você selecionou cadastro de cliente.");
		System.out.println("Por favor, digite o CPF (apenas os números):");
		String cpf = scanner.nextLine();
		System.out.println("Por favor, digite o nome completo:");
		String nome = scanner.nextLine();
		System.out.println("Por favor, digite a data de nascimento (dd/mm/aaaa):");
		String dataNascimento = scanner.nextLine();
		System.out.println("Por favor, digite o endereco completo (rua, número, complemento, cidade, estado, cep:");
		String endereco = scanner.nextLine();
		System.out.println("Por favor, informe a categoria do cliente: COMUM, SUPER, PREMIUM:");
		String tipoString = scanner.nextLine();
		TipoCliente tipo = TipoCliente.valueOf(tipoString.toUpperCase());
		
		
		clienteService.cadastrarCliente(cpf, nome, dataNascimento, endereco, tipo);
	}
	
	private static void listarClientes() {
		System.out.println("Relação de clientes CDBank:");
		for (Cliente cliente : clienteService.getClientes()){
			System.out.println(cliente);
		}
	}	
}
