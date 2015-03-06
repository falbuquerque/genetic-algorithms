package br.com.falbuquerque.ai.optimization.genetic.domains;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.mutation.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.population.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.ComportamentoReproducao;

public class DominioEquacaoSeno3DRepresInteiros extends Dominio<Integer, Double, Integer> {

	private static final Integer TAMANHO_CROMOSSOMO = 22;

	public DominioEquacaoSeno3DRepresInteiros(final Integer numMaxGeracoes,
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
	protected Integer obterValorIndividuo(final Individuo<Integer, Double> individuo) {
		final List<Integer> genotipo = individuo.getGenotipo();
		final Integer x = this.obterValorIndividuo(genotipo.subList(0, (this.tamanhoCromossomo / 2)));
		final Integer y = this.obterValorIndividuo(genotipo.subList((this.tamanhoCromossomo / 2), this.tamanhoCromossomo));

		return x * y;
	}

	/**
	 * Obtém o valor do indivíduo que tem o genótipo fornecido
	 * 
	 * @param genotipo
	 *            o genótipo
	 * @return o valor do indivíduo segundo o genótipo
	 */
	private Integer obterValorIndividuo(final List<Integer> genotipo) {
		final StringBuilder representacao = new StringBuilder((this.tamanhoCromossomo / 2) - 1);
		final Integer sinal = genotipo.get(0);
		final List<Integer> genotipoSemBitDoSinal = genotipo.subList(1, genotipo.size());
		genotipoSemBitDoSinal.forEach(gene -> representacao.append(gene));

		final Integer valor = Integer.valueOf(representacao.toString(), 2);

		// Atribui o sinal
		if (1 == sinal) {
			return -valor;
		}

		return valor;
	}

}
