package br.com.falbuquerque.ai.optimization.genetic.context;

import br.com.falbuquerque.ai.optimization.genetic.alphabet.Alfabeto;
import br.com.falbuquerque.ai.optimization.genetic.population.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.selection.TipoProblema;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergencia;

public class ContextoProbabilidadesFixas<N extends Number, S extends Number & Comparable<S>> extends ContextoEvolucao<N, S> {

	public ContextoProbabilidadesFixas(final ConjuntoTaxas conjuntoTaxasGerais,
	        final ConjuntoTaxas conjuntoTaxasMutacaoDirigida, final Alfabeto<N> alfabeto, final TipoProblema<S> tipoProblema,
	        final VerificadorConvergencia<N> verificadorConvergencia) {
		super(conjuntoTaxasGerais, conjuntoTaxasMutacaoDirigida, alfabeto, tipoProblema, verificadorConvergencia);
	}

	@Override
	public void modificarTaxas(final EstruturaPopulacao<N, S> estruturaPopulacao) {
		// método não precisa ser implementado nessa classe
	}

}
