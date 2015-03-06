package br.com.falbuquerque.ai.optimization.genetic.alfabeto;

public abstract class IntervaloNumerico<N extends Number> {

	protected final N valorMinimo;
	protected final N valorMaximo;

	public IntervaloNumerico(final N valorMinimo, final N valorMaximo) {
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
	}

	public abstract N obterMenorValor(N valor1, N valor2);

	public abstract N obterMaiorValor(N valor1, N valor2);

	public abstract boolean isNumeroNoIntervalo(N numero);

	public abstract N sortearNumero();

	public N getValorMinimo() {
		return this.valorMinimo;
	}

	public N getValorMaximo() {
		return this.valorMaximo;
	}

}
