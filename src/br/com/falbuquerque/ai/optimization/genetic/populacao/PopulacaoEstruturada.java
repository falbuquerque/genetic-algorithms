package br.com.falbuquerque.ai.optimization.genetic.populacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.mutacao.ControladorMutacoes;

/**
 * Organiza os <i>crossovers</i> e mutações de uma população estruturada em uma
 * árvore ternária com três níveis. Cada nó da árvore tem um agente, que contém
 * um indivíduo principal (<i>pocket</i>) e um gerado após o <i>crossover</i>
 * com seu agente líder (<i>current</i>).<br>
 * Após a organização da população, os <i>pockets</i> dos agentes são sempre
 * melhores que os <i>currents</i>, e os <i>pockets</i> dos agentes líderes são
 * sempre melhores que os dos subordinados.<br>
 * Os <i>crossovers</i> são realizados sempre entre líderes e subordinados, e os
 * filhos resultados serão guardados como <i>current</i> de cada subordinado.
 */
public class PopulacaoEstruturada<N extends Number, S extends Number & Comparable<S>> extends PopulacaoGenerica<N, S> {

	private static final Integer MAX_AGENTES = 13;

	private final Random sorteador;
	private Agente<N, S> agenteLider;

	public PopulacaoEstruturada(final ControladorMutacoes<N, S> controladorMutacoes) {
		super(controladorMutacoes);
		this.sorteador = new Random();
	}

	@Override
	public Boolean adicionarIndividuo(final Individuo<N, S> individuo) {
		final Agente<N, S> agente = new Agente<>(individuo);

		if (this.agenteLider == null) {
			this.agenteLider = new Agente<>(individuo);
			return true;
		}

		return this.agenteLider.adicionarComoSubordinado(agente);
	}

	@Override
	public void renovarPopulacao(final ContextoEvolucao<N, S> contextoEvolucao) {
		this.efetuarReproducao(this.agenteLider);
	}

	@Override
	public List<Individuo<N, S>> getIndividuos() {
		final List<Individuo<N, S>> todosIndividuos = this.getIndividuosPocket();
		todosIndividuos.addAll(this.getIndividuosCurrent());
		return todosIndividuos;
	}

	@Override
	public Individuo<N, S> getMelhorIndividuo() {
		return this.agenteLider.getPocket();
	}

	@Override
	public Boolean isPopulacaoCheia() {

		if (this.agenteLider == null) {
			return false;
		}

		return (this.agenteLider.getQtdeAgentesSubordinados() == MAX_AGENTES);
	}

	@Override
	public void reinicializarPopulacao() {
		this.agenteLider = null;
	}

	@Override
	public void receberAvaliacoes() {
		this.agenteLider.organizarPocketsComSubordinados();
	}

	@Override
	protected void aplicarMutacoes(final ContextoEvolucao<N, S> contextoEvolucao) {

		// No caso da população estruturada, a mutação é sempre simples,
		// realizada em cima dos indivíduos pocket dos agentes
		this.controladorMutacoes.aplicarMutacaoSimples(this.getIndividuosPocket(), contextoEvolucao);
	}

	private void efetuarReproducao(final Agente<N, S> agente) {
		this.filhosGeracaoAtual = new ArrayList<>();
		agente.getSubordinados().forEach(agenteSubordinado -> {
			final List<Individuo<N, S>> filhos = agente.getPocket().reproduzirSexuadamente(agenteSubordinado.getPocket());
			final Individuo<N, S> filhoEscolhido = filhos.get(this.sorteador.nextInt(filhos.size()));

			this.filhosGeracaoAtual.add(filhoEscolhido);
			agenteSubordinado.setCurrent(filhoEscolhido);

			// Efetua a reprodução do agente subordinado, de maneira recursiva
			    this.efetuarReproducao(agenteSubordinado);
		    });
	}

	private List<Individuo<N, S>> getIndividuosPocket() {
		return this.getIndividuosPocket(this.agenteLider);
	}

	private List<Individuo<N, S>> getIndividuosPocket(final Agente<N, S> agente) {
		final List<Individuo<N, S>> individuos = new ArrayList<>(MAX_AGENTES);
		individuos.add(agente.getPocket());

		agente.getSubordinados().stream().parallel()
		        .forEach(agenteSubordinado -> individuos.addAll(this.getIndividuosPocket(agenteSubordinado)));

		return individuos;
	}

	private List<Individuo<N, S>> getIndividuosCurrent() {
		return this.getIndividuosCurrent(this.agenteLider);
	}

	private List<Individuo<N, S>> getIndividuosCurrent(final Agente<N, S> agente) {
		final List<Individuo<N, S>> individuos = new ArrayList<>(MAX_AGENTES);

		if (agente.getCurrent() != null) {
			individuos.add(agente.getCurrent());
		}

		// Obtém os indivíduos dos agentes subordinados de maneira recursiva
		agente.getSubordinados().stream().parallel()
		        .forEach(agenteSubordinado -> individuos.addAll(this.getIndividuosCurrent(agenteSubordinado)));

		return individuos;
	}

}
