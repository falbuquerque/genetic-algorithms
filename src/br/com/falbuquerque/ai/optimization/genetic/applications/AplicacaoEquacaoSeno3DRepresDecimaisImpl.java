package br.com.falbuquerque.ai.optimization.genetic.applications;

import java.util.Collections;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoProbabilidadesVariaveis;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVAplicacoes;
import br.com.falbuquerque.ai.optimization.genetic.domains.Dominio;
import br.com.falbuquerque.ai.optimization.genetic.domains.DominioEquacaoSeno3DRepresDecimais;
import br.com.falbuquerque.ai.optimization.genetic.mutation.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.population.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.population.PopulacaoTradicional;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.ComportamentoReproducao;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.CrossoverUmPonto;
import br.com.falbuquerque.ai.optimization.genetic.selection.MetodoSelecao;
import br.com.falbuquerque.ai.optimization.genetic.selection.RoletaIntegerDouble;

public class AplicacaoEquacaoSeno3DRepresDecimaisImpl implements AplicacaoEquacaoSeno3DRepresInteiros {

	@Override
	public void executarAlgoritmo(final List<Individuo<Integer, Double>> populacaoInicial,
	        final GeradorArquivosCSVAplicacoes<Double> geradorArquivos) {
		final Boolean aplicarMutacaoDirigida = false;
		final Integer aplicacao = 1;

		for (int i = 0; i < NUMERO_EXECUCOES; i++) {

			// Defini��o dos par�metros de muta��o
			final ControladorMutacoes<Integer, Double> controladorMutacoes = new ControladorMutacoes<>(aplicarMutacaoDirigida,
			        GERACAO_INICIO_VERIFICACAO_MD, QTDE_GERACOES_VERIFICACAO_MD, QTDE_INDIVIDUOS_SELECIONADOS_MD);

			// Defini��o do m�todo de sele��o de indiv�duos mais adaptados
			final MetodoSelecao<Integer, Double> metodoSelecao = new RoletaIntegerDouble(TIPO_PROBLEMA);

			// Defini��o do comportamento de reprodu��o dos indiv�duos
			final ComportamentoReproducao<Integer, Double> comportamentoReproducao = new CrossoverUmPonto<>();

			// Defini��o do tipo de popula��o utilizado
			final EstruturaPopulacao<Integer, Double> populacao = new PopulacaoTradicional<>(TAMANHO_POPULACAO,
			        controladorMutacoes, metodoSelecao);

			// Contexto de evolu��o
			final ContextoEvolucao<Integer, Double> CONTEXTO_EVOLUCAO = new ContextoProbabilidadesVariaveis<>(TAXAS_GERAIS,
			        TAXAS_MUTACAO_DIRIGIDA, TAXAS_MAXIMAS, ALFABETO, TIPO_PROBLEMA, VERIFICADOR_CONVERGENCIA,
			        TAXA_MODIFICACAO_TAXAS);

			final Dominio<Integer, Double, Double> dominio = new DominioEquacaoSeno3DRepresDecimais(NUM_MAX_GERACOES,
			        CONTEXTO_EVOLUCAO, comportamentoReproducao, controladorMutacoes, populacao);

			dominio.inicializarPopulacao(populacaoInicial);
			geradorArquivos.adicionarExecucao(aplicacao,
			        Collections.singletonList(dominio.procurarMelhorIndividuo().getFitness()));
		}

	}

}
