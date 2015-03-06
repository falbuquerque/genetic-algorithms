package br.com.falbuquerque.ai.optimization.genetic.selecao;

import java.util.Map;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class RoletaIntegerDouble extends Roleta<Integer, Double> {

	public RoletaIntegerDouble(final TipoProblema<Double> tipoProblema, final Boolean aplicarEscalonamentoSigma,
	        final Windowing<Integer, Double> windowing) {
		super(tipoProblema, aplicarEscalonamentoSigma, windowing);
		this.fitnessTotal = 0.0;
	}

	public RoletaIntegerDouble(final TipoProblema<Double> tipoProblema, final Boolean aplicarEscalonamentoSigma) {
		this(tipoProblema, aplicarEscalonamentoSigma, null);
	}

	public RoletaIntegerDouble(final TipoProblema<Double> tipoProblema, final Windowing<Integer, Double> windowing) {
		this(tipoProblema, false, windowing);
	}

	public RoletaIntegerDouble(final TipoProblema<Double> tipoProblema) {
		this(tipoProblema, false, null);
	}

	@Override
	protected void incrementarFitnessTotal(final Double fitness) {
		this.fitnessTotal += fitness;
	}

	@Override
	public void reiniciar() {
		super.reiniciar();
		this.fitnessTotal = 0.0;
	}

	@Override
	protected EscalonamentoSigma<Integer, Double> getEscalonamentoSigma(
	        final Map<Individuo<Integer, Double>, Double> avaliacoes) {
		return new EscalonamentoSigmaDouble<>(avaliacoes);
	}

}
