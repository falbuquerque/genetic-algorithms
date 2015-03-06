package br.com.falbuquerque.ai.optimization.genetic.csv;

import java.util.Collections;
import java.util.List;

public class GeradorArquivosCSVAplicacoes<S extends Number & Comparable<S>> extends GeradorArquivosCSV {

	private static final String CONTEUDO_CABECALHO = "Aplicação;Execução;Máximo\n";

	protected Integer execucaoAtual;

	public GeradorArquivosCSVAplicacoes(final Integer numCasasDecimais) {
		super(numCasasDecimais, CONTEUDO_CABECALHO);
		this.execucaoAtual = 0;
	}

	public void adicionarExecucao(final Integer aplicacao, final List<S> todosFitness) {
		final S maiorFitness = Collections.max(todosFitness);

		synchronized (this.execucaoAtual) {
			this.execucaoAtual++;
		}

		this.conteudo.append(aplicacao).append(";").append(this.execucaoAtual).append(";")
		        .append(this.formatadorValores.format(maiorFitness)).append("\n");
	}

	public void clear() {
		super.clear();
		this.execucaoAtual = 0;
	}

}
