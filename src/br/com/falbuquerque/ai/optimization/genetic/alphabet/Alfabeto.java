package br.com.falbuquerque.ai.optimization.genetic.alphabet;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Alfabeto<N extends Number> {

	protected final Random sorteador;
	private final List<IntervaloNumerico<N>> intervalosAceitos;

	@SafeVarargs
    public Alfabeto(final IntervaloNumerico<N>... intervalos) {
		this.intervalosAceitos = Arrays.asList(intervalos);
		this.sorteador = new Random();
	}

	public Boolean isGeneValido(final N gene) {
		boolean geneValido = false;

		for (final IntervaloNumerico<N> intervalo : intervalosAceitos) {
			geneValido |= intervalo.isNumeroNoIntervalo(gene);

			if (geneValido) {
				break;
			}

		}

		return geneValido;
	}

	public List<IntervaloNumerico<N>> getIntervalosAceitos() {
		return this.intervalosAceitos;
	}

	public N sortearNumero() {
		int intervaloConsiderado = this.sorteador.nextInt(this.intervalosAceitos.size());
		return this.intervalosAceitos.get(intervaloConsiderado).sortearNumero();
	}

}
