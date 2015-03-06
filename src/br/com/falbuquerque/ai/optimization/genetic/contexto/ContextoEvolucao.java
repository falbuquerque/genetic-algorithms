package br.com.falbuquerque.ai.optimization.genetic.contexto;

import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.alfabeto.Alfabeto;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.selecao.TipoProblema;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergencia;

public abstract class ContextoEvolucao<N extends Number, S extends Number & Comparable<S>> {

	protected final ConjuntoTaxas conjuntoTaxasGerais;
	private final ConjuntoTaxas conjuntoTaxasMutacaoDirigida;
	private final Alfabeto<N> alfabeto;
	private final TipoProblema<S> tipoProblema;
	private final VerificadorConvergencia<N> verificadorConvergencia;
	private final Random sorteadorTaxas;
	private Boolean utilizarTaxasMutacaoDirigida;

	public ContextoEvolucao(final ConjuntoTaxas conjuntoTaxasGerais, final ConjuntoTaxas conjuntoTaxasMutacaoDirigida,
	        final Alfabeto<N> alfabeto, final TipoProblema<S> tipoProblema,
	        final VerificadorConvergencia<N> verificadorConvergencia) {
		this.conjuntoTaxasGerais = conjuntoTaxasGerais;
		this.conjuntoTaxasMutacaoDirigida = conjuntoTaxasMutacaoDirigida;
		this.alfabeto = alfabeto;
		this.tipoProblema = tipoProblema;
		this.utilizarTaxasMutacaoDirigida = false;
		this.verificadorConvergencia = verificadorConvergencia;
		this.sorteadorTaxas = new Random();
	}

	public abstract void modificarTaxas(EstruturaPopulacao<N, S> estruturaPopulacao);

	public boolean isCrossoverAplicavel() {
		Double taxaCruzamento = null;

		if (this.utilizarTaxasMutacaoDirigida) {
			taxaCruzamento = this.conjuntoTaxasMutacaoDirigida.getTaxaCruzamento();
		} else {
			taxaCruzamento = this.conjuntoTaxasGerais.getTaxaCruzamento();
		}

		return (this.sorteadorTaxas.nextDouble() <= taxaCruzamento);
	}

	public boolean isMutacaoAplicavel() {
		Double taxaMutacao = null;

		if (this.utilizarTaxasMutacaoDirigida) {
			taxaMutacao = this.conjuntoTaxasMutacaoDirigida.getTaxaMutacao();
		} else {
			taxaMutacao = this.conjuntoTaxasGerais.getTaxaMutacao();
		}

		return (this.sorteadorTaxas.nextDouble() <= taxaMutacao);
	}

	public Alfabeto<N> getAlfabeto() {
		return alfabeto;
	}

	public TipoProblema<S> getTipoProblema() {
		return tipoProblema;
	}

	public Double getIntervaloGeracao() {
		return this.conjuntoTaxasGerais.getIntervaloGeracao();
	}

	public void utilizarTaxasMutacaoDirigidaEsquemaDominante() {
		this.utilizarTaxasMutacaoDirigida = true;
	}

	public void utilizarTaxasMutacaoSimples() {
		this.utilizarTaxasMutacaoDirigida = false;
	}

	public Boolean isPopulacaoConvergindo(EstruturaPopulacao<N, S> estruturaPopulacao) {
		return this.verificadorConvergencia.isPopulacaoConvergindo(estruturaPopulacao);
	}

}