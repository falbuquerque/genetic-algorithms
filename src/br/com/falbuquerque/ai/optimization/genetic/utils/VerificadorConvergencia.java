package br.com.falbuquerque.ai.optimization.genetic.utils;

import br.com.falbuquerque.ai.optimization.genetic.population.EstruturaPopulacao;

public abstract class VerificadorConvergencia<N extends Number> {

	protected final Double percentualMinimoConvergencia;
	protected final Double percentualMinimoIgualdadeAlelos;

	public VerificadorConvergencia(final Double percentualMinimoConvergencia, final Double percentualMinimoIgualdadeAlelos) {
		this.percentualMinimoConvergencia = percentualMinimoConvergencia;
		this.percentualMinimoIgualdadeAlelos = percentualMinimoIgualdadeAlelos;
	}

	public abstract <S extends Number & Comparable<S>> Boolean isPopulacaoConvergindo(EstruturaPopulacao<N, S> populacao);

	protected Double calcularPorcentagem(final Integer n, final Integer total) {
		return (n / total.doubleValue());
	}

	/**
	 * Guarda um total parcial e seu respectivo percentual em relação ao total
	 * geral
	 */
	protected class RelTotalPercentual {
		Double percentual;
		Integer total;

		/**
		 * Cria uma relação entre total parcial e percentual quanto ao total
		 * geral
		 */
		RelTotalPercentual() {
			this.total = 0;
			this.percentual = 0.0;
		}

		/**
		 * Incrementa o total em 1
		 */
		void incrementarTotal() {
			this.total++;
		}

	}

}
