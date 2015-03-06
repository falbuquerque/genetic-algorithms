package br.com.falbuquerque.ai.optimization.genetic.contexto;

public class ConjuntoTaxas {

	private Double intervaloGeracao;
	private Double taxaCruzamento;
	private Double taxaMutacao;

	public ConjuntoTaxas(final Double intervaloGeracao, final Double taxaCruzamento, final Double taxaMutacao) {
		this.intervaloGeracao = intervaloGeracao;
		this.taxaCruzamento = taxaCruzamento;
		this.taxaMutacao = taxaMutacao;
	}

	public ConjuntoTaxas(final ConjuntoTaxas taxasBase) {
		this.intervaloGeracao = taxasBase.getIntervaloGeracao();
		this.taxaCruzamento = taxasBase.getTaxaCruzamento();
		this.taxaMutacao = taxasBase.getTaxaMutacao();
	}

	public Double getIntervaloGeracao() {
		return intervaloGeracao;
	}

	public Double getTaxaCruzamento() {
		return taxaCruzamento;
	}

	public void setTaxaCruzamento(Double taxaCruzamento) {
		this.taxaCruzamento = taxaCruzamento;
	}

	public Double getTaxaMutacao() {
		return taxaMutacao;
	}

	public void setTaxaMutacao(Double taxaMutacao) {
		this.taxaMutacao = taxaMutacao;
	}

	public void incrementarTaxas(Double taxaModificacao) {
		this.taxaCruzamento += taxaModificacao;
		this.taxaMutacao += taxaModificacao;
	}

	public void decrementarTaxas(Double taxaModificacao) {
		this.taxaCruzamento -= taxaModificacao;
		this.taxaMutacao -= taxaModificacao;
	}

}
