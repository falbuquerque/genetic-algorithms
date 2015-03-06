package br.com.falbuquerque.ai.optimization.genetic.selection;

import java.util.Map;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class EscalonamentoSigmaDouble<N extends Number> extends EscalonamentoSigma<N, Double> {

	public EscalonamentoSigmaDouble(final Map<Individuo<N, Double>, Double> avaliacoes) {
		super(avaliacoes);
	}

	@Override
	protected Double calcularPercentual(final Double fitnessAtual, final Double media, final Double desvioPadrao) {
		return 1 + ((fitnessAtual - media) / (2 * desvioPadrao));
	}

}
