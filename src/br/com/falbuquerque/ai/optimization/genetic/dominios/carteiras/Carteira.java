package br.com.falbuquerque.ai.optimization.genetic.dominios.carteiras;

import java.util.List;

public class Carteira {

	private final List<Ativo> ativos;

	public Carteira(final List<Ativo> ativos) {
		this.ativos = ativos;
	}

	public Double calcularRetornoEsperado() {
		Double retorno = 0.0;

		for (final Ativo ativo : this.ativos) {
			retorno += ativo.getPeso() * ativo.getRetornoEsperado();
		}

		return retorno;
	}

	public Double calcularVariancia() {
		final Double[][] matrizCovariancia = new Double[this.ativos.size()][this.ativos.size()];

		for (int i = 0; i < matrizCovariancia.length; i++) {
			final Ativo ativo = this.ativos.get(i);

			for (int j = 0; j < matrizCovariancia[0].length; j++) {
				final RelacaoComAtivo relacao = ativo.getRelacoesComOutrosAtivos().get(j);
				final Ativo outroAtivo = relacao.getAtivo();

				matrizCovariancia[i][j] = ativo.getPeso() * outroAtivo.getPeso() * relacao.getCovariancia();
			}

		}

		return this.calcularDeterminante(matrizCovariancia);
	}

	private Double calcularDeterminante(final Double[][] matriz) {
		return (matriz[0][0] * matriz[1][1] * matriz[2][2] * matriz[3][3] * matriz[4][4])
		        + (matriz[1][0] * matriz[2][1] * matriz[3][2] * matriz[4][3] * matriz[0][4])
		        + (matriz[2][0] * matriz[3][1] * matriz[4][2] * matriz[0][3] * matriz[1][4])
		        + (matriz[3][0] * matriz[4][1] * matriz[0][2] * matriz[1][3] * matriz[2][4])
		        + (matriz[4][0] * matriz[0][1] * matriz[1][2] * matriz[2][3] * matriz[3][4])
		        - (matriz[0][4] * matriz[1][3] * matriz[2][2] * matriz[3][1] * matriz[4][0])
		        - (matriz[1][4] * matriz[2][3] * matriz[3][4] * matriz[4][1] * matriz[0][0])
		        - (matriz[2][4] * matriz[3][3] * matriz[4][2] * matriz[0][1] * matriz[1][0])
		        - (matriz[3][4] * matriz[4][3] * matriz[0][2] * matriz[1][1] * matriz[2][0])
		        - (matriz[4][4] * matriz[0][3] * matriz[1][2] * matriz[2][1] * matriz[3][0]);
	}

	@Override
	public String toString() {
		final StringBuilder retorno = new StringBuilder();
		this.ativos.forEach(ativo -> retorno.append(ativo).append("\n"));
		return retorno.toString();
	}

}
