package br.com.falbuquerque.ai.optimization.genetic.selection;

import java.util.HashMap;
import java.util.Map;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public abstract class EscalonamentoSigma<N extends Number, S extends Number & Comparable<S>> {

	private final Map<Individuo<N, S>, S> avaliacoes;

	public EscalonamentoSigma(final Map<Individuo<N, S>, S> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public Map<Individuo<N, S>, Double> aplicarEscalonamento() {
		final Map<Individuo<N, S>, Double> porcentagens = new HashMap<>(this.avaliacoes.size());
		final Double media = this.calcularMediaAvaliacoes();
		final Double desvioPadrao = this.calcularDesvioPadraoAvaliacoes();

		if (desvioPadrao != 0.0) {
			this.avaliacoes.keySet().forEach(individuo -> {
				final S fitnessAtual = this.avaliacoes.get(individuo);
				Double percentual = this.calcularPercentual(fitnessAtual, media, desvioPadrao);

				if (percentual < 0) {
					percentual = 0.1;
				}

				porcentagens.put(individuo, percentual);
			});

		} else {
			this.avaliacoes.keySet().forEach(individuo -> porcentagens.put(individuo, 1.0));
		}

		return porcentagens;
	}

	protected abstract Double calcularPercentual(S fitnessAtual, Double media, Double desvioPadrao);

	private Double calcularDesvioPadraoAvaliacoes() {
		final Double media = this.calcularMediaAvaliacoes();
		Double desvioPadrao = 0.0;

		for (final Individuo<N, S> individuo : this.avaliacoes.keySet()) {
			final Double diferenca = this.avaliacoes.get(individuo).doubleValue() - media;
			desvioPadrao += Math.pow(diferenca, 2);
		}

		return Math.sqrt(desvioPadrao / (this.avaliacoes.size() - 1));
	}

	private Double calcularMediaAvaliacoes() {
		Double media = 0.0;

		for (final Individuo<N, S> individuo : this.avaliacoes.keySet()) {
			media += this.avaliacoes.get(individuo).doubleValue();
		}

		return media / this.avaliacoes.size();
	}

}
