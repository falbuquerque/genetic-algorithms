package br.com.falbuquerque.ai.optimization.genetic.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public abstract class Torneio<N extends Number, S extends Number & Comparable<S>> extends MetodoSelecao<N, S> {

	protected Float limiar;
	private Random sorteador;

	public Torneio(final TipoProblema<S> tipoProblema, final Float limiar) {
		super(tipoProblema);
		this.limiar = limiar;
		this.sorteador = new Random();
	}

	@Override
	public Individuo<N, S> obterProximoIndividuo() {
		final List<Individuo<N, S>> individuos = new ArrayList<>(this.avaliacoes.keySet());
		int quantidadeSorteados = 2;
		S maiorAvaliacao = null;
		Individuo<N, S> individuoEscolhido = null;

		while (quantidadeSorteados > 0) {
			final Individuo<N, S> individuoSorteado = individuos.get(this.sorteador.nextInt(individuos.size()));
			final S fitnessIndividuo = this.avaliacoes.get(individuoSorteado);
			final boolean ganhaOVencedor = !(this.sorteador.nextFloat() < this.limiar);

			if (individuoEscolhido == null) {
				individuoEscolhido = individuoSorteado;
				maiorAvaliacao = fitnessIndividuo;
			} else {

				// Se as avaliações forem iguais, fica o primeiro sorteado
				if ((fitnessIndividuo.compareTo(maiorAvaliacao) > 0) && ganhaOVencedor) {
					individuoEscolhido = individuoSorteado;
					maiorAvaliacao = fitnessIndividuo;
				}

			}

			quantidadeSorteados--;
		}

		return individuoEscolhido;
	}

}
