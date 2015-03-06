package br.com.falbuquerque.ai.optimization.genetic.mutacao;

import java.util.List;
import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;

public class MutacaoSimples<N extends Number, S extends Number & Comparable<S>> implements ComportamentoMutacao<N, S> {
	private final Random sorteador = new Random();

	@Override
	public void aplicarMutacao(final List<Individuo<N, S>> individuos, final ContextoEvolucao<N, S> contextoEvolucao) {

		if (!individuos.isEmpty() && contextoEvolucao.isMutacaoAplicavel()) {
			final int posicaoAlterada = 0;
			final Individuo<N, S> individuoSorteado = individuos.get(sorteador.nextInt(individuos.size()));
			individuoSorteado.alterarGenotipo(posicaoAlterada, contextoEvolucao.getAlfabeto().sortearNumero());
		}

	}

}
