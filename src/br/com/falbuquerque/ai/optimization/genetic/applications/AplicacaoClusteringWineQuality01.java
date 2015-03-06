package br.com.falbuquerque.ai.optimization.genetic.applications;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoProbabilidadesFixas;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVAplicacoes;
import br.com.falbuquerque.ai.optimization.genetic.domains.Dominio;
import br.com.falbuquerque.ai.optimization.genetic.domains.DominioClusteringRepresGruposInt;
import br.com.falbuquerque.ai.optimization.genetic.domains.clustering.Cluster;
import br.com.falbuquerque.ai.optimization.genetic.mutation.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.population.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.population.PopulacaoTradicional;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.ComportamentoReproducao;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.CrossoverUniforme;
import br.com.falbuquerque.ai.optimization.genetic.selection.MetodoSelecao;
import br.com.falbuquerque.ai.optimization.genetic.selection.RoletaIntegerDouble;
import br.com.falbuquerque.ai.optimization.genetic.selection.Windowing;
import br.com.falbuquerque.ai.optimization.genetic.selection.WindowingDouble;

public class AplicacaoClusteringWineQuality01 implements AplicacaoClusteringWineQuality {

	@Override
	public void executarAlgoritmo(final List<Individuo<Integer, Double>> populacaoInicial,
	        final GeradorArquivosCSVAplicacoes<Double> geradorArquivos) {
		final Boolean aplicarMutacaoDirigida = true;
		final Integer numClusters = 7;
		final Integer aplicacao = 1;

		for (int i = 0; i < 1; i++) {
			// Definição dos parâmetros de mutação
			final ControladorMutacoes<Integer, Double> controladorMutacoes = new ControladorMutacoes<>(aplicarMutacaoDirigida,
			        GERACAO_INICIO_VERIFICACAO_MD, QTDE_GERACOES_VERIFICACAO_MD, QTDE_INDIVIDUOS_SELECIONADOS_MD);

			final Windowing<Integer, Double> windowing = new WindowingDouble<>(0.0009);

			// Definição do método de seleção de indivíduos mais adaptados
			final MetodoSelecao<Integer, Double> metodoSelecao = new RoletaIntegerDouble(TIPO_PROBLEMA, windowing);

			// Definição do comportamento de reprodução dos indivíduos
			final ComportamentoReproducao<Integer, Double> comportamentoReproducao = new CrossoverUniforme<>();

			// Definição do tipo de população utilizado
			final EstruturaPopulacao<Integer, Double> populacao = new PopulacaoTradicional<>(TAMANHO_POPULACAO,
			        controladorMutacoes, metodoSelecao, i, true);

			// Contexto de evolução
			final ContextoEvolucao<Integer, Double> contextoEvolucao = new ContextoProbabilidadesFixas<>(TAXAS_GERAIS,
			        TAXAS_MUTACAO_DIRIGIDA, ALFABETO, TIPO_PROBLEMA, VERIFICADOR_CONVERGENCIA);

			final Dominio<Integer, Double, List<Cluster>> dominio = new DominioClusteringRepresGruposInt(NUM_MAX_GERACOES,
			        DADOS, numClusters, contextoEvolucao, comportamentoReproducao, controladorMutacoes, populacao);

			geradorArquivos.adicionarExecucao(aplicacao,
			        Collections.singletonList(dominio.procurarMelhorIndividuo().getFitness()));

			try {
				final AuxiliarDadosPopulacoes aux = new AuxiliarDadosPopulacoes();
				aux.resumirDadosGeracoes(NUM_MAX_GERACOES, i);
				aux.encontrarMelhorIndividuo(NUM_MAX_GERACOES, i);
			} catch (final IOException ioException) {
				ioException.printStackTrace();
			}

		}

	}

}
