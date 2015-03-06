package br.com.falbuquerque.ai.optimization.genetic.contexto;

import br.com.falbuquerque.ai.optimization.genetic.alfabeto.Alfabeto;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.selecao.TipoProblema;
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
