package br.com.falbuquerque.ai.optimization.genetic.selecao;

public class TorneioEquacaoQuadratica extends Torneio<Integer, Long> {

	public TorneioEquacaoQuadratica(final TipoProblema<Long> tipoProblema, final Float limiar) {
		super(tipoProblema, limiar);
		this.fitnessTotal = 0L;
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

}
