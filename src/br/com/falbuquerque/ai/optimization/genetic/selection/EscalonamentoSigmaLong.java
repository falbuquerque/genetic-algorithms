package br.com.falbuquerque.ai.optimization.genetic.selection;

import java.util.Map;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class EscalonamentoSigmaLong<N extends Number> extends EscalonamentoSigma<N, Long> {

	public EscalonamentoSigmaLong(final Map<Individuo<N, Long>, Long> avaliacoes) {
		super(avaliacoes);
	}

	@Override
	protected Double calcularPercentual(final Long fitnessAtual, final Double media, final Double desvioPadrao) {
		return 1 + ((fitnessAtual - media) / (2 * desvioPadrao));
	}

}
