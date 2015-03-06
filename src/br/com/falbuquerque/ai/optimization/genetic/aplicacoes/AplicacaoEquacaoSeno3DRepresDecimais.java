package br.com.falbuquerque.ai.optimization.genetic.aplicacoes;

import br.com.falbuquerque.ai.optimization.genetic.alfabeto.Alfabeto;
import br.com.falbuquerque.ai.optimization.genetic.alfabeto.AlfabetoBinario;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ConjuntoTaxas;
import br.com.falbuquerque.ai.optimization.genetic.selecao.TipoProblema;
import br.com.falbuquerque.ai.optimization.genetic.selecao.TipoProblemaMaximizacao;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergencia;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergenciaNumerosDiscretos;

public interface AplicacaoEquacaoSeno3DRepresDecimais extends AplicacaoAlgoritmoGenetico<Integer, Double> {

	/**
	 * Percentual da popula��o que � substitu�da a cada gera��o
	 */
	Double INTERVALO_GERACAO = 0.6;

	/**
	 * Taxa de <i>crossover</i>
	 */
	Double TAXA_CROSSOVER = 0.8;

	/**
	 * Taxa de muta��o
	 */
	Double TAXA_MUTACAO = 0.2;

	/**
	 * Taxa de <i>crossover</i> m�xima
	 */
	Double TAXA_CROSSOVER_MAXIMA = 1.0;

	/**
	 * Taxa de muta��o m�xima
	 */
	Double TAXA_MUTACAO_MAXIMA = 0.7;

	/**
	 * Taxa de <i>crossover</i> na muta��o dirigida no esquema dominante
	 */
	Double TAXA_CROSSOVER_MUTACAO_DIRIGIDA = 0.8;

	/**
	 * Taxa de muta��o na muta��o dirigida no esquema dominante
	 */
	Double TAXA_MUTACAO_MUTACAO_DIRIGIDA = 0.9;

	/**
	 * Taxa de modifica��o de taxas de <i>crossover</i> e muta��o
	 */
	Double TAXA_MODIFICACAO_TAXAS = 0.1;

	/**
	 * Probabilidade de o indiv�duo de <i>fitness</i> menos adaptado ser
	 * escolhido no lugar do <i>fitness</i> mais adaptado
	 */
	Float LIMIAR_SELECAO_TORNEIO = 0.1F;

	/**
	 * Tamanho do cromossomo
	 */
	Integer TAMANHO_CROMOSSOMO = 30;

	/**
	 * Tamanho da popula��o
	 */
	Integer TAMANHO_POPULACAO = 80;

	/**
	 * M�ximo de gera��es que o algoritmo verificar�
	 */
	Integer NUM_MAX_GERACOES = 400;

	/**
	 * Gera��o a partir da qual a muta��o dirigida no esquema dominante come�a a
	 * monitorar a popula��o
	 */
	Integer GERACAO_INICIO_VERIFICACAO_MD = 120;

	/**
	 * Quantidade de gera��es consecutivas nas quais a muta��o dirigida no
	 * esquema dominante � aplicada
	 */
	Integer QTDE_GERACOES_VERIFICACAO_MD = 10;

	/**
	 * Quantidade de melhores indiv�duos selecionados a cada gera��o para sofrer
	 * muta��o, na muta��o dirigida no esquema dominante
	 */
	Integer QTDE_INDIVIDUOS_SELECIONADOS_MD = 5;

	/**
	 * Alfabeto do problema
	 */
	Alfabeto<Integer> ALFABETO = new AlfabetoBinario();

	/**
	 * Tipo de problema
	 */
	TipoProblema<Double> TIPO_PROBLEMA = new TipoProblemaMaximizacao<Double>();

	/**
	 * Conjunto de taxas utilizada no algoritmo, por <i>default</i>
	 */
	ConjuntoTaxas TAXAS_GERAIS = new ConjuntoTaxas(INTERVALO_GERACAO, TAXA_CROSSOVER, TAXA_MUTACAO);

	/**
	 * M�ximos para as taxas utilizadas no algoritmo
	 */
	ConjuntoTaxas TAXAS_MAXIMAS = new ConjuntoTaxas(INTERVALO_GERACAO, TAXA_CROSSOVER_MAXIMA, TAXA_MUTACAO_MAXIMA);

	/**
	 * Conjunto de taxas utilizada no algoritmo na muta��o dirigida no esquema
	 * dominante
	 */
	ConjuntoTaxas TAXAS_MUTACAO_DIRIGIDA = new ConjuntoTaxas(INTERVALO_GERACAO, TAXA_CROSSOVER_MUTACAO_DIRIGIDA,
	        TAXA_MUTACAO_MUTACAO_DIRIGIDA);

	/**
	 * Verificador de converg�ncia, com:<br>
	 * <ul>
	 * <li>Percentual de genes iguais para um alelo ser considerado convergente:
	 * 80%</li>
	 * <li>Percentual de alelos convergentes para afirmar que uma popula��o est�
	 * convergindo: 80%</li>
	 * </ul>
	 */
	VerificadorConvergencia<Integer> VERIFICADOR_CONVERGENCIA = new VerificadorConvergenciaNumerosDiscretos<Integer>(0.8, 0.8);
}
