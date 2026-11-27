package www.campominado;

public class JogoTerminal {
    public void iniciar() {
        EntradaTerminal entrada = new EntradaTerminal();

        Dificuldade dificuldade = entrada.escolherDificuldade();
        Tabuleiro tabuleiro = new Tabuleiro(dificuldade);
        Jogo jogo = new Jogo(tabuleiro);

        while (jogo.getEstado() == EstadoPartida.EM_ANDAMENTO) {
            tabuleiro.exeibir();

            Acao acao = entrada.escolherAcao();

            if (acao == null) {
                System.out.println("opção inválida! Tente novamente.\n");
                continue;
            }

            int linha = entrada.lerLinha();
            int coluna = entrada.lerColuna();

            jogo.jogar(linha, coluna, acao);
        }

        tabuleiro.exeibir();

        if (jogo.getEstado() == EstadoPartida.DERROTA) {
            tabuleiro.exeibir();
            System.out.println("\n✹ BOOM! Você cavou a bomba. GAME OVER");
        }
        if (jogo.getEstado() == EstadoPartida.VITORIA) {
            tabuleiro.exeibir();
            System.out.println("\n:) PARABÉNS! Você Limpou o campo sem explodir!");
        }

        entrada.fechar();
    }
}
