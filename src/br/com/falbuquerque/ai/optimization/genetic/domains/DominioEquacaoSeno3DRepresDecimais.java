package br.com.falbuquerque.ai.optimization.genetic.domains;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.mutation.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.population.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.ComportamentoReproducao;

public class DominioEquacaoSeno3DRepresDecimais extends Dominio<Integer, Double, Double> {

	private static final Integer TAMANHO_CROMOSSOMO = 30;

	private static final Integer LIMITE_SUPERIOR = 1023;

	private static final Integer LIMITE_INFERIOR = -1024;

	public DominioEquacaoSeno3DRepresDecimais(final Integer numMaxGeracoes,
	        final ContextoEvolucao<Integer, Double> contextoEvolucao,
	        final ComportamentoReproducao<Integer, Double> comportamentoReproducao,
	        final ControladorMutacoes<Integer, Double> controladorMutacoes,
	        final EstruturaPopulacao<Integer, Double> estruturaPopulacao) {
		super(numMaxGeracoes, TAMANHO_CROMOSSOMO, contextoEvolucao, comportamentoReproducao, controladorMutacoes,
		        estruturaPopulacao);
	}

	@Override
	protected Double calcularFitness(final Individuo<Integer, Double> individuo) {
		return Math.sin(this.obterValorIndividuo(individuo));
	}

	@Override
	protected Boolean encontrouOtimoGlobal() {
		return (1 == this.obterValorIndividuo(this.estruturaPopulacao.getMelhorIndividuo()));
	}

	@Override
	protected Double obterValorIndividuo(final Individuo<Integer, Double> individuo) {
		final List<Integer> genotipo = individuo.getGenotipo();
		final Double x = this.obterValorIndividuo(genotipo.subList(0, (this.tamanhoCromossomo / 2)));
		final Double y = this.obterValorIndividuo(genotipo.subList((this.tamanhoCromossomo / 2), this.tamanhoCromossomo));

		return x * y;
	}

	private Double obterValorIndividuo(final List<Integer> genotipo) {
		final Integer k = this.tamanhoCromossomo / 2;
		final StringBuilder representacao = new StringBuilder(k);
		genotipo.forEach(gene -> representacao.append(gene));

		final Integer r = Integer.valueOf(representacao.toString(), 2);

		return LIMITE_INFERIOR + ((LIMITE_SUPERIOR - LIMITE_INFERIOR) / Math.pow(2, k)) * r;
	}

}
