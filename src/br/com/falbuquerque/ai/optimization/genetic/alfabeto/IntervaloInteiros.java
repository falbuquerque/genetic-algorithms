package br.com.falbuquerque.ai.optimization.genetic.alfabeto;

import java.util.Random;

public class IntervaloInteiros extends IntervaloNumerico<Integer> {

	public IntervaloInteiros(final Integer valorMinimo, final Integer valorMaximo) {
		super(valorMinimo, valorMaximo);
	}

	@Override
	public boolean isNumeroNoIntervalo(final Integer numero) {
		return (numero.compareTo(valorMinimo) >= 0) && (numero.compareTo(valorMaximo) <= 0);
	}

	@Override
	public Integer obterMaiorValor(final Integer valor1, final Integer valor2) {

		if (valor1.compareTo(valor2) >= 0) {
			return valor1;
		}

		return valor2;
	}

	@Override
	public Integer obterMenorValor(final Integer valor1, final Integer valor2) {

		if (valor1.compareTo(valor2) <= 0) {
			return valor1;
		}

		return valor2;
	}

	@Override
	public Integer sortearNumero() {
		return new Random().nextInt(this.valorMaximo + 1);
	}

}
