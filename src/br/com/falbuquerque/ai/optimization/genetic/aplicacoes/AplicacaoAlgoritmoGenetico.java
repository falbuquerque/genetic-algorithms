package br.com.falbuquerque.ai.optimization.genetic.aplicacoes;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVAplicacoes;

/**
 * Executa algoritmos gen�ticos com uma combina��o espec�fica de par�metros
 *
 * @param <N>
 *            o tipo num�rico contido nos cromossomos dos indiv�duos
 * @param <S>
 *            o tipo num�rico retornado pela fun��o <i>fitness</i>
 */
public interface AplicacaoAlgoritmoGenetico<N extends Number, S extends Number & Comparable<S>> {

	/**
	 * Diret�rio onde os arquivos ser�o gravados
	 */
	String DIRETORIO = "/dev/tmp/ai/files";

	/**
	 * N�mero de execu��es de cada aplica��o de algoritmos gen�ticos
	 */
	Integer NUMERO_EXECUCOES = 500;

	/**
	 * Executa o algoritmo diversas vezes, com a popula��o inicial definida, e a
	 * adiciona ao gerador de arquivos
	 * 
	 * @param populacaoInicial
	 *            a popula��o inicial
	 * @param geradorArquivos
	 *            o gerador de arquivos
	 */
	void executarAlgoritmo(List<Individuo<N, S>> populacaoInicial, GeradorArquivosCSVAplicacoes<S> geradorArquivos);
}
