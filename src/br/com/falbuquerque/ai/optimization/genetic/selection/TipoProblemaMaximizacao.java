package br.com.falbuquerque.ai.optimization.genetic.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class TipoProblemaMaximizacao<S extends Number & Comparable<S>> implements TipoProblema<S> {

	@Override
	public S definirMelhorFitness(final S fitness1, final S fitness2) {

		if (fitness1.compareTo(fitness2) >= 0) {
			return fitness1;
		}

		return fitness2;
	}

	@Override
	public <N extends Number> void ordenarIndividuosDecrescentePorAdaptacao(final List<Individuo<N, S>> individuos) {
		// Ordena em ordem decrescente de fitness, ou seja, maiores são
		// primeiros da lista
		Collections.sort(individuos);
		Collections.reverse(individuos);
	}

	@Override
	public <N extends Number> List<Individuo<N, S>> obterNMelhoresIndividuos(final List<Individuo<N, S>> individuos,
	        final Integer n) {
		final List<Individuo<N, S>> melhoresIndividuos = new ArrayList<>(individuos);
		this.ordenarIndividuosDecrescentePorAdaptacao(melhoresIndividuos);
		return melhoresIndividuos.subList(0, n);
	}

}
