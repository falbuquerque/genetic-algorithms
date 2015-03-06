package br.com.falbuquerque.ai.optimization.genetic.mutacao;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;

public class ControladorMutacoes<N extends Number, S extends Number & Comparable<S>> {

	private final Boolean aplicarMutacaoDirigida;
	private final Integer geracaoInicioVerificacao;
	private final Integer qtdeGeracoesVerificacao;
	private final Integer qtdeIndividuosSelecionados;
	private Integer qtdeGeracoesVerificadasAteAgora;
	private ComportamentoMutacao<N, S> comportamentoMutacao;
	private Integer geracoesSemAplicarMutacaoDirigida;

	public ControladorMutacoes(final Boolean aplicarMutacaoDirigida, final Integer geracaoInicioVerificacao,
	        final Integer qtdeGeracoesVerificacao, final Integer qtdeIndividuosSelecionados) {
		this.aplicarMutacaoDirigida = aplicarMutacaoDirigida;
		this.geracaoInicioVerificacao = geracaoInicioVerificacao;
		this.qtdeGeracoesVerificacao = qtdeGeracoesVerificacao;
		this.qtdeIndividuosSelecionados = qtdeIndividuosSelecionados;
		this.qtdeGeracoesVerificadasAteAgora = 0;
		this.geracoesSemAplicarMutacaoDirigida = 0;
		this.comportamentoMutacao = new MutacaoSimples<>();
	}

	private Boolean isMutacaoDirigidaAplicavel(final EstruturaPopulacao<N, S> populacao, final Integer geracaoAtual,
	        final Boolean isPopulacaoConvergindo) {
		return this.aplicarMutacaoDirigida

		// est� proibido de aplicar muta��o dirigida?
		        && (this.geracoesSemAplicarMutacaoDirigida == 0) && isPopulacaoConvergindo

		        // est� na gera��o m�nima para aplicar muta��o dirigida?
		        && (geracaoAtual.compareTo(this.geracaoInicioVerificacao) >= 0)

		        // chegou ao final da janela de muta��o dirigida?
		        && (this.qtdeGeracoesVerificadasAteAgora.compareTo(this.qtdeGeracoesVerificacao) <= 0);
	}

	/**
	 * Aplica a muta��o � popula��o. Pode ser muta��o simples ou dirigida no
	 * esquema dominante, dependendo do estado da popula��o atual.
	 * 
	 * @param populacao
	 *            a popula��o
	 * @param contextoEvolucao
	 */
	public void aplicarMutacao(final EstruturaPopulacao<N, S> populacao, final ContextoEvolucao<N, S> contextoEvolucao) {
		List<Individuo<N, S>> candidatosMutacao = null;

		if (this.geracoesSemAplicarMutacaoDirigida > 0) {
			this.geracoesSemAplicarMutacaoDirigida--;
		}

		if (this.isMutacaoDirigidaAplicavel(populacao, populacao.getGeracaoAtual(),
		        contextoEvolucao.isPopulacaoConvergindo(populacao))) {
			this.utilizarParametrosMutacaoDirigidaEsquemaDominante(contextoEvolucao);

			// Incrementa o n�mero de gera��es que sofreram a muta��o dirigida
			this.qtdeGeracoesVerificadasAteAgora++;

			// Se estiver na �ltima gera��o de verifica��o, define por quantas
			// gera��es a muta��o dirigida n�o ser� aplicada
			if (this.qtdeGeracoesVerificadasAteAgora.equals(this.qtdeGeracoesVerificacao)) {
				this.geracoesSemAplicarMutacaoDirigida = (this.qtdeGeracoesVerificacao * 2);
			}

			// Os candidatos � muta��o s�o os N melhores indiv�duos
			candidatosMutacao = contextoEvolucao.getTipoProblema().obterNMelhoresIndividuos(populacao.getIndividuos(),
			        this.qtdeIndividuosSelecionados);
		} else {
			this.utilizarParametrosMutacaoSimples(contextoEvolucao);

			if (this.qtdeGeracoesVerificadasAteAgora > 0) {
				this.qtdeGeracoesVerificadasAteAgora = 0;
			}

			// Os candidatos � muta��o s�o os filhos da gera��o atual
			candidatosMutacao = populacao.getFilhosGeracalAtual();
		}

		// Aplica a muta��o
		if (contextoEvolucao.isMutacaoAplicavel()) {
			this.comportamentoMutacao.aplicarMutacao(candidatosMutacao, contextoEvolucao);
		}

	}

	public void aplicarMutacaoSimples(final List<Individuo<N, S>> individuos, final ContextoEvolucao<N, S> contextoEvolucao) {
		this.utilizarParametrosMutacaoSimples(contextoEvolucao);

		if (contextoEvolucao.isMutacaoAplicavel()) {
			this.comportamentoMutacao.aplicarMutacao(individuos, contextoEvolucao);
		}

	}

	private void utilizarParametrosMutacaoSimples(final ContextoEvolucao<N, S> contextoEvolucao) {

		// Se estiver aplicando muta��o dirigida, muda para simples
		if (this.comportamentoMutacao instanceof MutacaoDirigidaEsquemaDominante) {
			this.comportamentoMutacao = new MutacaoSimples<>();
			contextoEvolucao.utilizarTaxasMutacaoSimples();
		}

	}

	private void utilizarParametrosMutacaoDirigidaEsquemaDominante(final ContextoEvolucao<N, S> contextoEvolucao) {

		// Se estiver aplicando muta��o simples, muda para dirigida
		if (this.comportamentoMutacao instanceof MutacaoSimples) {
			this.comportamentoMutacao = new MutacaoDirigidaEsquemaDominante<N, S>(this.qtdeIndividuosSelecionados);

			// Utiliza as taxas da muta��o dirigida
			contextoEvolucao.utilizarTaxasMutacaoDirigidaEsquemaDominante();
		}

	}

}
