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
import br.com.falbuquerque.ai.optimization.genetic.selecao.Roleta;
import br.com.falbuquerque.ai.optimization.genetic.selecao.RoletaIntegerDouble;

public class AplicacaoClusteringWine01 implements AplicacaoClusteringWine {

	@Override
	public void executarAlgoritmo(List<Individuo<Integer, Double>> populacaoInicial,
	        GeradorArquivosCSVAplicacoes<Double> geradorArquivos) {
		final Boolean aplicarMutacaoDirigida = true;
		final Integer numClusters = 3;
		final Integer aplicacao = 1;

		for (int execucao = 0; execucao < 1; execucao++) {

			// Definição dos parâmetros de mutação
			final ControladorMutacoes<Integer, Double> controladorMutacoes = new ControladorMutacoes<>(aplicarMutacaoDirigida,
			        GERACAO_INICIO_VERIFICACAO_MD, QTDE_GERACOES_VERIFICACAO_MD, QTDE_INDIVIDUOS_SELECIONADOS_MD);

			// Definição do método de seleção de indivíduos mais adaptados
			final Roleta<Integer, Double> roleta = new RoletaIntegerDouble(TIPO_PROBLEMA);

			// Definição do comportamento de reprodução dos indivíduos
			final ComportamentoReproducao<Integer, Double> comportamentoReproducao = new CrossoverUniforme<>();

			// Definição do tipo de população utilizado
			final EstruturaPopulacao<Integer, Double> populacao = new PopulacaoTradicional<>(TAMANHO_POPULACAO,
			        controladorMutacoes, roleta, execucao, true);

			// Contexto de evolução
			final ContextoEvolucao<Integer, Double> contextoEvolucao = new ContextoProbabilidadesFixas<>(TAXAS_GERAIS,
			        TAXAS_MUTACAO_DIRIGIDA, ALFABETO, TIPO_PROBLEMA, VERIFICADOR_CONVERGENCIA);

			final Dominio<Integer, Double, List<Cluster>> dominio = new DominioClusteringRepresGruposInt(NUM_MAX_GERACOES,
			        DADOS, numClusters, contextoEvolucao, comportamentoReproducao, controladorMutacoes, populacao);

			geradorArquivos.adicionarExecucao(aplicacao,
			        Collections.singletonList(dominio.procurarMelhorIndividuo().getFitness()));

			try {
				new AuxiliarDadosPopulacoes().resumirDadosGeracoes(AplicacaoClusteringWine.NUM_MAX_GERACOES, execucao);
			} catch (final IOException ioException) {
				ioException.printStackTrace();
			}

		}

	}

}
