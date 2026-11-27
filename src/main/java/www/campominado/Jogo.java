package www.campominado;

public class Jogo {
    public void iniciar() {
        EntradaTerminal entrada = new EntradaTerminal();

        Dificuldade dificuldade = entrada.escolherDificuldade();
        Tabuleiro tabuleiro = new Tabuleiro(dificuldade);
        EstadoPartida estado = EstadoPartida.EM_ANDAMENTO;

        while (estado == EstadoPartida.EM_ANDAMENTO) {
            tabuleiro.exeibir();

            Acao acao = entrada.escolherAcao();

            if (acao == null) {
                System.out.println("opção inválida! Tente novamente.\n");
                continue;
            }

            int linha = entrada.lerLinha();
            int coluna = entrada.lerColuna();

            estado = processarJoogada(tabuleiro, linha, coluna, acao);
        }

        tabuleiro.exeibir();

        if (estado == EstadoPartida.DERROTA) {
            tabuleiro.exeibir();
            System.out.println("\n✹ BOOM! Você cavou a bomba. GAME OVER");
        }
        if (estado == EstadoPartida.VITORIA) {
            tabuleiro.exeibir();
            System.out.println("\n:) PARABÉNS! Você Limpou o campo sem explodir!");
        }

        entrada.fechar();
    }

    private EstadoPartida processarJoogada(Tabuleiro tabuleiro, int linha, int coluna, Acao acao) {
        if  (!tabuleiro.executarAcao(linha, coluna, acao)) {
            return EstadoPartida.DERROTA;
        }

        if (tabuleiro.verificarVitoria()) {
            return EstadoPartida.VITORIA;
        }

        return EstadoPartida.EM_ANDAMENTO;
    }
}
