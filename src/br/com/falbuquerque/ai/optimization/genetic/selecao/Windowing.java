package br.com.falbuquerque.ai.optimization.genetic.selecao;

import java.util.HashMap;
import java.util.Map;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public abstract class Windowing<N extends Number, S extends Number & Comparable<S>> {

	protected S valorSubtrair;

	public Windowing(final S valorSubtrair) {
		this.valorSubtrair = valorSubtrair;
	}

	public Map<Individuo<N, S>, S> aplicarWindowing(final Map<Individuo<N, S>, S> avaliacoes) {
		final Map<Individuo<N, S>, S> novasAvaliacoes = new HashMap<>(avaliacoes.size());
		avaliacoes.keySet().forEach(
		        individuo -> novasAvaliacoes.put(individuo, this.subtrairDoFitness(avaliacoes.get(individuo))));

		return novasAvaliacoes;
	}

	protected abstract S subtrairDoFitness(S fitness);
}
