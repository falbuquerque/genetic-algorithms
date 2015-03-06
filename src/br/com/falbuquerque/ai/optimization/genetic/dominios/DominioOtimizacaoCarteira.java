package br.com.falbuquerque.ai.optimization.genetic.dominios;

import java.util.ArrayList;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.alfabeto.Alfabeto;
import br.com.falbuquerque.ai.optimization.genetic.alfabeto.AlfabetoSemZero;
import br.com.falbuquerque.ai.optimization.genetic.alfabeto.IntervaloInteiros;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ConjuntoTaxas;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.contexto.ContextoProbabilidadesFixas;
import br.com.falbuquerque.ai.optimization.genetic.dominios.carteiras.Ativo;
import br.com.falbuquerque.ai.optimization.genetic.dominios.carteiras.Carteira;
import br.com.falbuquerque.ai.optimization.genetic.mutacao.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.populacao.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.populacao.PopulacaoTradicional;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.ComportamentoReproducao;
import br.com.falbuquerque.ai.optimization.genetic.reproducao.CrossoverUniforme;
import br.com.falbuquerque.ai.optimization.genetic.selecao.MetodoSelecao;
import br.com.falbuquerque.ai.optimization.genetic.selecao.RoletaIntegerDouble;
import br.com.falbuquerque.ai.optimization.genetic.selecao.TipoProblema;
import br.com.falbuquerque.ai.optimization.genetic.selecao.TipoProblemaMaximizacao;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergencia;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergenciaNumerosDiscretos;

public class DominioOtimizacaoCarteira extends Dominio<Integer, Double, Carteira> {

	private final List<Ativo> ativos;

	public DominioOtimizacaoCarteira(final Integer numMaxGeracoes, final ContextoEvolucao<Integer, Double> contextoEvolucao,
	        final ComportamentoReproducao<Integer, Double> comportamentoReproducao,
	        final ControladorMutacoes<Integer, Double> controladorMutacoes,
	        final EstruturaPopulacao<Integer, Double> estruturaPopulacao, final List<Ativo> ativos) {
		super(numMaxGeracoes, ativos.size(), contextoEvolucao, comportamentoReproducao, controladorMutacoes,
		        estruturaPopulacao);
		this.ativos = ativos;
	}

	@Override
	protected Double calcularFitness(final Individuo<Integer, Double> individuo) {
		final Carteira carteira = this.obterValorIndividuo(individuo);
		return carteira.calcularRetornoEsperado() / carteira.calcularVariancia();
	}

	@Override
	public Carteira obterValorIndividuo(final Individuo<Integer, Double> individuo) {
		int posAtivo = 0;
		final List<Ativo> ativosCarteira = new ArrayList<>(this.ativos.size());
		Integer somaCarteira = 0;

		for (final Integer peso : individuo.getGenotipo()) {
			somaCarteira += peso;
		}

		for (final Integer peso : individuo.getGenotipo()) {
			ativosCarteira.add(new Ativo(this.ativos.get(posAtivo), peso.doubleValue() / somaCarteira));
			posAtivo++;
		}

		return new Carteira(ativosCarteira);
	}

	@Override
	protected Boolean isIndividuoValido(final Individuo<Integer, Double> individuo) {
		return this.obterValorIndividuo(individuo).calcularRetornoEsperado() >= 6;
	}

	public static void main(String[] args) {
		final List<Ativo> ativos = new ArrayList<>(5);

		final Ativo ativoVale = new Ativo(1, "VALE", 3.63, 0.2);
		final Ativo ativoPetro = new Ativo(2, "PETROBRAS", 6.17, 0.2);
		final Ativo ativoCyrela = new Ativo(3, "CYRELA", 2.27, 0.2);
		final Ativo ativoTecToy = new Ativo(4, "TEC TOY", 1.51, 0.2);
		final Ativo ativoGerdau = new Ativo(5, "GERDAU", 2.38, 0.2);

		ativoVale.adicionarRelacaoComOutroAtivo(ativoVale, 0.008888);
		ativoVale.adicionarRelacaoComOutroAtivo(ativoPetro, -0.003752);
		ativoVale.adicionarRelacaoComOutroAtivo(ativoCyrela, -0.000054);
		ativoVale.adicionarRelacaoComOutroAtivo(ativoTecToy, -0.0391640);
		ativoVale.adicionarRelacaoComOutroAtivo(ativoGerdau, 0.0018628);

		ativoPetro.adicionarRelacaoComOutroAtivo(ativoVale, -0.004);
		ativoPetro.adicionarRelacaoComOutroAtivo(ativoPetro, 0.170638);
		ativoPetro.adicionarRelacaoComOutroAtivo(ativoCyrela, 0.041837);
		ativoPetro.adicionarRelacaoComOutroAtivo(ativoTecToy, 0.0021759);
		ativoPetro.adicionarRelacaoComOutroAtivo(ativoGerdau, -0.0031349);

		ativoCyrela.adicionarRelacaoComOutroAtivo(ativoVale, -0.0001);
		ativoCyrela.adicionarRelacaoComOutroAtivo(ativoPetro, 0.042);
		ativoCyrela.adicionarRelacaoComOutroAtivo(ativoCyrela, 0.034983);
		ativoCyrela.adicionarRelacaoComOutroAtivo(ativoTecToy, 0.031958);
		ativoCyrela.adicionarRelacaoComOutroAtivo(ativoGerdau, -0.002066);

		ativoTecToy.adicionarRelacaoComOutroAtivo(ativoVale, -0.0392);
		ativoTecToy.adicionarRelacaoComOutroAtivo(ativoPetro, 0.0022);
		ativoTecToy.adicionarRelacaoComOutroAtivo(ativoCyrela, 0.03196);
		ativoTecToy.adicionarRelacaoComOutroAtivo(ativoTecToy, 0.4616099);
		ativoTecToy.adicionarRelacaoComOutroAtivo(ativoGerdau, -0.0261310);

		ativoGerdau.adicionarRelacaoComOutroAtivo(ativoVale, 0.0019);
		ativoGerdau.adicionarRelacaoComOutroAtivo(ativoPetro, -0.0031);
		ativoGerdau.adicionarRelacaoComOutroAtivo(ativoCyrela, -0.0021);
		ativoGerdau.adicionarRelacaoComOutroAtivo(ativoTecToy, -0.0261);
		ativoGerdau.adicionarRelacaoComOutroAtivo(ativoGerdau, 0.0045);

		ativos.add(ativoVale);
		ativos.add(ativoPetro);
		ativos.add(ativoCyrela);
		ativos.add(ativoTecToy);
		ativos.add(ativoGerdau);

		Double retornoEsperado = 0.0;
		Individuo<Integer, Double> melhorIndividuo = null;
		Carteira carteira = null;

		while ((retornoEsperado < 3.0) || (retornoEsperado > 3.001)) {
			final ConjuntoTaxas taxasGerais = new ConjuntoTaxas(0.6, 0.8, 0.2);
			final ConjuntoTaxas taxasMutacaoDirigida = new ConjuntoTaxas(0.6, 0.3, 0.7);
			final Alfabeto<Integer> alfabeto = new AlfabetoSemZero(new IntervaloInteiros(1, 100));
			final TipoProblema<Double> tipoProblema = new TipoProblemaMaximizacao<>();
			final VerificadorConvergencia<Integer> verificadorConvergencia = new VerificadorConvergenciaNumerosDiscretos<>(
			        0.9, 0.9);
			final ContextoEvolucao<Integer, Double> contextoEvolucao = new ContextoProbabilidadesFixas<>(taxasGerais,
			        taxasMutacaoDirigida, alfabeto, tipoProblema, verificadorConvergencia);
			final ComportamentoReproducao<Integer, Double> comportamentoReproducao = new CrossoverUniforme<>();
			final ControladorMutacoes<Integer, Double> controladorMutacoes = new ControladorMutacoes<>(true, 120, 10, 80);
			final MetodoSelecao<Integer, Double> metodoSelecao = new RoletaIntegerDouble(tipoProblema);
			final EstruturaPopulacao<Integer, Double> estruturaPopulacao = new PopulacaoTradicional<>(100,
			        controladorMutacoes, metodoSelecao);

			final Dominio<Integer, Double, Carteira> dominio = new DominioOtimizacaoCarteira(100, contextoEvolucao,
			        comportamentoReproducao, controladorMutacoes, estruturaPopulacao, ativos);

			melhorIndividuo = dominio.procurarMelhorIndividuo();
			carteira = dominio.obterValorIndividuo(melhorIndividuo);
			retornoEsperado = carteira.calcularRetornoEsperado();
		}

		System.out.println(melhorIndividuo);
		System.out.println(melhorIndividuo.getFitness());
		System.out.println(carteira);
		System.out.println(carteira.calcularRetornoEsperado());
		System.out.println(carteira.calcularVariancia());
	}

}
