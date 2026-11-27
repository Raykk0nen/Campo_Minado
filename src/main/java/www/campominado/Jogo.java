package www.campominado;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private EstadoPartida estado = EstadoPartida.EM_ANDAMENTO;

    public Jogo(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public void jogar(int linha, int coluna, Acao acao) {
        if (estado != EstadoPartida.EM_ANDAMENTO) {
            return;
        }

        if  (!tabuleiro.executarAcao(linha, coluna, acao)) {
            estado = EstadoPartida.DERROTA;
        }

        if (tabuleiro.verificarVitoria()) {
            estado = EstadoPartida.VITORIA;
        }
    }
}
