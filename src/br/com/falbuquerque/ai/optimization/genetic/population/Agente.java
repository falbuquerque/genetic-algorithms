package br.com.falbuquerque.ai.optimization.genetic.population;

import java.util.ArrayList;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;

public class Agente<N extends Number, S extends Number & Comparable<S>> {

	private static final Integer MAX_SUBORDINADOS = 3;

	private final List<Agente<N, S>> subordinados;
	protected Individuo<N, S> pocket;
	protected Individuo<N, S> current;

	public Agente(final Individuo<N, S> individuo) {
		this.pocket = individuo;
		this.subordinados = new ArrayList<>(MAX_SUBORDINADOS);
	}

	public Boolean adicionarComoSubordinado(final Agente<N, S> agente) {

		if (this.subordinados.size() < MAX_SUBORDINADOS) {
			this.subordinados.add(agente);
		} else {

			for (final Agente<N, S> agenteSubordinado : this.subordinados) {

				if (agenteSubordinado.subordinados.size() < MAX_SUBORDINADOS) {
					agenteSubordinado.subordinados.add(agente);
					return true;
				}

			}

		}

		return false;
	}

	public void organizarPocketsComSubordinados() {
		this.definirPocket();
		this.subordinados.forEach(agente -> {
			agente.organizarPocketsComSubordinados();

			if (this.isTrocaPocketsAplicavel(agente)) {
				final Individuo<N, S> temp = this.pocket;
				this.pocket = agente.pocket;
				agente.pocket = temp;

				this.organizarPocketsComSubordinados();
			}

		});

	}

	public Integer getQtdeAgentesSubordinados() {
		Integer qtdeAgentesAdicionados = 1;

		for (final Agente<N, S> agenteSubordinado : this.subordinados) {
			qtdeAgentesAdicionados += agenteSubordinado.getQtdeAgentesSubordinados();
		}

		return qtdeAgentesAdicionados;
	}

	public List<Agente<N, S>> getSubordinados() {
		return this.subordinados;
	}

	protected Individuo<N, S> getPocket() {
		return this.pocket;
	}

	protected Individuo<N, S> getCurrent() {
		return this.current;
	}

	protected void setCurrent(final Individuo<N, S> current) {
		this.current = current;
	}

	private Boolean isCurrentMaiorQuePocket() {
		return (this.current.getFitness().compareTo(this.pocket.getFitness()) > 0);
	}

	private Boolean isTrocaPocketsAplicavel(final Agente<N, S> subordinado) {
		return (subordinado.pocket.getFitness().compareTo(this.pocket.getFitness()) > 0);
	}

	private void definirPocket() {

		if ((this.current != null) && (this.isCurrentMaiorQuePocket())) {
			final Individuo<N, S> temp = this.pocket;
			this.pocket = this.current;
			this.current = temp;
		}

	}

}
