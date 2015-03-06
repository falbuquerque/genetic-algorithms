package br.com.falbuquerque.ai.optimization.genetic.csv;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GeradorArquivosCSVRoleta<S extends Number & Comparable<S>> extends GeradorArquivosCSV {

	private static final String CONTEUDO_CABECALHO = "Geração;Fitness;Porcentagem " + "na roleta;Genótipo\n";

	private final NumberFormat formatadorPorcentagens;

	public GeradorArquivosCSVRoleta(final Integer numCasasDecimais) {
		super(numCasasDecimais, CONTEUDO_CABECALHO);
		this.formatadorPorcentagens = NumberFormat.getInstance(new Locale("pt", "BR"));
		this.formatadorPorcentagens.setMinimumFractionDigits(2);
		this.formatadorPorcentagens.setMaximumFractionDigits(2);
		this.formatadorPorcentagens.setGroupingUsed(true);
	}

	public <N extends Number> void adicionarfFitness(final Integer geracao, final S fitness, final Double porcentagemNaRoleta,
	        final List<N> genotipo) {
		String individuo = genotipo.toString().replaceAll("\\s", "").replace('[', '\u0000').replace(']', '\u0000');

		this.conteudo.append(geracao).append(";").append(this.formatadorValores.format(fitness)).append(";")
		        .append(this.formatadorPorcentagens.format(porcentagemNaRoleta)).append(";").append(individuo).append("\n");
	}

}
