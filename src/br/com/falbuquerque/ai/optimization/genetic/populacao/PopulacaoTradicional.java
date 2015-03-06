package br.com.falbuquerque.ai.optimization.genetic.populacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.mutacao.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.selecao.MetodoSelecao;
import br.com.falbuquerque.ai.optimization.genetic.selecao.Roleta;

public class PopulacaoTradicional<N extends Number, S extends Number & Comparable<S>> extends PopulacaoGenerica<N, S> {

	private final MetodoSelecao<N, S> metodoSelecao;
	private final Integer tamanhoPopulacao;
	private final Integer execucao;
	private final Boolean salvarRoleta;
	private List<Individuo<N, S>> populacao;

	public PopulacaoTradicional(final Integer tamanhoPopulacao, final ControladorMutacoes<N, S> controladorMutacoes,
	        final MetodoSelecao<N, S> metodoSelecao) {
		this(tamanhoPopulacao, controladorMutacoes, metodoSelecao, 999, false);
	}

	public PopulacaoTradicional(final Integer tamanhoPopulacao, final ControladorMutacoes<N, S> controladorMutacoes,
	        final MetodoSelecao<N, S> metodoSelecao, final Integer execucao, final Boolean salvarRoleta) {
		super(controladorMutacoes);
		this.tamanhoPopulacao = tamanhoPopulacao;
		this.metodoSelecao = metodoSelecao;
		this.populacao = new ArrayList<Individuo<N, S>>(this.tamanhoPopulacao);
		this.execucao = execucao;
		this.salvarRoleta = salvarRoleta;
	}

	@Override
	public Boolean adicionarIndividuo(final Individuo<N, S> individuo) {

		if (!this.isPopulacaoCheia()) {
			this.populacao.add(individuo);
			return true;
		}

		return false;
	}

	@Override
	protected void renovarPopulacao(final ContextoEvolucao<N, S> contextoEvolucao) {

		if (this.populacao == null) {
			throw new IllegalStateException("A população deve ser inicializada " + "antes da criação de uma nova geração");
		}

		// Calcula o total de indivíduos que sobrevivem e o total de indivíduos
		// que
		// serão substituídos
		final Integer totalNovosIndividuos = (int) (this.tamanhoPopulacao * contextoEvolucao.getIntervaloGeracao());
		final Integer totalSobreviventes = this.tamanhoPopulacao - totalNovosIndividuos;

		// Criação de uma nova população
		final List<Individuo<N, S>> novaPopulacao = new ArrayList<>(totalNovosIndividuos);
		this.filhosGeracaoAtual = new ArrayList<>();

		while (novaPopulacao.size() < totalNovosIndividuos) {
			List<Individuo<N, S>> filhos = Collections.emptyList();

			// Sorteia dois indivíduos, dentre os sobreviventes, para fazer
			// crossover
			final Individuo<N, S> pai = this.metodoSelecao.obterProximoIndividuo();
			final Individuo<N, S> mae = this.metodoSelecao.obterProximoIndividuo();

			if (contextoEvolucao.isCrossoverAplicavel()) {
				filhos = pai.reproduzirSexuadamente(mae);
			}

			filhos.forEach(filho -> {
				novaPopulacao.add(filho);
				this.filhosGeracaoAtual.add(filho);
			});

		}

		// Completa a lista com os indivíduos mais adaptados
		novaPopulacao.addAll(contextoEvolucao.getTipoProblema().obterNMelhoresIndividuos(this.populacao, totalSobreviventes));

		this.populacao = novaPopulacao;
	}

	@Override
	public List<Individuo<N, S>> getIndividuos() {
		return this.populacao;
	}

	@Override
	public Individuo<N, S> getMelhorIndividuo() {
		return this.metodoSelecao.getMelhorIndividuo();
	}

	@Override
	public Boolean isPopulacaoCheia() {
		return (this.populacao.size() == this.tamanhoPopulacao);
	}

	@Override
	public void reinicializarPopulacao() {
		this.populacao = new ArrayList<>(this.tamanhoPopulacao);
	}

	@Override
	public void receberAvaliacoes() {

		if (this.salvarRoleta) {

			if (this.metodoSelecao instanceof Roleta) {

				try {
					((Roleta<N, S>) this.metodoSelecao).criarArquivo("/dev/tmp/ai/files/roulettes", "roleta_exec"
					        + this.execucao + "_ger" + this.geracaoAtual);
				} catch (final IOException e) {
					e.printStackTrace();
				}

			}

		}

		this.metodoSelecao.reiniciar();
		this.getIndividuos().forEach(individuo -> this.metodoSelecao.adicionarAvaliacao(individuo, individuo.getFitness()));
	}

}
