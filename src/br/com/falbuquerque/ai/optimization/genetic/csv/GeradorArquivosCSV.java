package br.com.falbuquerque.ai.optimization.genetic.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;

public abstract class GeradorArquivosCSV {

	protected final String conteudoCabecalho;
	protected final NumberFormat formatadorValores;
	protected StringBuilder conteudo;

	public GeradorArquivosCSV(final Integer numCasasDecimais, final String conteudoCabecalho) {
		this.conteudoCabecalho = conteudoCabecalho;
		this.conteudo = new StringBuilder(2000);
		this.conteudo.append(conteudoCabecalho);

		this.formatadorValores = NumberFormat.getInstance(new Locale("pt", "BR"));
		this.formatadorValores.setMinimumFractionDigits(numCasasDecimais);
		this.formatadorValores.setMaximumFractionDigits(numCasasDecimais);
		this.formatadorValores.setGroupingUsed(true);
	}

	public void criarArquivo(final String diretorio, final String nomeArquivo) throws IOException {
		final File localDiretorio = new File(diretorio);

		if (!localDiretorio.exists()) {
			localDiretorio.mkdirs();
		}

		final File arquivoCSV = new File(diretorio + System.getProperty("file.separator") + nomeArquivo + ".csv");

		final PrintWriter escritorArquivo = new PrintWriter(new FileWriter(arquivoCSV));
		escritorArquivo.print(this.conteudo);
		escritorArquivo.flush();
		escritorArquivo.close();
	}

	public void clear() {
		this.conteudo = new StringBuilder(2000);
		this.conteudo.append(this.conteudoCabecalho);
	}

}
