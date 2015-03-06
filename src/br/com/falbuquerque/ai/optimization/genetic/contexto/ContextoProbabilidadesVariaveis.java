package br.com.falbuquerque.ai.optimization.genetic.contexto;

import br.com.falbuquerque.ai.optimization.genetic.alfabeto.Alfabeto;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.selecao.TipoProblema;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergencia;

public class ContextoProbabilidadesVariaveis<N extends Number, S extends Number & Comparable<S>> extends
        ContextoEvolucao<N, S> {

	private final ConjuntoTaxas conjuntoTaxasMinimas;
	private final ConjuntoTaxas conjuntoTaxasMaximas;
	private final Double taxaModificacao;

	public ContextoProbabilidadesVariaveis(final ConjuntoTaxas conjuntoTaxasGerais,
	        final ConjuntoTaxas conjuntoTaxasMutacaoDirigida, final ConjuntoTaxas conjuntoTaxasMaximas,
	        final Alfabeto<N> alfabeto, final TipoProblema<S> tipoProblema,
	        final VerificadorConvergencia<N> verificadorConvergencia, final Double taxaModificacao) {
		super(conjuntoTaxasGerais, conjuntoTaxasMutacaoDirigida, alfabeto, tipoProblema, verificadorConvergencia);
		this.conjuntoTaxasMaximas = conjuntoTaxasMaximas;
		this.taxaModificacao = taxaModificacao;
		this.conjuntoTaxasMinimas = new ConjuntoTaxas(conjuntoTaxasGerais);
	}

	@Override
	public void modificarTaxas(final EstruturaPopulacao<N, S> estruturaPopulacao) {

		// Se a população estiver convergindo, incrementa as taxas de crossover
		// e mutação
		if (this.isPopulacaoConvergindo(estruturaPopulacao)) {
			this.conjuntoTaxasGerais.incrementarTaxas(this.taxaModificacao);

			// Se a taxa de crossover for maior que o máximo estipulado, trunca
			// o valor no máximo
			if (this.conjuntoTaxasGerais.getTaxaCruzamento().compareTo(this.conjuntoTaxasMaximas.getTaxaCruzamento()) > 0) {
				this.conjuntoTaxasGerais.setTaxaCruzamento(this.conjuntoTaxasMaximas.getTaxaCruzamento());
			}

			// Se a taxa de mutação for maior que o máximo estipulado, trunca o
			// valor no máximo
			if (this.conjuntoTaxasGerais.getTaxaMutacao().compareTo(this.conjuntoTaxasMaximas.getTaxaMutacao()) > 0) {
				this.conjuntoTaxasGerais.setTaxaMutacao(this.conjuntoTaxasMaximas.getTaxaMutacao());
			}

		} else { // Senão, decrementa as taxas de crossover e mutação
			this.conjuntoTaxasGerais.decrementarTaxas(this.taxaModificacao);

			// Se a taxa de crossover for menor que o valor inicial, trunca no
			// valor inicial
			if (this.conjuntoTaxasGerais.getTaxaCruzamento().compareTo(this.conjuntoTaxasMinimas.getTaxaCruzamento()) < 0) {
				this.conjuntoTaxasGerais.setTaxaCruzamento(this.conjuntoTaxasMinimas.getTaxaCruzamento());
			}

			// Se a taxa de mutação for menor que o valor inicial, trunca no
			// valor inicial
			if (this.conjuntoTaxasGerais.getTaxaMutacao().compareTo(this.conjuntoTaxasMinimas.getTaxaMutacao()) < 0) {
				this.conjuntoTaxasGerais.setTaxaMutacao(this.conjuntoTaxasMinimas.getTaxaMutacao());
			}

		}

	}

}
