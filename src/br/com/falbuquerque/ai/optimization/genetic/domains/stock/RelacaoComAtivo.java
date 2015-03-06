package br.com.falbuquerque.ai.optimization.genetic.domains.stock;

public class RelacaoComAtivo implements Comparable<RelacaoComAtivo> {

	private final Ativo ativo;
	private final Double covariancia;

	public RelacaoComAtivo(final Ativo ativo, final Double covariancia) {
		this.ativo = ativo;
		this.covariancia = covariancia;
	}

	public Ativo getAtivo() {
		return this.ativo;
	}

	public Double getCovariancia() {
		return this.covariancia;
	}

	@Override
	public int compareTo(final RelacaoComAtivo other) {

		if (other == null) {
			return 1;
		}

		if (this == other) {
			return 0;
		}

		return this.ativo.compareTo(other.ativo);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		final RelacaoComAtivo other = (RelacaoComAtivo) obj;
		return other.ativo.equals(this.ativo);
	}

	@Override
	public int hashCode() {
		return this.ativo.hashCode() * 3;
	}

}
