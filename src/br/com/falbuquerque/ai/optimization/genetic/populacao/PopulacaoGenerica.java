package br.com.falbuquerque.ai.optimization.genetic.populacao;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.dominios.Dominio;
import br.com.falbuquerque.ai.optimization.genetic.mutacao.ControladorMutacoes;

public abstract class PopulacaoGenerica<N extends Number, S extends Number & Comparable<S>> implements
        EstruturaPopulacao<N, S> {

	protected final ControladorMutacoes<N, S> controladorMutacoes;
	protected Integer geracaoAtual;
	protected List<Individuo<N, S>> filhosGeracaoAtual;
	protected Dominio<N, S, ?> dominio;

	public PopulacaoGenerica(final ControladorMutacoes<N, S> controladorMutacoes) {
		this.controladorMutacoes = controladorMutacoes;
		this.geracaoAtual = 0;
	}

	protected abstract void renovarPopulacao(ContextoEvolucao<N, S> contextoEvolucao);

	@Override
	public Integer getGeracaoAtual() {
		return this.geracaoAtual;
	}

	@Override
	public void criarNovaGeracao(final ContextoEvolucao<N, S> contextoEvolucao) {
		this.incrementarGeracaoAtual();
		this.renovarPopulacao(contextoEvolucao);

		if (this.dominio != null) {
			this.dominio.avaliarIndividuos(this.filhosGeracaoAtual);
		}

		this.aplicarMutacoes(contextoEvolucao);
	}

	@Override
	public List<Individuo<N, S>> getFilhosGeracalAtual() {
		return this.filhosGeracaoAtual;
	}

	@Override
	public void setDominio(final Dominio<N, S, ?> dominio) {
		this.dominio = dominio;
	}

	protected void aplicarMutacoes(final ContextoEvolucao<N, S> contextoEvolucao) {
		this.controladorMutacoes.aplicarMutacao(this, contextoEvolucao);
	}

	private void incrementarGeracaoAtual() {
		this.geracaoAtual++;
	}

}
