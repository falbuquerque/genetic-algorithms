package br.com.falbuquerque.ai.optimization.genetic.alphabet;

public class AlfabetoClustering extends Alfabeto<Integer> {

	@SafeVarargs
	public AlfabetoClustering(final IntervaloNumerico<Integer>... intervalos) {
		super(intervalos);
	}

	@Override
	public Boolean isGeneValido(final Integer gene) {
		return super.isGeneValido(gene) && (gene != 0);
	}

	@Override
	public Integer sortearNumero() {
		final Integer numSorteado = super.sortearNumero();

		if (!isGeneValido(numSorteado)) {
			return this.sortearNumero();
		}

		return numSorteado;
	}

}
