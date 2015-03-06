package br.com.falbuquerque.ai.optimization.genetic.aplicacoes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.GeradorIndividuos;
import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.alfabeto.AlfabetoBinario;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVAplicacoes;

public class Main {

	public static void main(String[] args) throws Exception {
		gerarInformacoesEquacaoQuadratica();
		gerarInformacoesEquacaoSeno3DRepresInteiros();
		gerarInformacoesEquacaoSeno3DRepresDecimais();
		gerarInformacoesClusteringWine();
		gerarInformacoesClusteringEcoli();
		gerarInformacoesClusteringWineQuality();
	}

	private static void gerarInformacoesEquacaoQuadratica() throws IOException {
		final GeradorIndividuos<Integer, Long> geradorIndividuos = new GeradorIndividuos<>(new AlfabetoBinario(),
		        AplicacaoEquacaoQuadratica.TAMANHO_CROMOSSOMO);
		final List<Individuo<Integer, Long>> populacaoInicial = new ArrayList<>(AplicacaoEquacaoQuadratica.TAMANHO_POPULACAO);
		final List<AplicacaoEquacaoQuadratica> aplicacoes = Arrays.asList(new AplicacaoEquacaoQuadraticaImpl());
		final GeradorArquivosCSVAplicacoes<Long> geradorArquivos = new GeradorArquivosCSVAplicacoes<>(0);

		int i = 0;

		while (i < AplicacaoEquacaoQuadratica.TAMANHO_POPULACAO) {
			populacaoInicial.add(geradorIndividuos.criarIndividuoAleatoriamente());
			i++;
		}

		i = 0;

		for (final AplicacaoEquacaoQuadratica aplicacao : aplicacoes) {
			i++;
			geradorArquivos.clear();
			aplicacao.executarAlgoritmo(populacaoInicial, geradorArquivos);
			geradorArquivos.criarArquivo("/dev/tmp/ai/files", "dados-x_quadrado_ap" + i);
		}

		System.out.println(populacaoInicial);
	}

	private static void gerarInformacoesEquacaoSeno3DRepresInteiros() throws IOException {
		final GeradorIndividuos<Integer, Double> geradorIndividuos = new GeradorIndividuos<Integer, Double>(
		        new AlfabetoBinario(), AplicacaoEquacaoSeno3DRepresInteiros.TAMANHO_CROMOSSOMO);
		final List<Individuo<Integer, Double>> populacaoInicial = new ArrayList<Individuo<Integer, Double>>(
		        AplicacaoEquacaoSeno3DRepresInteiros.TAMANHO_POPULACAO);
		final List<AplicacaoEquacaoSeno3DRepresInteiros> aplicacoes = Arrays
		        .asList(new AplicacaoEquacaoSeno3DRepresInteirosImpl());
		final GeradorArquivosCSVAplicacoes<Double> geradorArquivos = new GeradorArquivosCSVAplicacoes<Double>(9);

		int i = 0;

		while (i < AplicacaoEquacaoSeno3DRepresInteiros.TAMANHO_POPULACAO) {
			populacaoInicial.add(geradorIndividuos.criarIndividuoAleatoriamente());
			i++;
		}

		i = 0;

		for (final AplicacaoEquacaoSeno3DRepresInteiros aplicacao : aplicacoes) {
			i++;
			geradorArquivos.clear();
			aplicacao.executarAlgoritmo(populacaoInicial, geradorArquivos);
			geradorArquivos.criarArquivo("/dev/tmp/ai/files", "dados-sen_3D_ap" + i);
		}

		System.out.println(populacaoInicial);
	}

	private static void gerarInformacoesEquacaoSeno3DRepresDecimais() throws IOException {
		final GeradorIndividuos<Integer, Double> geradorIndividuos = new GeradorIndividuos<>(new AlfabetoBinario(),
		        AplicacaoEquacaoSeno3DRepresInteiros.TAMANHO_CROMOSSOMO);
		final List<Individuo<Integer, Double>> populacaoInicial = new ArrayList<>(
		        AplicacaoEquacaoSeno3DRepresInteiros.TAMANHO_POPULACAO);
		final List<AplicacaoEquacaoSeno3DRepresInteiros> aplicacoes = Arrays
		        .asList(new AplicacaoEquacaoSeno3DRepresInteirosImpl());
		final GeradorArquivosCSVAplicacoes<Double> geradorArquivos = new GeradorArquivosCSVAplicacoes<Double>(9);

		int i = 0;

		while (i < AplicacaoEquacaoSeno3DRepresInteiros.TAMANHO_POPULACAO) {
			populacaoInicial.add(geradorIndividuos.criarIndividuoAleatoriamente());
			i++;
		}

		i = 0;

		for (final AplicacaoEquacaoSeno3DRepresInteiros aplicacao : aplicacoes) {
			i++;
			geradorArquivos.clear();
			aplicacao.executarAlgoritmo(populacaoInicial, geradorArquivos);
			geradorArquivos.criarArquivo("/dev/tmp/ai/files", "dados-sen_3D_ap" + i);
		}

		System.out.println(populacaoInicial);
	}

	private static void gerarInformacoesClusteringWine() throws IOException {
		final List<AplicacaoClusteringWine01> aplicacoes = Arrays.asList(new AplicacaoClusteringWine01());
		final GeradorArquivosCSVAplicacoes<Double> geradorArquivos = new GeradorArquivosCSVAplicacoes<>(10);

		int i = 0;

		for (final AplicacaoClusteringWine01 aplicacao : aplicacoes) {
			i++;
			geradorArquivos.clear();
			aplicacao.executarAlgoritmo(null, geradorArquivos);
			geradorArquivos.criarArquivo("/dev/tmp/ai/files", "dados-clustering_wine_ap" + i);
		}

	}

	private static void gerarInformacoesClusteringEcoli() throws IOException {
		final List<AplicacaoClusteringEcoli01> aplicacoes = Arrays.asList(new AplicacaoClusteringEcoli01());
		final GeradorArquivosCSVAplicacoes<Double> geradorArquivos = new GeradorArquivosCSVAplicacoes<>(10);

		int i = 0;

		for (final AplicacaoClusteringEcoli01 aplicacao : aplicacoes) {
			i++;
			geradorArquivos.clear();
			aplicacao.executarAlgoritmo(null, geradorArquivos);
			geradorArquivos.criarArquivo("/dev/tmp/ai/files", "dados-clustering_ecoli_ap" + i);
		}

	}

	private static void gerarInformacoesClusteringWineQuality() throws IOException {
		final List<AplicacaoClusteringWineQuality01> aplicacoes = Arrays.asList(new AplicacaoClusteringWineQuality01());
		final GeradorArquivosCSVAplicacoes<Double> geradorArquivos = new GeradorArquivosCSVAplicacoes<>(10);

		int i = 0;

		for (final AplicacaoClusteringWineQuality01 aplicacao : aplicacoes) {
			i++;
			geradorArquivos.clear();
			aplicacao.executarAlgoritmo(null, geradorArquivos);
			geradorArquivos.criarArquivo("/dev/tmp/ai/files", "dados-clustering_wineQuality_ap" + i);
		}

	}

}
