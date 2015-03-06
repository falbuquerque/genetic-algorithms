package br.com.falbuquerque.ai.optimization.genetic.aplicacoes;

import java.util.Collections;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoProbabilidadesVariaveis;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVAplicacoes;
import br.com.falbuquerque.ai.optimization.genetic.dominios.Dominio;
import br.com.falbuquerque.ai.optimization.genetic.dominios.DominioEquacaoSeno3DRepresInteiros;
import br.com.falbuquerque.ai.optimization.genetic.mutacao.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.populacao.PopulacaoTradicional;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.ComportamentoReproducao;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.CrossoverUmPonto;
import br.com.falbuquerque.ai.optimization.genetic.selecao.MetodoSelecao;
import br.com.falbuquerque.ai.optimization.genetic.selecao.RoletaIntegerDouble;

public class AplicacaoEquacaoSeno3DRepresInteirosImpl implements AplicacaoEquacaoSeno3DRepresInteiros {

	@Override
	public void executarAlgoritmo(final List<Individuo<Integer, Double>> populacaoInicial,
	        final GeradorArquivosCSVAplicacoes<Double> geradorArquivos) {
		final Boolean aplicarMutacaoDirigida = false;
		final Integer aplicacao = 1;

		for (int i = 0; i < NUMERO_EXECUCOES; i++) {
			// Definição dos parâmetros de mutação
			final ControladorMutacoes<Integer, Double> controladorMutacoes = new ControladorMutacoes<>(aplicarMutacaoDirigida,
			        GERACAO_INICIO_VERIFICACAO_MD, QTDE_GERACOES_VERIFICACAO_MD, QTDE_INDIVIDUOS_SELECIONADOS_MD);

			// Definição do método de seleção de indivíduos mais adaptados
			final MetodoSelecao<Integer, Double> metodoSelecao = new RoletaIntegerDouble(TIPO_PROBLEMA);

			// Definição do comportamento de reprodução dos indivíduos
			final ComportamentoReproducao<Integer, Double> comportamentoReproducao = new CrossoverUmPonto<>();

			// Definição do tipo de população utilizado
			final EstruturaPopulacao<Integer, Double> populacao = new PopulacaoTradicional<>(TAMANHO_POPULACAO,
			        controladorMutacoes, metodoSelecao);

			// Contexto de evolução
			final ContextoEvolucao<Integer, Double> CONTEXTO_EVOLUCAO = new ContextoProbabilidadesVariaveis<>(TAXAS_GERAIS,
			        TAXAS_MUTACAO_DIRIGIDA, TAXAS_MAXIMAS, ALFABETO, TIPO_PROBLEMA, VERIFICADOR_CONVERGENCIA,
			        TAXA_MODIFICACAO_TAXAS);

			final Dominio<Integer, Double, Integer> dominio = new DominioEquacaoSeno3DRepresInteiros(NUM_MAX_GERACOES,
			        CONTEXTO_EVOLUCAO, comportamentoReproducao, controladorMutacoes, populacao);

			dominio.inicializarPopulacao(populacaoInicial);
			geradorArquivos.adicionarExecucao(aplicacao,
			        Collections.singletonList(dominio.procurarMelhorIndividuo().getFitness()));
		}

	}

}
