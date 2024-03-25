package br.com.cdb.bancodigital.view;

import br.com.cdb.bancodigital.dao.ClienteDAO;
import br.com.cdb.bancodigital.entity.Seguro;
import br.com.cdb.bancodigital.service.ClienteService;
import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Cliente.TipoCliente;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;
import br.com.cdb.bancodigital.entity.SeguroFraude;
import br.com.cdb.bancodigital.entity.SeguroViagem;
import br.com.cdb.bancodigital.service.ContaService;
import br.com.cdb.bancodigital.dao.ContaDAO;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.entity.Cartao;
import java.util.Scanner;

public class Main {
	
	private static ClienteService clienteService;	
	private static ContaService contaService;
	private static Scanner scanner;

	public static void main(String[] args) {
		
		clienteService = new ClienteService(new ClienteDAO());
		contaService = new ContaService(new ContaDAO());
		
		
		scanner = new Scanner(System.in);
		
		exibirMenu();
	}
	
	private static void exibirMenu() {
		
		int opcao;
		
		do {
			System.out.println();
			System.out.println("===== BEM-VINDO AO CDBANK =====");
			System.out.println();
			System.out.println("DIGITE A OPÇÃO DESEJADA:");
			System.out.println();
			System.out.println("1 - Cadastrar Novo Cliente");
			System.out.println("2 - Listar Clientes Cadastrados");
			System.out.println("3 - Cadastrar Conta Corrente");
			System.out.println("4 - Cadastrar Conta Poupança");
			System.out.println("5 - Depositar");
			System.out.println("6 - Transferir");
			System.out.println("7 - Sacar");
			System.out.println("8 - Exibir Saldo");
			System.out.println("9 - Exibir Extrato");
			System.out.println("10 - Exibir Dados da Conta");
			System.out.println("11 - Emitir Cartão de Crédito");
			System.out.println("12 - Emitir Cartão de Débito");
			System.out.println("13 - Alteração de Senha de Cartões");
			System.out.println("14 - Pagamentos com Cartão de Crédito");
			System.out.println("15 - Exibir Fatura do Cartão de Crédito");
			System.out.println("16 - Pagamentos com Cartão de Débito");
			System.out.println("17 - Alterar Limite Diário do Cartão de Débito");
			System.out.println("18 - Contratar Seguro Viagem");			
			System.out.println("19 - Emissão de Apólice de Seguro Viagem");
			System.out.println("20 - Emissão de Apólice de Seguro Fraude");
			System.out.println("21 - Desativar Cartão");
			System.out.println("22 - Reativar Cartão");
			System.out.println("23 - Sair");
			opcao = scanner.nextInt();
			
			switch (opcao) {
				case 1:
					cadastrarCliente();
					break;
				case 2:
					listarClientes();
					break;
				case 3:
					criarContaCorrente();
					break;
				case 4:
					criarContaPoupanca();
					break;
				case 5:
					depositar();
					break;
				case 6:
					transferir();
					break;
				case 7:
					sacar();
					break;
				case 8:
					exibirSaldo();
					break;
				case 9:
					exibirExtrato();
					break;
				case 10:
					exibirDadosConta();
					break;
				case 11:
					emitirCartaoCredito();
					break;
				case 12:
					emitirCartaoDebito();
				break;
				case 13:
					alterarSenhaCartao();
				break;
				case 14:
					realizarPagamentoCartaoCredito();
					break;
				case 15:
					exibirFaturaAtual();
					break;
				case 16:
					realizarPagamentoCartaoDebito();
					break;
				case 17:
					alterarLimiteDiarioCartaoDebito();
					break;
				case 18:
					contratarSeguroViagem();
					break;
				case 19:
					exibirApoliceViagem();
					break;
				case 20:
					exibirApoliceFraude();
					break;
				case 21:
					desativarCartao();
					break;
				case 22:
					ativarCartao();
					break;
				case 23:
					System.out.println("Sessão encerrada. O CDBANK agradece sua visita.");
					break;
				default:
					System.out.println("Opção inválida. Por favor, escolha novamente a opção desejada:");
			}
		} while (opcao != 23);
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
		
		TipoCliente tipo = null;
		boolean tipoValido = false;
		
		do {
			try {
				System.out.println("Por favor, informe a categoria do cliente: COMUM, SUPER, PREMIUM:");
				String tipoString = scanner.nextLine();
				tipo = TipoCliente.valueOf(tipoString.toUpperCase());
				tipoValido = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Tipo de cliente inválido. Por favor, digite novamente.");
			}
		} while (!tipoValido);
		
		
		clienteService.cadastrarCliente(cpf, nome, dataNascimento, endereco, tipo);
		System.out.println();
	}
	
	private static void listarClientes() {
		System.out.println("Relação de clientes CDBANK:");
		for (Cliente cliente : clienteService.getClientes()){
			System.out.println("Nome: " + cliente.getNome());
			System.out.println("CPF: " + cliente.getCpf());
			System.out.println("Categoria: " + cliente.getTipo());
		}
		System.out.println();
	}	
	
	private static void criarContaCorrente() {
		scanner.nextLine();
		System.out.println("Você selecionou cadastro de conta corrente.");
		System.out.println("Por favor, digite o CPF do titular da conta (apenas números).");
		String cpf = scanner.nextLine();
		Cliente titular = clienteService.encontrarClientePorCPF(cpf);
		if (titular == null) {
			System.out.println("Cliente não está cadastrado.");
			return;			
		}		
		System.out.println("Por favor, informe o saldo inicial da conta.");
		double saldoInicial = scanner.nextDouble();
		ContaCorrente contaCorrente = contaService.criarContaCorrente(cpf, titular, saldoInicial);
		System.out.println("Conta corrente criada com sucesso!");
	    System.out.println("Número da conta: " + contaCorrente.getNumeroConta());
	}
	
	private static void criarContaPoupanca() {
		scanner.nextLine();
		System.out.println("Você selecionou cadastro de conta poupança.");
		System.out.println("Por favor, digite o CPF do titular da conta (apenas números).");
		String cpf = scanner.nextLine();
		Cliente titular = clienteService.encontrarClientePorCPF(cpf);
		if (titular == null) {
			System.out.println("Cliente não está cadastrado.");
			return;
		}
		System.out.println("Por favor, informe o saldo inicial da conta.");
		double saldoInicial = scanner.nextDouble();
		ContaPoupanca contaPoupanca = contaService.criarContaPoupanca(cpf, titular, saldoInicial);
		System.out.println("Conta corrente criada com sucesso!");
	    System.out.println("Número da conta: " + contaPoupanca.getNumeroConta());
	}
	
	private static void depositar() {
	    scanner.nextLine();
	    System.out.println("Você selecionou depositar.");
	    System.out.println("Por favor, digite o número da conta:");
	    String numeroConta = scanner.nextLine();
	    System.out.println("Por favor, digite o valor a ser depositado:");
	    double valor = scanner.nextDouble();
	    
	    Conta conta = contaService.buscarContaPorNumero(numeroConta);
	    
	    if(conta != null) {
	    	contaService.depositar(conta, valor);
	    } else {
	    	System.out.println("Conta não encontrada.");
	    }
	}
	
	private static void transferir() {
	    scanner.nextLine();
	    System.out.println("Você selecionou transferir.");
	    System.out.println("Por favor, digite o número da conta de origem:");
	    String origemNumeroConta = scanner.nextLine();
	    System.out.println("Por favor, digite o número da conta de destino:");
	    String destinoNumeroConta = scanner.nextLine();
	    System.out.println("Por favor, digite o valor a ser transferido:");
	    double valor = scanner.nextDouble();
	    
	    Conta origem = contaService.buscarContaPorNumero(origemNumeroConta);
	    Conta destino = contaService.buscarContaPorNumero(destinoNumeroConta);
	    
	    if (origem == null || destino == null) {
	    	System.out.println("Uma das contas informadas não foi encontrada.");
	    	return;
	    }
	    contaService.transferir(origem, destino, valor);
	}
	
	private static void sacar() {
	    scanner.nextLine();
	    System.out.println("Você selecionou sacar.");
	    System.out.println("Por favor, digite o número da conta:");
	    String numeroConta = scanner.nextLine();
	    System.out.println("Por favor, digite o valor a ser sacado:");
	    double valor = scanner.nextDouble();
	    
	    Conta conta = contaService.buscarContaPorNumero(numeroConta);
	    
	    if (conta == null) {
	    	System.out.println("A conta informada não foi encontrada.");
	    	return;
	    }
	    contaService.sacar(conta, valor);
	}
	
	private static void exibirSaldo() {
	    scanner.nextLine();
	    System.out.println("Você selecionou exibir saldo.");
	    System.out.println("Por favor, digite o número da conta:");
	    String numeroConta = scanner.nextLine();
	    
	    Conta conta = contaService.buscarContaPorNumero(numeroConta);
	    
	    if (conta == null) {
	    	System.out.println("A conta informada não foi encontrada.");
	    	return;
	    }
	    contaService.exibirSaldo(conta);
	}
	
	private static void exibirExtrato() {
	    scanner.nextLine();
	    System.out.println("Você selecionou exibir extrato.");
	    System.out.println("Por favor, digite o número da conta:");
	    String numeroConta = scanner.nextLine();
	    
	    Conta conta = contaService.buscarContaPorNumero(numeroConta);
	    
	    if (conta == null) {
	    	System.out.println("A conta informada não foi encontrada.");
	    	return;
	    }
	    contaService.exibirExtrato(conta);
	}
	
	private static void exibirDadosConta() {
	    scanner.nextLine();
	    System.out.println("Você selecionou exibir dados da conta.");
	    System.out.println("Por favor, digite o número da conta:");
	    String numeroConta = scanner.nextLine();
	    
	    Conta conta = contaService.buscarContaPorNumero(numeroConta);
	    
	    if (conta == null) {
	    	System.out.println("A conta informada não foi encontrada.");
	    	return;
	    }
	    contaService.exibiDadosConta(conta);
	}
	
	private static void emitirCartaoCredito() {
		scanner.nextLine();
		System.out.println("Você selecionou emissão de cartão de crédito.");
		System.out.println("Por favor, digite o número da conta");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		if(conta == null) {
			System.out.println("Conta não encontrada.");
			return;
		}
		
		if(conta.getCartaoCredito()!= null) {
			System.out.println("Essa conta já possui um cartão de crédito vinculado.");
			return;
		}
		
		System.out.println("Por favor, digite a senha do cartão com 6 números.");
		String senhaCredito = scanner.nextLine();
		
		CartaoCredito cartaoCredito = contaService.emitirCartaoCredito(conta);
		if(cartaoCredito != null) {
			cartaoCredito.setSenha(senhaCredito);
			System.out.println("Cartão de crédito emitido com sucesso.");
			System.out.println("Número do cartão: " + cartaoCredito.getNumero());
			System.out.println("A sua senha é: " + cartaoCredito.getSenha());
		}
	}
	
	private static void emitirCartaoDebito() {
		scanner.nextLine();
		System.out.println("Você selecionou emissão de cartão de débito.");
		System.out.println("Por favor, digite o número da conta");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		if(conta == null) {
			System.out.println("Conta não encontrada.");
			return;
		}
		
		if(conta.getCartaoDebito() != null) {
			System.out.println("Essa conta já possui um cartão de débito vinculado.");
			return;
		}
		
		System.out.println("Por favor, digite a senha do cartão com 6 números.");
		String senhaDebito = scanner.nextLine();		
		
		CartaoDebito cartaoDebito = contaService.emitirCartaoDebito(conta, 0);
		if(cartaoDebito != null) {
			cartaoDebito.setSenha(senhaDebito);
			System.out.println("Cartão de débito emitido com sucesso.");
			System.out.println("Número do cartão: " + cartaoDebito.getNumero());
			System.out.println("A sua senha é: " + cartaoDebito.getSenha());
		}
	}
	
	private static void exibirApoliceViagem() {
	    scanner.nextLine();
	    System.out.println("Você selecionou exibir detalhes da apólice do seguro de viagem.");
	    System.out.println("Por favor, digite o número da conta:");
	    String numeroConta = scanner.nextLine();
	    
	    Conta conta = contaService.buscarContaPorNumero(numeroConta);
	    
	    if (conta == null) {
	        System.out.println("A conta informada não foi encontrada.");
	        return;
	    }
	    
	    Cliente titular = conta.getTitular();
	    CartaoCredito cartaoCredito = conta.getCartaoCredito();
	    
	    if (cartaoCredito == null) {
	        System.out.println("Essa conta não possui um cartão de crédito vinculado.");
	        return;
	    }
	    
	    if(titular.getTipo() != TipoCliente.PREMIUM && !titular.isSeguroViagemContratado()) {
	    	System.out.println("O cliente informado não contratou esse serviço.");
	    	return;
	    }
	    
	    SeguroViagem seguroViagem = new SeguroViagem();
	    String detalhesApoliceViagem = seguroViagem.gerarApolice(titular, cartaoCredito);
	    System.out.println(detalhesApoliceViagem);
	    
	    System.out.println("\nPressione Enter para retornar ao Menu Principal.");
	    scanner.nextLine();
	}
	
	private static void exibirApoliceFraude() {
	    scanner.nextLine();
	    System.out.println("Você selecionou exibir detalhes da apólice do seguro contra fraude.");
	    System.out.println("Por favor, digite o número da conta:");
	    String numeroConta = scanner.nextLine();
	    
	    Conta conta = contaService.buscarContaPorNumero(numeroConta);
	    
	    if (conta == null) {
	        System.out.println("A conta informada não foi encontrada.");
	        return;
	    }
	    
	    Cliente titular = conta.getTitular();
	    CartaoCredito cartaoCredito = conta.getCartaoCredito();
	    
	    if (cartaoCredito == null) {
	        System.out.println("Essa conta não possui um cartão de crédito vinculado.");
	        return;
	    }
	    
	    SeguroFraude seguroFraude = new SeguroFraude();
	    String detalhesApoliceFraude = seguroFraude.gerarApolice(titular, cartaoCredito);
	    System.out.println(detalhesApoliceFraude);
	    
	    System.out.println("\nPressione Enter para retornar ao Menu Principal.");
	    scanner.nextLine();	
	}
	
	private static void contratarSeguroViagem() {
	    scanner.nextLine();
	    System.out.println("Você selecionou contratar seguro viagem.");
	    System.out.println("Por favor, digite o número da conta:");
	    String numeroConta = scanner.nextLine();
	    
	    Conta conta = contaService.buscarContaPorNumero(numeroConta);
	    	    
	    if (conta == null) {
	        System.out.println("A conta informada não foi encontrada.");
	        return;
	    }
	    
	    Cliente cliente = conta.getTitular();
	    
	    if (cliente.getTipo() == TipoCliente.PREMIUM) {
	        System.out.println("Seguro viagem já incluso para clientes Premium.");
	        return;
	    }
	    
	    if(cliente.isSeguroViagemContratado()) {
	    	System.out.println("O seguro já foi contratado para esse cliente.");
	    	return;
	    }
	    
	    double custoSeguro = 50.0;
	    
	    if(!cobrarCustoSeguro(cliente, custoSeguro)) {
	    	System.out.println("Saldo insuficiente para a contratação desse seguro");
	    	return;
	    }
	    
	    cliente.setSeguroViagemContratado(true);
	    
	    System.out.println("Seguro viagem contratado com sucesso.");
	}
	
	private static boolean cobrarCustoSeguro(Cliente cliente, double custoSeguro) {
		
		Conta conta = cliente.getConta();
		
		if(conta == null) {
			System.out.println("Conta não encontrada.");
			return false;
		}
		
		double saldo = conta.getSaldo();
		
		if (saldo >= custoSeguro) {
			conta.debitar(custoSeguro);
			return true;
		} else {
			System.out.println("Saldo insuficiente para contratação do seguro");
			return false;
		}
	}
	
	private static void realizarPagamentoCartaoCredito() {
		scanner.nextLine();
		System.out.println("Você selecionou pagamentos com cartão de crédito.");
		System.out.println("Por favor, digite o número da conta");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		
		if (conta == null) {
			System.out.println("A conta informada não foi encontrada");
			return;
		}
		
		CartaoCredito cartaoCredito = conta.getCartaoCredito();
		
		if (cartaoCredito == null || !cartaoCredito.isAtivo()) {
			System.out.println("Essa conta não possui um cartão de crédito ativo.");
			return;
		}
		
		System.out.println("Por favor, digite o valor do pagamento:");
		double valor = scanner.nextDouble();
		
		boolean pagamentoRealizado = contaService.realizarPagamentoCartaoCredito(cartaoCredito, valor);
		if(pagamentoRealizado) {
			System.out.println("Pagamento realizado com sucesso.");
		} else {
			System.out.println("Pagamento não realizado. Limite do cartão excedido ou cartão desativado.");
		}		
		
	}
	
	private static void exibirFaturaAtual() {
		scanner.nextLine();
		System.out.println("Você selecionou exibir fatura do cartão de crédito");
		System.out.println("Por favor, digite o número da conta:");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		
		if (conta == null) {
			System.out.println("A conta informada não foi encontrada.");
			return;
		}
		
		CartaoCredito cartaoCredito = conta.getCartaoCredito();
		
		if (cartaoCredito == null || !cartaoCredito.isAtivo()) {
			System.out.println("Essa conta não possui cartão de crédito ativo");
			return;
		}
		
		double faturaAtual = cartaoCredito.getFaturaAtual();
		double taxaUso = 	cartaoCredito.calcularTaxaUso();
		double faturaTotal = faturaAtual + taxaUso;
		
		System.out.println("Fatura atual: R$ " + faturaAtual);
		System.out.println("Taxa de uso: R$ " + taxaUso);
		System.out.println("Total da Fatura: R$ " + faturaTotal);
	}
	
	private static void realizarPagamentoCartaoDebito() {
		scanner.nextLine();
		System.out.println("Você selecionou pagamento com cartão de débito");
		System.out.println("Por favor, digite o número da conta");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		
		if (conta == null) {
			System.out.println("A conta informada não foi localizada");
			return;
		}
		
		CartaoDebito cartaoDebito = conta.getCartaoDebito();
		
		if (cartaoDebito == null || !cartaoDebito.isAtivo()) {
			System.out.println("Essa conta não possui um cartão de débito ativo");
			return;
		}
		
		System.out.println("Por favor, informe o valor do pagamento:");
		double valor = scanner.nextDouble();
		
		boolean pagamentoRealizado = contaService.realizarPagamentoCartaoDebito(cartaoDebito, valor);
		if(pagamentoRealizado) {
			System.out.println("Pagamento realizado com sucesso.");
		} else {
			System.out.println("Pagamento não realizado. Limite diário do cartão excedido ou cartão desativado.");
		}
	}
	
	private static void alterarLimiteDiarioCartaoDebito() {
		scanner.nextLine();
		System.out.println("Você selecionou alterar o limite diário do cartão de débito.");
		System.out.println("Por favor, digite o número da conta:");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		
		if(conta == null) {
			System.out.println("Conta informada não foi localizada.");
			return;
		}
		
		CartaoDebito cartaoDebito = conta.getCartaoDebito();
		
		if(cartaoDebito == null) {
			System.out.println("Essa conta não possui um cartão de débito vinculado.");
			return;
		}
		
		System.out.println("Por favor, digite o limite diário desejado:");
		double novoLimite = scanner.nextDouble();
		
		cartaoDebito.setLimiteDiario(novoLimite);
		System.out.println("Limite diário do cartão de débito alterado para R$ " + novoLimite);
	}
	
	private static void alterarSenhaCartao() {
		scanner.nextLine();
		System.out.println("Você selecionou alteração de senha de cartões.");
		System.out.println("Por favor, digite o número da conta:");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		
		if(conta == null) {
			System.out.println("A conta informada não foi encontrada.");
			return;
		}
		
		CartaoCredito cartaoCredito = conta.getCartaoCredito();
		CartaoDebito cartaoDebito = conta.getCartaoDebito();
		
		if(cartaoCredito == null && cartaoDebito == null) {
			System.out.println("A conta informada não possui cartão vinculado.");
			return;
		}
		
		if(cartaoCredito != null && cartaoDebito != null) {
			System.out.println("Informe o cartão que terá a senha alterada:");
			System.out.println("1 - Cartão de crédito");
			System.out.println("2 - Cartão de débito");
			int opcao = scanner.nextInt();
			
			if(opcao ==1) {
				alterarSenhaCartaoCredito(cartaoCredito);
			} else if(opcao ==2) {
				alterarSenhaCartaoDebito(cartaoDebito);
			} else {
				System.out.println("Opção inválida.");
			}
	
		} else if(cartaoCredito != null) {
			alterarSenhaCartaoCredito(cartaoCredito);
		} else if(cartaoDebito != null) {
			alterarSenhaCartaoDebito(cartaoDebito);
		}		
	}
	
	private static void alterarSenhaCartaoCredito(CartaoCredito cartaoCredito) {
		scanner.nextLine();
		System.out.println("Por favor, digite a nova senha com 6 números:");
		String novaSenha = scanner.nextLine();
		cartaoCredito.setSenha(novaSenha);
		System.out.println("Senha alterada com sucesso.");
	}
	
	private static void alterarSenhaCartaoDebito(CartaoDebito cartaoDebito) {
		scanner.nextLine();
		System.out.println("Por favor, digite a nova senha com 6 números:");
		String novaSenha = scanner.nextLine();
		cartaoDebito.setSenha(novaSenha);
		System.out.println("Senha alterada com sucesso.");
	}
	
	private static void desativarCartao() {
		scanner.nextLine();
		System.out.println("Você selecionou desativar cartão.");
		System.out.println("Por favor, digite o número da conta:");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		
		if(conta == null) {
			System.out.println("A conta informada não foi localizada.");
			return;
		}
		
		System.out.println("Por favor, selecione o cartão que deseja desativar:");
		System.out.println("1 - Cartão de Crédito");
		System.out.println("2 - Cartão de Débito");
		int opcao = scanner.nextInt();
		
		if(opcao == 1) {
			CartaoCredito cartaoCredito = conta.getCartaoCredito();
			if(cartaoCredito == null) {
				System.out.println("A conta informada não possui cartão de crédito vinculado.");
				return;
			}
			cartaoCredito.setAtivo(false);
			System.out.println("Cartão de crédito desativado com sucesso.");
		} else if(opcao == 2) {
			CartaoDebito cartaoDebito = conta.getCartaoDebito();
			if(cartaoDebito == null) {
				System.out.println("A conta informada não possui cartão de débito vinculado.");
				return;
			}
			cartaoDebito.setAtivo(false);
			System.out.println("Cartão de débito desativado com sucesso.");
		} else {
			System.out.println("Opção inválida.");
		}
	}
	
	
	private static void ativarCartao() {
		scanner.nextLine();
		System.out.println("Você selecionou ativar cartão");
		System.out.println("Por favor, digite o número da conta:");
		String numeroConta = scanner.nextLine();
		
		Conta conta = contaService.buscarContaPorNumero(numeroConta);
		
		if(conta == null) {
			System.out.println("A conta informada não foi localizada.");
			return;
		}
		
		System.out.println("Por favor, selecione o cartão que deseja ativar:");
		System.out.println("1 - Cartão de Crédito");
		System.out.println("2 - cartão de Débito");
		int opcao = scanner.nextInt();
		
		if (opcao == 1) {
			CartaoCredito cartaoCredito = conta.getCartaoCredito();
			if(cartaoCredito == null) {
				System.out.println("A conta informada não possui cartão de crédito vinculado.");
				return;
			}
			cartaoCredito.setAtivo(true);
			System.out.println("Cartão de crédito ativado com sucesso.");
		} else if(opcao == 2) {
			CartaoDebito cartaoDebito = conta.getCartaoDebito();
			if(cartaoDebito == null) {
				System.out.println("A conta informada não possui cartão de débito vinculado.");
				return;
			}
			cartaoDebito.setAtivo(true);
			System.out.println("Cartão de débito ativado com sucesso.");
		} else {
			System.out.println("Opção inválida.");
		}
	}
	
}
	
	

