package br.com.falbuquerque.ai.optimization.genetic.reproducao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class CrossoverUmPonto<N extends Number, S extends Number & Comparable<S>> implements ComportamentoReproducao<N, S> {

	@Override
	public List<Individuo<N, S>> gerarFilhos(Individuo<N, S> pai, Individuo<N, S> mae) {
		final int tamanhoCromossomo = pai.getGenotipo().size();
		int pontoCorte = new Random().nextInt(tamanhoCromossomo);

		if (pontoCorte == 0) {
			pontoCorte = 1;
		}

		final List<N> primeiroFragmentoPai = new ArrayList<>(pai.getGenotipo().subList(0, pontoCorte));
		final List<N> segundoFragmentoPai = new ArrayList<>(pai.getGenotipo().subList(pontoCorte, tamanhoCromossomo));
		final List<N> primeiroFragmentoMae = new ArrayList<>(mae.getGenotipo().subList(0, pontoCorte));
		final List<N> segundoFragmentoMae = new ArrayList<>(mae.getGenotipo().subList(pontoCorte, tamanhoCromossomo));

		primeiroFragmentoPai.addAll(segundoFragmentoMae);
		primeiroFragmentoMae.addAll(segundoFragmentoPai);

		final List<Individuo<N, S>> filhosGerados = new ArrayList<>(2);
		filhosGerados.add(new Individuo<>(primeiroFragmentoPai, pai.getComportamentoReproducao()));
		filhosGerados.add(new Individuo<>(primeiroFragmentoMae, mae.getComportamentoReproducao()));

		return filhosGerados;
	}

}
