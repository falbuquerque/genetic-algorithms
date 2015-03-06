package br.com.falbuquerque.ai.optimization.genetic.selection;

public class WindowingLong<N extends Number> extends Windowing<N, Long> {

	public WindowingLong(final Long valorSubtrair) {
		super(valorSubtrair);
	}

	@Override
	protected Long subtrairDoFitness(final Long fitness) {
		return fitness - this.valorSubtrair;
	}

}
