package br.com.falbuquerque.ai.optimization.genetic;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.reproducao.ComportamentoReproducao;

public class Individuo<N extends Number, S extends Number & Comparable<S>> implements Comparable<Individuo<N, S>> {

	private static Integer ultimoIdIndividuo = 0;

	private final Integer id;
	private final Cromossomo<N> cromossomo;
	private ComportamentoReproducao<N, S> comportamentoReproducao;
	private S fitness;

	public Individuo(final List<N> genotipo, final ComportamentoReproducao<N, S> comportamentoReproducao) {
		ultimoIdIndividuo++;
		this.id = ultimoIdIndividuo;
		this.cromossomo = new Cromossomo<N>(genotipo);
		this.comportamentoReproducao = comportamentoReproducao;
	}

	public List<Individuo<N, S>> reproduzirSexuadamente(final Individuo<N, S> parceiro) {
		return this.comportamentoReproducao.gerarFilhos(this, parceiro);
	}

	public List<N> getGenotipo() {
		return this.cromossomo.getGenotipo();
	}

	public ComportamentoReproducao<N, S> getComportamentoReproducao() {
		return this.comportamentoReproducao;
	}

	public S getFitness() {
		return fitness;
	}

	public void setFitness(final S fitness) {
		this.fitness = fitness;
	}

	public void setComportamentoReproducao(final ComportamentoReproducao<N, S> comportamentoReproducao) {
		this.comportamentoReproducao = comportamentoReproducao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
	        return false;
        }
		
		if (this == obj) {
	        return true;
        }
		
		if (!getClass().equals(obj.getClass())) {
	        return false;
        }
		
		final Individuo<N, S> other = (Individuo<N, S>) obj;
		return this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return this.id + " - " + this.cromossomo.toString();
	}

	public void alterarGenotipo(final Integer posicaoGene, final N novoValor) {
		this.cromossomo.alterarGene(posicaoGene, novoValor);
	}

	@Override
	public int compareTo(final Individuo<N, S> individuo) {
		
		if (individuo == null) {
	        return 1;
        }
		
		if (this == individuo) {
	        return 0;
        }
		
		return this.fitness.compareTo(individuo.fitness);
	}

}
