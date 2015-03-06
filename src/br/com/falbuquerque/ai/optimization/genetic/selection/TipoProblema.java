package br.com.falbuquerque.ai.optimization.genetic.selection;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public interface TipoProblema<S extends Number & Comparable<S>> {

	S definirMelhorFitness(S fitness1, S fitness2);

	<N extends Number> void ordenarIndividuosDecrescentePorAdaptacao(List<Individuo<N, S>> individuos);

	<N extends Number> List<Individuo<N, S>> obterNMelhoresIndividuos(List<Individuo<N, S>> individuos, Integer n);
}
