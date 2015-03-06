package br.com.falbuquerque.ai.optimization.genetic.selecao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.com.falbuquerque.ai.optimization.genetic.Individuo;
import br.com.falbuquerque.ai.optimization.genetic.csv.GeradorArquivosCSVRoleta;

public abstract class Roleta<N extends Number, S extends Number & Comparable<S>> extends MetodoSelecao<N, S> {

	private final GeradorArquivosCSVRoleta<S> geradorArquivos;
	private final Boolean aplicarEscalonamentoSigma;
	private final Windowing<N, S> windowing;
	private Map<Individuo<N, S>, Double> porcentagens;
	private Integer geracao;
	private List<Individuo<N, S>> roleta;

	public Roleta(final TipoProblema<S> tipoProblema, final Boolean aplicarEscalonamentoSigma, final Windowing<N, S> windowing) {
		super(tipoProblema);
		this.geracao = 0;
		this.geradorArquivos = new GeradorArquivosCSVRoleta<>(10);
		this.aplicarEscalonamentoSigma = aplicarEscalonamentoSigma;
		this.windowing = windowing;
	}

	public Roleta(final TipoProblema<S> tipoProblema, final Boolean aplicarEscalonamentoSigma) {
		this(tipoProblema, aplicarEscalonamentoSigma, null);
	}

	public Roleta(final TipoProblema<S> tipoProblema, final Windowing<N, S> windowing) {
		this(tipoProblema, false, windowing);
	}

	public Roleta(final TipoProblema<S> tipoProblema) {
		this(tipoProblema, false, null);
	}

	@Override
	public Individuo<N, S> obterProximoIndividuo() {

		if (this.roleta == null) {
			this.criarRoleta();
		}

		return this.roleta.get(new Random().nextInt(this.roleta.size()));
	}

	private void criarRoleta() {

		if (this.windowing != null) {
			this.avaliacoes = this.windowing.aplicarWindowing(this.avaliacoes);
		}

		if (this.aplicarEscalonamentoSigma) {
			this.porcentagens = this.getEscalonamentoSigma(this.avaliacoes).aplicarEscalonamento();
		} else {
			this.porcentagens = new HashMap<>(this.avaliacoes.keySet().size());
			this.avaliacoes.keySet().forEach(
			        individuo -> this.porcentagens.put(individuo,
			                this.calcularPorcentagemDoTotal(this.avaliacoes.get(individuo))));
		}

		this.geracao++;
		this.roleta = new ArrayList<>(360);
		this.porcentagens.keySet().forEach(
		        individuo -> {
			        final Double porcentagem = this.porcentagens.get(individuo);
			        Integer qtdePosicoes = this.calcularQtdePosicoesNaRoleta(porcentagem);

			        this.geradorArquivos.adicionarfFitness(this.geracao, individuo.getFitness(), porcentagem,
			                individuo.getGenotipo());

			        while (qtdePosicoes > 0) {

				        if (this.roleta.size() < 360) {
					        this.roleta.add(individuo);
				        } else {
					        break;
				        }

				        qtdePosicoes--;
			        }

		        });

		final Map<Individuo<N, S>, Integer> posicoes = new HashMap<>();
		this.roleta.forEach(individuo -> {
			Integer qtde = posicoes.get(individuo);
			posicoes.remove(individuo);
			qtde = (qtde == null) ? 0 : (qtde + 1);
			posicoes.put(individuo, qtde);
		});
	}

	protected abstract EscalonamentoSigma<N, S> getEscalonamentoSigma(Map<Individuo<N, S>, S> avaliacoes);

	@Override
	public void reiniciar() {
		super.reiniciar();
		this.roleta = null;
		this.porcentagens = null;
	}

	public void criarArquivo(final String diretorio, final String nomeArquivo) throws IOException {
		this.geradorArquivos.criarArquivo(diretorio, nomeArquivo);
		this.geradorArquivos.clear();
	}

	private Integer calcularQtdePosicoesNaRoleta(final Double porcentagem) {
		return (int) Math.round((360.0 * porcentagem) / 100);
	}

	private Double calcularPorcentagemDoTotal(final S fitness) {
		return (fitness.doubleValue() * 100) / this.fitnessTotal.doubleValue();
	}

}
