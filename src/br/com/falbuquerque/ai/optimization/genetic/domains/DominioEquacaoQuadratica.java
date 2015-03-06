package br.com.falbuquerque.ai.optimization.genetic.domains;

import java.util.ArrayList;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.mutation.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.population.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.ComportamentoReproducao;

public class DominioEquacaoQuadratica extends Dominio<Integer, Long, Integer> {

	public DominioEquacaoQuadratica(final Integer numMaxGeracoes, final Integer tamanhoCromossomo,
	        final ContextoEvolucao<Integer, Long> contextoEvolucao,
	        final ComportamentoReproducao<Integer, Long> comportamentoReproducao,
	        final ControladorMutacoes<Integer, Long> controladorMutacoes,
	        final EstruturaPopulacao<Integer, Long> estruturaPopulacao) {
		super(numMaxGeracoes, tamanhoCromossomo, contextoEvolucao, comportamentoReproducao, controladorMutacoes,
		        estruturaPopulacao);
	}

	@Override
	protected Long calcularFitness(final Individuo<Integer, Long> individuo) {
		return (long) Math.pow(this.obterValorIndividuo(individuo), 2);
	}

	@Override
	protected Boolean encontrouOtimoGlobal() {
		final List<Integer> genotipoEsperado = new ArrayList<>(this.tamanhoCromossomo);

		for (int i = 0; i < this.tamanhoCromossomo; i++) {
			genotipoEsperado.add(1);
		}

		return genotipoEsperado.equals(this.estruturaPopulacao.getMelhorIndividuo().getGenotipo());
	}

	@Override
	protected Integer obterValorIndividuo(final Individuo<Integer, Long> individuo) {
		final StringBuilder representacao = new StringBuilder(this.tamanhoCromossomo);
		individuo.getGenotipo().forEach(gene -> representacao.append(gene));

		return Integer.valueOf(representacao.toString(), 2);
	}

}
