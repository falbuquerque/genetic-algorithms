package br.com.falbuquerque.ai.optimization.genetic.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;

public class VerificadorConvergenciaNumerosDiscretos<N extends Number> extends VerificadorConvergencia<N> {

	public VerificadorConvergenciaNumerosDiscretos(final Double percentualMinimoConvergencia,
	        final Double percentualMinimoIgualdadeAlelos) {
		super(percentualMinimoConvergencia, percentualMinimoIgualdadeAlelos);
	}

	@Override
	public <S extends Number & Comparable<S>> Boolean isPopulacaoConvergindo(final EstruturaPopulacao<N, S> populacao) {
		final List<Individuo<N, S>> individuos = populacao.getIndividuos();
		final int qtdeGenes = individuos.get(0).getGenotipo().size();
		final List<Map<N, RelTotalPercentual>> percentuais = new ArrayList<>(qtdeGenes);

		// Inicializa��o da lista de percentuais
		for (int i = 0; i < qtdeGenes; i++) {
			percentuais.add(new HashMap<>());
		}

		// Verifica quantas ocorr�ncias de cada gene ocorrem em cada alelo,
		// considerando o total de indiv�duos
		individuos.forEach(individuo -> {
			int posAleloAtual = 0;

			for (final N gene : individuo.getGenotipo()) {
				final Map<N, RelTotalPercentual> somasAleloAtual = percentuais.get(posAleloAtual);

				if (!somasAleloAtual.containsKey(gene)) {
					somasAleloAtual.put(gene, new RelTotalPercentual());
				}

				somasAleloAtual.get(gene).incrementarTotal();
				posAleloAtual++;
			}

		});

		final int totalIndividuos = individuos.size();

		// Atribui��o de percentuais aos totais calculados e verifica��o de
		// quantos alelos est�o convergindo
		int qtdeConvergindo = 0;

		for (final Map<N, RelTotalPercentual> aleloAtual : percentuais) {

			for (final N gene : aleloAtual.keySet()) {
				final RelTotalPercentual relacao = aleloAtual.get(gene);
				relacao.percentual = this.calcularPorcentagem(relacao.total, totalIndividuos);

				if (relacao.percentual >= super.percentualMinimoIgualdadeAlelos) {
					qtdeConvergindo++;
				}

			}

		}

		// Verifica se a quantidade de alelos convergindo � maior ou igual ao
		// percentual m�nimo definido
		return (this.calcularPorcentagem(qtdeConvergindo, qtdeGenes) >= super.percentualMinimoConvergencia);
	}

}
