package br.com.falbuquerque.ai.optimization.genetic.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import br.com.falbuquerque.ai.optimization.genetic.domains.clustering.DadoCluster;

public class LeitorArquivosCSV {

	private String caminhoCompleto;

	public LeitorArquivosCSV(final String diretorio, final String arquivo) {
		this.caminhoCompleto = diretorio + System.getProperty("file.separator") + arquivo;
	}

	public List<DadoCluster> gerarDadosCluster() {
		final List<DadoCluster> dados = new LinkedList<>();
		final Scanner scanner;

		try {
			scanner = new Scanner(new File(this.caminhoCompleto));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("O arquivo especificado não existe");
		}

		while (scanner.hasNext()) {
			final String[] valores = scanner.nextLine().split(";");
			final List<Double> elementos = new ArrayList<>(valores.length);

			for (final String valor : valores) {
				elementos.add(Double.valueOf(valor));
			}

			dados.add(new DadoCluster(elementos));
		}

		scanner.close();

		return dados;
	}

}
