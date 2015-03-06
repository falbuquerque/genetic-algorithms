package br.com.falbuquerque.ai.optimization.genetic.reproduction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class CrossoverUniforme<N extends Number, S extends Number & Comparable<S>> implements ComportamentoReproducao<N, S> {
	private final Random sorteador = new Random();

	@Override
	public List<Individuo<N, S>> gerarFilhos(final Individuo<N, S> pai, final Individuo<N, S> mae) {
		final int tamanhoCromossomo = pai.getGenotipo().size();
		final List<N> genotipoFilho = new ArrayList<>(tamanhoCromossomo);

		for (int i = 0; i < tamanhoCromossomo; i++) {

			if (this.sorteador.nextInt(2) == 0) {
				genotipoFilho.add(pai.getGenotipo().get(i));
			} else {
				genotipoFilho.add(mae.getGenotipo().get(i));
			}

		}

		return Collections.singletonList(new Individuo<>(genotipoFilho, pai.getComportamentoReproducao()));
	}

}
