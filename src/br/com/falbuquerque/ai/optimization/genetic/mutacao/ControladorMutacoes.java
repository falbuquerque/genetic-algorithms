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

		// está proibido de aplicar mutação dirigida?
		        && (this.geracoesSemAplicarMutacaoDirigida == 0) && isPopulacaoConvergindo

		        // está na geração mínima para aplicar mutação dirigida?
		        && (geracaoAtual.compareTo(this.geracaoInicioVerificacao) >= 0)

		        // chegou ao final da janela de mutação dirigida?
		        && (this.qtdeGeracoesVerificadasAteAgora.compareTo(this.qtdeGeracoesVerificacao) <= 0);
	}

	/**
	 * Aplica a mutação à população. Pode ser mutação simples ou dirigida no
	 * esquema dominante, dependendo do estado da população atual.
	 * 
	 * @param populacao
	 *            a população
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

			// Incrementa o número de gerações que sofreram a mutação dirigida
			this.qtdeGeracoesVerificadasAteAgora++;

			// Se estiver na última geração de verificação, define por quantas
			// gerações a mutação dirigida não será aplicada
			if (this.qtdeGeracoesVerificadasAteAgora.equals(this.qtdeGeracoesVerificacao)) {
				this.geracoesSemAplicarMutacaoDirigida = (this.qtdeGeracoesVerificacao * 2);
			}

			// Os candidatos à mutação são os N melhores indivíduos
			candidatosMutacao = contextoEvolucao.getTipoProblema().obterNMelhoresIndividuos(populacao.getIndividuos(),
			        this.qtdeIndividuosSelecionados);
		} else {
			this.utilizarParametrosMutacaoSimples(contextoEvolucao);

			if (this.qtdeGeracoesVerificadasAteAgora > 0) {
				this.qtdeGeracoesVerificadasAteAgora = 0;
			}

			// Os candidatos à mutação são os filhos da geração atual
			candidatosMutacao = populacao.getFilhosGeracalAtual();
		}

		// Aplica a mutação
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

		// Se estiver aplicando mutação dirigida, muda para simples
		if (this.comportamentoMutacao instanceof MutacaoDirigidaEsquemaDominante) {
			this.comportamentoMutacao = new MutacaoSimples<>();
			contextoEvolucao.utilizarTaxasMutacaoSimples();
		}

	}

	private void utilizarParametrosMutacaoDirigidaEsquemaDominante(final ContextoEvolucao<N, S> contextoEvolucao) {

		// Se estiver aplicando mutação simples, muda para dirigida
		if (this.comportamentoMutacao instanceof MutacaoSimples) {
			this.comportamentoMutacao = new MutacaoDirigidaEsquemaDominante<N, S>(this.qtdeIndividuosSelecionados);

			// Utiliza as taxas da mutação dirigida
			contextoEvolucao.utilizarTaxasMutacaoDirigidaEsquemaDominante();
		}

	}

}
