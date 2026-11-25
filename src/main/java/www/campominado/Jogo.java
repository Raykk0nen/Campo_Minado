package www.campominado;

import java.util.Scanner;

public class Jogo {
    public void iniciar() {
        Scanner sc = new Scanner(System.in);
        EntradaTerminal entrada = new EntradaTerminal();

        Dificuldade dificuldade = entrada.escolherDificuldade(sc);
        Tabuleiro tabuleiro = new Tabuleiro(dificuldade);

        boolean jogoRodando = true;

        while (jogoRodando) {
            tabuleiro.exeibir();

            Acao acao = entrada.escolherAcao(sc);

            if (acao == null) {
                System.out.println("opção inválida! Tente novamente.\n");
                continue;
            }

            int linha = entrada.lerLinha(sc);
            int coluna = entrada.lerColuna(sc);

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
        sc.close();
    }
}
