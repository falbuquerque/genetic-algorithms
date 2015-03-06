package br.com.falbuquerque.ai.optimization.genetic.dominios;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.GeradorIndividuos;
import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.mutacao.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.ComportamentoReproducao;

public abstract class Dominio<N extends Number, S extends Number & Comparable<S>, W> {

	protected final Integer numMaxGeracoes;
	protected final Integer tamanhoCromossomo;
	protected final ContextoEvolucao<N, S> contextoEvolucao;
	protected final ComportamentoReproducao<N, S> comportamentoReproducao;
	protected final ControladorMutacoes<N, S> controladorMutacoes;
	protected final EstruturaPopulacao<N, S> estruturaPopulacao;
	private Boolean inicializouPopulacao;

	public Dominio(final Integer numMaxGeracoes, final Integer tamanhoCromossomo,
	        final ContextoEvolucao<N, S> contextoEvolucao, final ComportamentoReproducao<N, S> comportamentoReproducao,
	        final ControladorMutacoes<N, S> controladorMutacoes, final EstruturaPopulacao<N, S> estruturaPopulacao) {
		this.numMaxGeracoes = numMaxGeracoes;
		this.tamanhoCromossomo = tamanhoCromossomo;
		this.contextoEvolucao = contextoEvolucao;
		this.comportamentoReproducao = comportamentoReproducao;
		this.controladorMutacoes = controladorMutacoes;
		this.estruturaPopulacao = estruturaPopulacao;
		this.estruturaPopulacao.setDominio(this);
		this.inicializouPopulacao = false;
	}

	protected void inicializarPopulacao() {
		GeradorIndividuos<N, S> gerador = null;
		this.estruturaPopulacao.reinicializarPopulacao();

		gerador = new GeradorIndividuos<N, S>(this.contextoEvolucao.getAlfabeto(), this.tamanhoCromossomo,
		        this.comportamentoReproducao);

		while (!this.estruturaPopulacao.isPopulacaoCheia()) {
			this.estruturaPopulacao.adicionarIndividuo(gerador.criarIndividuoAleatoriamente());
		}

	}

	protected abstract S calcularFitness(Individuo<N, S> individuo);

	protected abstract W obterValorIndividuo(Individuo<N, S> individuo);

	protected Boolean isIndividuoValido(Individuo<N, S> individuo) {
		// A implementação padrão sugere que todos os indivíduos são válidos
		return true;
	}

	protected Boolean encontrouOtimoGlobal() {
		return false;
	}

	public Individuo<N, S> procurarMelhorIndividuo() {

		if (!this.inicializouPopulacao) {
			this.inicializarPopulacao();
		}

		this.avaliarPopulacao();

		while ((this.estruturaPopulacao.getGeracaoAtual() < this.numMaxGeracoes) && !this.encontrouOtimoGlobal()) {
			this.estruturaPopulacao.criarNovaGeracao(this.contextoEvolucao);
			this.avaliarPopulacao();
			this.contextoEvolucao.modificarTaxas(this.estruturaPopulacao);
		}

		return this.estruturaPopulacao.getMelhorIndividuo();
	}

	public void inicializarPopulacao(final List<Individuo<N, S>> individuos) {

		individuos.stream().parallel().forEach(individuo -> {
			individuo.setComportamentoReproducao(this.comportamentoReproducao);
			this.estruturaPopulacao.adicionarIndividuo(individuo);
		});

		this.inicializouPopulacao = true;
	}

	public void avaliarIndividuos(final List<Individuo<N, S>> individuos) {
		individuos.stream().parallel().forEach(individuo -> {
			individuo.setFitness(this.calcularFitness(individuo));
		});
	}

	private void avaliarPopulacao() {
		this.avaliarIndividuos(this.estruturaPopulacao.getIndividuos());
		this.estruturaPopulacao.receberAvaliacoes();
	}

}
