package br.com.falbuquerque.ai.optimization.genetic.aplicacoes;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVAplicacoes;

/**
 * Executa algoritmos genéticos com uma combinação específica de parâmetros
 *
 * @param <N>
 *            o tipo numérico contido nos cromossomos dos indivíduos
 * @param <S>
 *            o tipo numérico retornado pela função <i>fitness</i>
 */
public interface AplicacaoAlgoritmoGenetico<N extends Number, S extends Number & Comparable<S>> {

	/**
	 * Diretório onde os arquivos serão gravados
	 */
	String DIRETORIO = "/dev/tmp/ai/files";

	/**
	 * Número de execuções de cada aplicação de algoritmos genéticos
	 */
	Integer NUMERO_EXECUCOES = 500;

	/**
	 * Executa o algoritmo diversas vezes, com a população inicial definida, e a
	 * adiciona ao gerador de arquivos
	 * 
	 * @param populacaoInicial
	 *            a população inicial
	 * @param geradorArquivos
	 *            o gerador de arquivos
	 */
	void executarAlgoritmo(List<Individuo<N, S>> populacaoInicial, GeradorArquivosCSVAplicacoes<S> geradorArquivos);
}
