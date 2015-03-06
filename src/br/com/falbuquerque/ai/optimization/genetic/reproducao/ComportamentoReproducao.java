package br.com.falbuquerque.ai.optimization.genetic.reproducao;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public interface ComportamentoReproducao<N extends Number, S extends Number & Comparable<S>> { 
	
	List<Individuo<N, S>> gerarFilhos(Individuo<N, S> pai, Individuo<N, S> mae);
}
