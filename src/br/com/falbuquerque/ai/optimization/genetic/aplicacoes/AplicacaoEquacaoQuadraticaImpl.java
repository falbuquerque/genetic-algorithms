package br.com.falbuquerque.ai.optimization.genetic.aplicacoes;

import java.util.Collections;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoProbabilidadesVariaveis;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVAplicacoes;
import br.com.falbuquerque.ai.optimization.genetic.dominios.Dominio;
import br.com.falbuquerque.ai.optimization.genetic.dominios.DominioEquacaoQuadratica;
import br.com.falbuquerque.ai.optimization.genetic.mutacao.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.populacao.PopulacaoTradicional;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.ComportamentoReproducao;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.CrossoverUmPonto;
import br.com.falbuquerque.ai.optimization.genetic.selecao.MetodoSelecao;
import br.com.falbuquerque.ai.optimization.genetic.selecao.RoletaEquacaoQuadratica;

public class AplicacaoEquacaoQuadraticaImpl implements AplicacaoEquacaoQuadratica {

	@Override
	public void executarAlgoritmo(final List<Individuo<Integer, Long>> populacaoInicial,
	        final GeradorArquivosCSVAplicacoes<Long> geradorArquivos) {
		final Boolean aplicarMutacaoDirigida = false;
		final Integer aplicacao = 1;

		for (int i = 0; i < NUMERO_EXECUCOES; i++) {

			// Defini��o dos par�metros de muta��o
			final ControladorMutacoes<Integer, Long> controladorMutacoes = new ControladorMutacoes<>(aplicarMutacaoDirigida,
			        GERACAO_INICIO_VERIFICACAO_MD, QTDE_GERACOES_VERIFICACAO_MD, QTDE_INDIVIDUOS_SELECIONADOS_MD);

			// Defini��o do m�todo de sele��o de indiv�duos mais adaptados
			final MetodoSelecao<Integer, Long> metodoSelecao = new RoletaEquacaoQuadratica(TIPO_PROBLEMA);

			// Defini��o do comportamento de reprodu��o dos indiv�duos
			final ComportamentoReproducao<Integer, Long> comportamentoReproducao = new CrossoverUmPonto<>();

			// Defini��o do tipo de popula��o utilizado
			final EstruturaPopulacao<Integer, Long> populacao = new PopulacaoTradicional<>(TAMANHO_POPULACAO,
			        controladorMutacoes, metodoSelecao);

			// Contexto de evolu��o
			final ContextoEvolucao<Integer, Long> CONTEXTO_EVOLUCAO = new ContextoProbabilidadesVariaveis<>(TAXAS_GERAIS,
			        TAXAS_MUTACAO_DIRIGIDA, TAXAS_MAXIMAS, ALFABETO, TIPO_PROBLEMA, VERIFICADOR_CONVERGENCIA,
			        TAXA_MODIFICACAO_TAXAS);

			final Dominio<Integer, Long, Integer> dominio = new DominioEquacaoQuadratica(NUM_MAX_GERACOES, TAMANHO_CROMOSSOMO,
			        CONTEXTO_EVOLUCAO, comportamentoReproducao, controladorMutacoes, populacao);

			dominio.inicializarPopulacao(populacaoInicial);
			geradorArquivos.adicionarExecucao(aplicacao,
			        Collections.singletonList(dominio.procurarMelhorIndividuo().getFitness()));
		}

	}

}
