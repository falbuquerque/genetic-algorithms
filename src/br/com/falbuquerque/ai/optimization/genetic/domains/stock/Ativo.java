package br.com.falbuquerque.ai.optimization.genetic.domains.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ativo implements Comparable<Ativo> {

	private final Integer id;
	private final List<RelacaoComAtivo> relacoesComOutrosAtivos;
	private final Double peso;
	private final Double retornoEsperado;
	private final String descricao;

	public Ativo(final Integer id, final String descricao, final Double retornoEsperado, final Double peso) {
		this(id, descricao, retornoEsperado, peso, new ArrayList<>());
	}

	public Ativo(final Ativo ativoBase, final Double peso) {
		this(ativoBase.id, ativoBase.descricao, ativoBase.retornoEsperado, peso, ativoBase.relacoesComOutrosAtivos);
	}

	private Ativo(final Integer id, final String descricao, final Double retornoEsperado, final Double peso,
	        final List<RelacaoComAtivo> relacoesComOutrosAtivos) {
		this.id = id;
		this.descricao = descricao;
		this.retornoEsperado = retornoEsperado;
		this.peso = peso;
		this.relacoesComOutrosAtivos = relacoesComOutrosAtivos;
	}

	public Integer getId() {
		return this.id;
	}

	public void adicionarRelacaoComOutroAtivo(final Ativo outroAtivo, final Double covariancia) {
		this.relacoesComOutrosAtivos.add(new RelacaoComAtivo(outroAtivo, covariancia));
	}

	public List<RelacaoComAtivo> getRelacoesComOutrosAtivos() {
		return Collections.unmodifiableList(this.relacoesComOutrosAtivos);
	}

	public Double getPeso() {
		return this.peso;
	}

	public Double getRetornoEsperado() {
		return this.retornoEsperado;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public int compareTo(Ativo other) {
		return this.id.compareTo(other.id);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Ativo) {
			Ativo other = (Ativo) obj;

			return other.id.equals(this.id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode() * 3;
	}

	@Override
	public String toString() {
		final StringBuilder retorno = new StringBuilder();

		retorno.append(this.id).append(" - ").append(this.descricao).append(" - ").append(this.peso).append(" - ")
		        .append(this.retornoEsperado);

		return retorno.toString();
	}

}
