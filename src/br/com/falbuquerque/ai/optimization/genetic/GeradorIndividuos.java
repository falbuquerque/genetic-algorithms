package br.com.falbuquerque.ai.optimization.genetic;

import java.util.ArrayList;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.alfabeto.Alfabeto;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.ComportamentoReproducao;

public class GeradorIndividuos<N extends Number, S extends Number & Comparable<S>> {

	private final Integer tamanhoCromossomo;
	private final Alfabeto<N> alfabeto;
	private final ComportamentoReproducao<N, S> comportamentoReproducao;

	public GeradorIndividuos(final Alfabeto<N> alfabeto, final Integer tamanhoCromossomo,
	        final ComportamentoReproducao<N, S> comportamentoReproducao) {
		this.alfabeto = alfabeto;
		this.tamanhoCromossomo = tamanhoCromossomo;
		this.comportamentoReproducao = comportamentoReproducao;
	}

	public GeradorIndividuos(final Alfabeto<N> alfabeto, final Integer tamanhoCromossomo) {
		this(alfabeto, tamanhoCromossomo, null);
	}

	public Individuo<N, S> criarIndividuoAleatoriamente() {
		final List<N> genotipo = new ArrayList<N>(this.tamanhoCromossomo);

		for (int i = 0; i < this.tamanhoCromossomo; i++) {
			genotipo.add(this.alfabeto.sortearNumero());
		}

		return new Individuo<N, S>(genotipo, this.comportamentoReproducao);
	}

}
