package br.com.cdb.bancodigital.entity;

public class SeguroFraude implements Seguro {
	
	private static final double coberturaBase = 5000.0;
	
	@Override
	public double calcularCustoMensal() {
		// TODO Auto-generated method stub
		return 0.0;
	}

	@Override
	public String gerarApolice(Cliente cliente, CartaoCredito cartao) {
		
		String nomeCliente = cliente.getNome();
		String numeroCartao = cartao.getNumero();
		
		StringBuilder apoliceFraude = new StringBuilder();
		apoliceFraude.append("Apólice do Seguro Contra Fraudes:\n");
		apoliceFraude.append("Cliente: ").append(nomeCliente).append("\n");
		apoliceFraude.append("Número do Cartão de Crédito: ").append(numeroCartao).append("\n");
		apoliceFraude.append("Cobertura: R$ ").append(coberturaBase).append("\n");
		apoliceFraude.append("Esse seguro oferece cobertura automática contra fraudes.");		
				
		return apoliceFraude.toString();
	}
	
	
}
