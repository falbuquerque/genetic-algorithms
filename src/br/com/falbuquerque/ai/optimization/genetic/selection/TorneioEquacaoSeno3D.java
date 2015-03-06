package br.com.falbuquerque.ai.optimization.genetic.selection;

public class TorneioEquacaoSeno3D extends Torneio<Integer, Double> {

	public TorneioEquacaoSeno3D(final TipoProblema<Double> tipoProblema, final Float limiar) {
		super(tipoProblema, limiar);
		this.fitnessTotal = 0.0;
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

}
