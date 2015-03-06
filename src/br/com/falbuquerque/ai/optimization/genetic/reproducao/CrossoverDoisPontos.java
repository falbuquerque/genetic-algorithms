package br.com.falbuquerque.ai.optimization.genetic.reproducao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class CrossoverDoisPontos<N extends Number, S extends Number & Comparable<S>> implements ComportamentoReproducao<N, S> {
	private Random sorteador = new Random();

	@Override
	public List<Individuo<N, S>> gerarFilhos(Individuo<N, S> pai, Individuo<N, S> mae) {
		final int tamanhoCromossomo = pai.getGenotipo().size();
		int pontoCorte1 = 0;
		int pontoCorte2 = 0;

		pontoCorte1 = sorteador.nextInt(tamanhoCromossomo / 2);

		if (pontoCorte1 == 0) {
			pontoCorte1++;
		}

		do {
			pontoCorte2 = sorteador.nextInt(tamanhoCromossomo / 2) + (tamanhoCromossomo / 2);
		} while (pontoCorte2 == pontoCorte1);

		final List<N> primeiroFragmentoPai = new ArrayList<>(pai.getGenotipo().subList(0, pontoCorte1));
		final List<N> segundoFragmentoPai = new ArrayList<>(pai.getGenotipo().subList(pontoCorte1, pontoCorte2));
		final List<N> terceiroFragmentoPai = new ArrayList<>(pai.getGenotipo().subList(pontoCorte2, tamanhoCromossomo));

		final List<N> primeiroFragmentoMae = new ArrayList<>(mae.getGenotipo().subList(0, pontoCorte1));
		final List<N> segundoFragmentoMae = new ArrayList<>(mae.getGenotipo().subList(pontoCorte1, pontoCorte2));
		final List<N> terceiroFragmentoMae = new ArrayList<>(mae.getGenotipo().subList(pontoCorte2, tamanhoCromossomo));

		primeiroFragmentoPai.addAll(segundoFragmentoMae);
		primeiroFragmentoPai.addAll(terceiroFragmentoPai);

		primeiroFragmentoMae.addAll(segundoFragmentoPai);
		primeiroFragmentoMae.addAll(terceiroFragmentoMae);

		final List<Individuo<N, S>> filhosGerados = new ArrayList<>(2);
		filhosGerados.add(new Individuo<>(primeiroFragmentoPai, pai.getComportamentoReproducao()));
		filhosGerados.add(new Individuo<>(primeiroFragmentoMae, mae.getComportamentoReproducao()));

		return filhosGerados;
	}

}
