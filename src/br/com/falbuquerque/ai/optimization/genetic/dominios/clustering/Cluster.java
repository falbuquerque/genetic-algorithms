package br.com.falbuquerque.ai.optimization.genetic.dominios.clustering;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

	private final List<DadoCluster> dados;
	private Integer qtdeElementosPorDado;
	private DadoCluster centroide;
	private Boolean encontrouCentroide;

	public Cluster() {
		this.dados = new ArrayList<>();
		this.encontrouCentroide = false;
	}

	public void adicionarDado(final DadoCluster dado) {
		this.dados.add(dado);
		this.encontrouCentroide = false;

		if (this.qtdeElementosPorDado == null) {
			this.qtdeElementosPorDado = dado.getElementos().size();
		}

	}

	public Double calcularVariacaoEntreDados() {
		this.encontrarCentroide();

		Double resultado = 0.0;

		for (final DadoCluster dado : this.dados) {
			final DadoCluster resultadoSubtracao = dado.subtrair(this.centroide);
			resultado += resultadoSubtracao.multiplicar(resultadoSubtracao);
		}

		return resultado;
	}

	private void encontrarCentroide() {

		if (!this.encontrouCentroide && !this.dados.isEmpty()) {
			final List<Double> elementosIniciais = new ArrayList<>(this.qtdeElementosPorDado);

			for (int i = 0; i < this.qtdeElementosPorDado; i++) {
				elementosIniciais.add(0.0);
			}

			this.centroide = new DadoCluster(elementosIniciais);
			this.centroide = this.centroide.somar(this.dados);
			this.centroide = this.centroide.dividir(this.dados.size());
			this.encontrouCentroide = true;
		}

	}

}
