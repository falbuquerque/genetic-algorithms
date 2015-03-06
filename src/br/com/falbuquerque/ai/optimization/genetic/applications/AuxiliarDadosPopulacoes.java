package br.com.falbuquerque.ai.optimization.genetic.applications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class AuxiliarDadosPopulacoes {

	public void resumirDadosGeracoes(final Integer numGeracoes, final Integer execucao) throws IOException {

		final Set<Double> fitnessOrdenados = new TreeSet<>(new Comparator<Double>() {

			@Override
			public int compare(final Double double1, final Double double2) {
				return double2.compareTo(double1);
			}

		});

		final StringBuilder builder = new StringBuilder(9000);
		final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
		numberFormat.setMinimumFractionDigits(10);
		numberFormat.setMaximumFractionDigits(10);
		numberFormat.setGroupingUsed(true);

		builder.append("Geração;Fitness Médio\n");

		for (int i = 1; i < numGeracoes; i++) {
			final Scanner scanner;
			Double fitnessMedio = 0d;
			String geracao = null;
			int qtdeFitness = 0;

			try {
				scanner = new Scanner(new File("/dev/tmp/ai/files/roulettes/roleta_exec" + execucao + "_ger" + i + ".csv"));
			} catch (final FileNotFoundException e) {
				throw new IllegalArgumentException("O arquivo especificado não existe");
			}

			scanner.nextLine();

			while (scanner.hasNext()) {
				final String[] valores = scanner.nextLine().split(";");
				geracao = valores[0];
				fitnessMedio += Double.valueOf(valores[1].replace(',', '.'));
				qtdeFitness++;
			}

			fitnessMedio = fitnessMedio / qtdeFitness;
			fitnessOrdenados.add(fitnessMedio);

			builder.append(geracao).append(";").append(numberFormat.format(fitnessMedio)).append("\n");

			scanner.close();
		}

		final File arquivoCSV = new File("/dev/tmp/ai/files/roulettes/resumo" + execucao + ".csv");
		final PrintWriter escritorArquivoResumo = new PrintWriter(new FileWriter(arquivoCSV));
		escritorArquivoResumo.print(builder.toString());
		escritorArquivoResumo.flush();
		escritorArquivoResumo.close();

		final File arquivoTXT = new File("/dev/tmp/ai/files/roulettes/melhor" + execucao + ".txt");
		final PrintWriter escritorArquivoMelhor = new PrintWriter(new FileWriter(arquivoTXT));
		escritorArquivoMelhor.print(fitnessOrdenados.iterator().next());
		escritorArquivoMelhor.flush();
		escritorArquivoMelhor.close();

		this.resumirDadosMelhores(numGeracoes, execucao);
	}

	public void resumirDadosMelhores(final Integer numGeracoes, final Integer execucao) throws IOException {
		final StringBuilder builder = new StringBuilder(9000);
		final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
		numberFormat.setMinimumFractionDigits(10);
		numberFormat.setMaximumFractionDigits(10);
		numberFormat.setGroupingUsed(true);

		builder.append("Geração;Fitness Médio\n");

		for (int i = 1; i < numGeracoes; i++) {
			final Scanner scanner;
			Double fitnessMaximo = Double.MIN_VALUE;
			String geracao = null;

			try {
				scanner = new Scanner(new File("/dev/tmp/ai/files/roulettes/roleta_exec" + execucao + "_ger" + i + ".csv"));
			} catch (final FileNotFoundException e) {
				throw new IllegalArgumentException("O arquivo especificado não existe");
			}

			scanner.nextLine();

			while (scanner.hasNext()) {
				final String[] valores = scanner.nextLine().split(";");
				geracao = valores[0];

				if (fitnessMaximo < Double.valueOf(valores[1].replace(',', '.'))) {
					fitnessMaximo = Double.valueOf(valores[1].replace(',', '.'));
				}

			}

			builder.append(geracao).append(";").append(numberFormat.format(fitnessMaximo)).append("\n");

			scanner.close();
		}

		final File arquivoCSV = new File("/dev/tmp/ai/files/roulettes/resumo_max" + execucao + ".csv");
		final PrintWriter escritorArquivo = new PrintWriter(new FileWriter(arquivoCSV));
		escritorArquivo.print(builder.toString());
		escritorArquivo.flush();
		escritorArquivo.close();
	}

	public void encontrarMelhorIndividuo(final Integer numGeracoes, final Integer execucao) throws IOException {
		String[] melhor = null;

		for (int i = 1; i < numGeracoes; i++) {
			final Scanner scanner;

			try {
				scanner = new Scanner(new File("/dev/tmp/ai/files/roulettes/roleta_exec" + execucao + "_ger" + i + ".csv"));
			} catch (final FileNotFoundException e) {
				throw new IllegalArgumentException("O arquivo especificado não existe");
			}

			scanner.nextLine();

			while (scanner.hasNext()) {
				final String[] valores = scanner.nextLine().split(";");

				if (melhor == null) {
					melhor = valores;
				} else {

					if (this.obterValorFitness(melhor).compareTo(this.obterValorFitness(valores)) < 0) {
						melhor = valores;
					}

				}

			}

			scanner.close();
		}

		final StringBuilder melhorToString = new StringBuilder();

		for (final String valor : melhor) {
			melhorToString.append(valor).append(";");
		}

		final File arquivoTXT = new File("/dev/tmp/ai/files/roulettes/melhor_ind" + execucao + ".txt");
		final PrintWriter escritorArquivo = new PrintWriter(new FileWriter(arquivoTXT));
		escritorArquivo.print(melhorToString);
		escritorArquivo.flush();
		escritorArquivo.close();
	}

	private Double obterValorFitness(final String[] valores) {
		return Double.valueOf(valores[2].replaceAll("\\.", "").replace(',', '.'));
	}

}
