package br.com.falbuquerque.ai.optimization.genetic.domains;

import java.util.ArrayList;
import java.util.List;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.alphabet.Alfabeto;
import br.com.falbuquerque.ai.optimization.genetic.alphabet.AlfabetoClustering;
import br.com.falbuquerque.ai.optimization.genetic.alphabet.IntervaloInteiros;
import br.com.falbuquerque.ai.optimization.genetic.context.ConjuntoTaxas;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoEvolucao;
import br.com.falbuquerque.ai.optimization.genetic.context.ContextoProbabilidadesFixas;
import br.com.falbuquerque.ai.optimization.genetic.csv.LeitorArquivosCSV;
import br.com.falbuquerque.ai.optimization.genetic.domains.clustering.Cluster;
import br.com.falbuquerque.ai.optimization.genetic.domains.clustering.DadoCluster;
import br.com.falbuquerque.ai.optimization.genetic.mutation.ControladorMutacoes;
import br.com.falbuquerque.ai.optimization.genetic.population.EstruturaPopulacao;
import br.com.falbuquerque.ai.optimization.genetic.population.PopulacaoTradicional;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.ComportamentoReproducao;
import br.com.falbuquerque.ai.optimization.genetic.reproduction.CrossoverUniforme;
import br.com.falbuquerque.ai.optimization.genetic.selection.MetodoSelecao;
import br.com.falbuquerque.ai.optimization.genetic.selection.TipoProblema;
import br.com.falbuquerque.ai.optimization.genetic.selection.TipoProblemaMaximizacao;
import br.com.falbuquerque.ai.optimization.genetic.selection.TorneioEquacaoSeno3D;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergencia;
import br.com.falbuquerque.ai.optimization.genetic.utils.VerificadorConvergenciaNumerosDiscretos;

public class DominioClusteringRepresGruposInt extends Dominio<Integer, Double, List<Cluster>> {

	private final List<DadoCluster> dados;
	private final Integer numClusters;

	public DominioClusteringRepresGruposInt(final Integer numMaxGeracoes, final List<DadoCluster> dados,
	        final Integer numClusters, final ContextoEvolucao<Integer, Double> contextoEvolucao,
	        final ComportamentoReproducao<Integer, Double> comportamentoReproducao,
	        final ControladorMutacoes<Integer, Double> controladorMutacoes,
	        final EstruturaPopulacao<Integer, Double> estruturaPopulacao) {
		super(numMaxGeracoes, dados.size(), contextoEvolucao, comportamentoReproducao, controladorMutacoes, estruturaPopulacao);

		this.numClusters = numClusters;
		this.dados = dados;
	}

	@Override
	protected Double calcularFitness(final Individuo<Integer, Double> individuo) {
		final List<Cluster> clusters = this.obterValorIndividuo(individuo);
		Double fitness = 0.0;

		for (final Cluster cluster : clusters) {
			fitness += cluster.calcularVariacaoEntreDados();
		}

		return (1 / (fitness + 1));
	}

	@Override
	protected List<Cluster> obterValorIndividuo(final Individuo<Integer, Double> individuo) {
		final List<Cluster> clusters = new ArrayList<>(this.numClusters);

		for (int i = 0; i < this.numClusters; i++) {
			clusters.add(new Cluster());
		}

		int posDado = 0;

		for (final Integer cluster : individuo.getGenotipo()) {
			clusters.get(cluster - 1).adicionarDado(this.dados.get(posDado));
			posDado++;
		}

		return clusters;
	}

	public static void main(String[] args) {
		final ControladorMutacoes<Integer, Double> controladorMutacoes = new ControladorMutacoes<>(false, 130, 500, 5);
		final TipoProblema<Double> tipoProblema = new TipoProblemaMaximizacao<>();
		final MetodoSelecao<Integer, Double> metodoSelecao = new TorneioEquacaoSeno3D(tipoProblema, 0.2f);
		final ComportamentoReproducao<Integer, Double> comportamentoReproducao = new CrossoverUniforme<>();
		final EstruturaPopulacao<Integer, Double> populacao = new PopulacaoTradicional<>(80, controladorMutacoes,
		        metodoSelecao);
		final ConjuntoTaxas taxasGerais = new ConjuntoTaxas(0.6, 0.8, 0.2);
		final ConjuntoTaxas taxasMutacao = new ConjuntoTaxas(0.6, 0.8, 0.2);
		final VerificadorConvergencia<Integer> verificadorConvergencia = new VerificadorConvergenciaNumerosDiscretos<>(.9, .9);
		final Alfabeto<Integer> alfabeto = new AlfabetoClustering(new IntervaloInteiros(1, 3));
		final ContextoEvolucao<Integer, Double> contextoEvolucao = new ContextoProbabilidadesFixas<>(taxasGerais,
		        taxasMutacao, alfabeto, tipoProblema, verificadorConvergencia);
		final List<DadoCluster> dados = new LeitorArquivosCSV("/dev/tmp/ai/databases", "wine.csv").gerarDadosCluster();

		final Dominio<Integer, Double, List<Cluster>> dominio = new DominioClusteringRepresGruposInt(500, dados, 3,
		        contextoEvolucao, comportamentoReproducao, controladorMutacoes, populacao);
		final Individuo<Integer, Double> melhorIndividuo = dominio.procurarMelhorIndividuo();

		System.out.println(melhorIndividuo);
		System.out.println(dominio.calcularFitness(melhorIndividuo));
	}

}
