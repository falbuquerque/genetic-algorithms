package br.com.falbuquerque.ai.optimization.genetic.selecao;

import java.util.Map;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class RoletaEquacaoQuadratica extends Roleta<Integer, Long> {

	public RoletaEquacaoQuadratica(final TipoProblema<Long> tipoProblema, final Boolean aplicarEscalonamentoSigma,
	        final Windowing<Integer, Long> windowing) {
		super(tipoProblema, aplicarEscalonamentoSigma, windowing);
		this.fitnessTotal = 0L;
	}

	public RoletaEquacaoQuadratica(final TipoProblema<Long> tipoProblema, final Boolean aplicarEscalonamentoSigma) {
		this(tipoProblema, aplicarEscalonamentoSigma, null);
	}

	public RoletaEquacaoQuadratica(final TipoProblema<Long> tipoProblema, final Windowing<Integer, Long> windowing) {
		this(tipoProblema, false, windowing);
	}

	public RoletaEquacaoQuadratica(final TipoProblema<Long> tipoProblema) {
		this(tipoProblema, false, null);
	}

	@Override
	protected void incrementarFitnessTotal(final Long fitness) {
		this.fitnessTotal += fitness;
	}

	@Override
	public void reiniciar() {
		super.reiniciar();
		this.fitnessTotal = 0L;
	}

	@Override
	protected EscalonamentoSigma<Integer, Long> getEscalonamentoSigma(final Map<Individuo<Integer, Long>, Long> avaliacoes) {
		return new EscalonamentoSigmaLong<>(avaliacoes);
	}

}
