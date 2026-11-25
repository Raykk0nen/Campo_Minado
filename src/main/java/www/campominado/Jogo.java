package www.campominado;

public class Jogo {
    public void iniciar() {
        EntradaTerminal entrada = new EntradaTerminal();

        Dificuldade dificuldade = entrada.escolherDificuldade();
        Tabuleiro tabuleiro = new Tabuleiro(dificuldade);

        boolean jogoRodando = true;

        while (jogoRodando) {
            tabuleiro.exeibir();

            Acao acao = entrada.escolherAcao();

            if (acao == null) {
                System.out.println("opção inválida! Tente novamente.\n");
                continue;
            }

            int linha = entrada.lerLinha();
            int coluna = entrada.lerColuna();

            jogoRodando = tabuleiro.executarAcao(linha, coluna, acao);

            if (!jogoRodando) {
                tabuleiro.exeibir();
                System.out.println("\n✹ BOOM! Você cavou a bomba. GAME OVER");
                break;
            }

            if (tabuleiro.verificarVitoria()){
                tabuleiro.exeibir();
                System.out.println("\n:) PARABÉNS! Você Limpou o campo sem explodir!");
                break;
            }
        }
        entrada.fechar();
    }
}
