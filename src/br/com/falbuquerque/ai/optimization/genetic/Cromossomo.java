package br.com.falbuquerque.ai.optimization.genetic;

import java.util.ArrayList;
import java.util.List;

public class Cromossomo<N extends Number> {

	private final Integer tamanhoCromossomo;
	private final List<N> genotipo;

	public Cromossomo(final List<N> genotipo) {
		this.tamanhoCromossomo = genotipo.size();

		if (genotipo.isEmpty()) {
			throw new IllegalArgumentException("O tamanho do cromossomo deve ser maior que 0");
		}

		this.genotipo = genotipo;
	}

	public Cromossomo(final Cromossomo<N> origem) {
		this.tamanhoCromossomo = origem.tamanhoCromossomo;
		this.genotipo = new ArrayList<>(origem.tamanhoCromossomo);
		origem.genotipo.forEach(gene -> this.genotipo.add(gene));
	}

	public void alterarGene(final int posicaoGene, final N novoValor) {
		this.genotipo.remove(posicaoGene);
		this.genotipo.add(posicaoGene, novoValor);
	}

	public List<N> getGenotipo() {
		return this.genotipo;
	}

	public Integer getTamanhoCromossomo() {
		return this.tamanhoCromossomo;
	}

	@Override
	public String toString() {
		return this.genotipo.toString();
	}

}
