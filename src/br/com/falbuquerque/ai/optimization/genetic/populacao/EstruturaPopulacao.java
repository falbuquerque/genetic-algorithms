package br.com.falbuquerque.ai.optimization.genetic.populacao;

import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.dominios.Dominio;

public interface EstruturaPopulacao <N extends Number, S extends Number & Comparable<S>> {

	void criarNovaGeracao(ContextoEvolucao<N, S> contextoEvolucao);

	void reinicializarPopulacao();

	void receberAvaliacoes();

	void setDominio(Dominio<N, S, ?> dominio);

	Boolean adicionarIndividuo(Individuo<N, S> individuo);

	Boolean isPopulacaoCheia();

	Integer getGeracaoAtual();

	List<Individuo<N, S>> getIndividuos();

	List<Individuo<N, S>> getFilhosGeracalAtual();

	Individuo<N, S> getMelhorIndividuo();
}
