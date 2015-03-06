package br.com.falbuquerque.ai.optimization.genetic.mutacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;

public class MutacaoDirigidaEsquemaDominante<N extends Number, S extends Number & Comparable<S>> implements
        ComportamentoMutacao<N, S> {

	private final Integer qtdeMelhoresIndividuosSelecionados;
	private final Random sorteador;

	public MutacaoDirigidaEsquemaDominante(final Integer qtdeMelhoresIndividuosSelecionados) {
		this.qtdeMelhoresIndividuosSelecionados = qtdeMelhoresIndividuosSelecionados;
		this.sorteador = new Random();
	}

	@Override
	public void aplicarMutacao(final List<Individuo<N, S>> individuos, final ContextoEvolucao<N, S> contextoEvolucao) {
		final List<Individuo<N, S>> melhoresIndividuos = contextoEvolucao.getTipoProblema().obterNMelhoresIndividuos(
		        individuos, this.qtdeMelhoresIndividuosSelecionados);

		this.aplicarMutacaoDirigidaEsquemaDominante(contextoEvolucao, individuos,
		        this.criarMascaraAlelosIguais(melhoresIndividuos));
	}

	private void aplicarMutacaoDirigidaEsquemaDominante(final ContextoEvolucao<N, S> contextoEvolucao,
	        final List<Individuo<N, S>> individuos, final List<MarcaraAlelos> mascarasAlelos) {
		mascarasAlelos.forEach(mascara -> {
			final Individuo<N, S> individuoSorteado = individuos.get(this.sorteador.nextInt(individuos.size()));

			if (mascara.gene.equals(individuoSorteado.getGenotipo().get(mascara.alelo))) {
				individuoSorteado.alterarGenotipo(mascara.alelo, contextoEvolucao.getAlfabeto().sortearNumero());
			}
		});
	}

	private List<MarcaraAlelos> criarMascaraAlelosIguais(final List<Individuo<N, S>> melhoresIndividuos) {
		final int tamanhoCromossomo = melhoresIndividuos.get(0).getGenotipo().size();
		final List<MarcaraAlelos> alelosMascara = new ArrayList<MarcaraAlelos>(tamanhoCromossomo);

		for (int posAlelo = 0; posAlelo < tamanhoCromossomo; posAlelo++) {
			boolean todosIguais = true;
			N primeiroGene = null;
			N geneIndividuo = null;

			for (final Individuo<N, S> individuo : melhoresIndividuos) {
				geneIndividuo = individuo.getGenotipo().get(posAlelo);

				if (primeiroGene == null) {
					primeiroGene = geneIndividuo;
				} else {
					todosIguais = (primeiroGene.equals(geneIndividuo));

					if (!todosIguais) {
						break;
					}

				}

			}

			if (todosIguais) {
				alelosMascara.add(new MarcaraAlelos(posAlelo, geneIndividuo));
			}

		}

		return alelosMascara;
	}

	private class MarcaraAlelos {
		final Integer alelo;
		final N gene;

		MarcaraAlelos(final Integer alelo, final N gene) {
			this.alelo = alelo;
			this.gene = gene;
		}

	}

}
