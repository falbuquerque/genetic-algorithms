package br.com.falbuquerque.ai.optimization.genetic.selecao;

public class WindowingDouble<N extends Number> extends Windowing<N, Double> {

	public WindowingDouble(final Double valorSubtrair) {
		super(valorSubtrair);
	}

	@Override
	protected Double subtrairDoFitness(final Double fitness) {
		return fitness - this.valorSubtrair;
	}

}
