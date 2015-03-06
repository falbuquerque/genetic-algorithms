package br.com.falbuquerque.ai.optimization.genetic.dominios.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DadoCluster {

	private final List<Double> elementos;

	public DadoCluster(final List<Double> elementos) {
		this.elementos = elementos;
	}

	@SafeVarargs
	public <N extends Number> DadoCluster(final N... elementos) {
		this.elementos = new ArrayList<>(elementos.length);

		for (final N elemento : elementos) {
			this.elementos.add(elemento.doubleValue());
		}

	}

	public DadoCluster somar(final DadoCluster dado) {
		final List<Double> elementosResultado = new ArrayList<>(this.elementos);
		int posElementoAtual = 0;

		for (final Double elemento : dado.elementos) {
			final Double soma = elemento + elementosResultado.get(posElementoAtual);
			elementosResultado.set(posElementoAtual, soma);
			posElementoAtual++;
		}

		return new DadoCluster(elementosResultado);
	}

	public DadoCluster somar(final List<DadoCluster> dados) {
		final List<Double> elementosResultado = new ArrayList<>(this.elementos);
		dados.forEach(dado -> {
			int posElementoAtual = 0;

			for (Double elemento : dado.elementos) {
				Double soma = elemento + elementosResultado.get(posElementoAtual);
				elementosResultado.set(posElementoAtual, soma);
				posElementoAtual++;
			}

		});

		return new DadoCluster(elementosResultado);
	}

	public DadoCluster subtrair(final List<DadoCluster> dados) {
		final List<DadoCluster> dadosSinalInvertido = new ArrayList<>(dados.size());
		dados.forEach(dado -> dadosSinalInvertido.add(dado.inverterSinais()));
		return this.somar(dadosSinalInvertido);
	}

	public DadoCluster subtrair(final DadoCluster dado) {
		return this.somar(dado.inverterSinais());
	}

	public Double multiplicar(final DadoCluster dado) {
		int posElementoAtual = 0;
		Double resultado = 0.0;

		for (final Double elemento : dado.elementos) {
			resultado += elemento * this.elementos.get(posElementoAtual);
			posElementoAtual++;
		}

		return resultado;
	}

	public DadoCluster dividir(final Integer n) {
		final List<Double> elementosResultado = new ArrayList<>(this.elementos.size());
		this.elementos.forEach(elemento -> elementosResultado.add(elemento / n));
		return new DadoCluster(elementosResultado);
	}

	public DadoCluster inverterSinais() {
		final List<Double> elementosResultado = new ArrayList<>(this.elementos.size());
		this.elementos.forEach(elemento -> elementosResultado.add(elemento * -1));
		return new DadoCluster(elementosResultado);
	}

	public List<Double> getElementos() {
		return Collections.unmodifiableList(this.elementos);
	}

}
