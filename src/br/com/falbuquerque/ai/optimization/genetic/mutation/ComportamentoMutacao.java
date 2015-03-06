package br.com.falbuquerque.ai.optimization.genetic.mutation;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoEvolucao;

public interface ComportamentoMutacao<N extends Number, S extends Number & Comparable<S>> {

	void aplicarMutacao(
			List<Individuo<N, S>> individuos, ContextoEvolucao<N, S> contextoEvolucao);
}
