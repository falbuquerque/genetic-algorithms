package br.com.falbuquerque.ai.optimization.genetic.selecao;

import java.util.HashMap;
import java.util.Map;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public abstract class MetodoSelecao<N extends Number, S extends Number & Comparable<S>> {

	protected final TipoProblema<S> tipoProblema;
	protected Map<Individuo<N, S>, S> avaliacoes;
	protected S fitnessTotal;
	protected S melhorFitness;
	protected Individuo<N, S> melhorIndividuo;

	public MetodoSelecao(final TipoProblema<S> tipoProblema) {
		this.avaliacoes = new HashMap<>();
		this.tipoProblema = tipoProblema;
	}

	protected abstract void incrementarFitnessTotal(S fitness);

	public abstract Individuo<N, S> obterProximoIndividuo();

	public void adicionarAvaliacao(final Individuo<N, S> individuo, final S fitness) {
		this.avaliacoes.put(individuo, fitness);
		this.incrementarFitnessTotal(fitness);

		if ((this.melhorFitness == null)
		        || !this.melhorFitness.equals(this.tipoProblema.definirMelhorFitness(this.melhorFitness, fitness))) {
			this.melhorFitness = fitness;
			this.melhorIndividuo = individuo;
		}

	}

	public void reiniciar() {
		this.avaliacoes.clear();
	}

	public Individuo<N, S> getMelhorIndividuo() {
		return this.melhorIndividuo;
	}

	public S getFitnessTotal() {
		return this.fitnessTotal;
	}

}
