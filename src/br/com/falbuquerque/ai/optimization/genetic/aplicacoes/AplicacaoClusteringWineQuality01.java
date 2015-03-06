package br.com.falbuquerque.ai.optimization.genetic.aplicacoes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoProbabilidadesFixas;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVAplicacoes;
import br.com.falbuquerque.ai.optimization.genetic.dominios.Dominio;
import br.com.falbuquerque.ai.optimization.genetic.dominios.DominioClusteringRepresGruposInt;
import br.com.falbuquerque.ai.optimization.genetic.dominios.clustering.Cluster;
import br.com.falbuquerque.ai.optimization.genetic.mutacao.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.populacao.PopulacaoTradicional;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.ComportamentoReproducao;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.CrossoverUniforme;
import br.com.falbuquerque.ai.optimization.genetic.selecao.MetodoSelecao;
import br.com.falbuquerque.ai.optimization.genetic.selecao.RoletaIntegerDouble;
import br.com.falbuquerque.ai.optimization.genetic.selecao.Windowing;
import br.com.falbuquerque.ai.optimization.genetic.selecao.WindowingDouble;

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
