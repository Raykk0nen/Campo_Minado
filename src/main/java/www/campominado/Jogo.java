package www.campominado;

import java.util.Scanner;

public class Jogo {
    public void iniciar() {
        Scanner sc = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("    BEM-VINDO AO CAMPO MINADO!    ");
        System.out.println("==================================");

        Dificuldade dificuldade = escolherDificuldade(sc);
        Tabuleiro tabuleiro = new Tabuleiro(dificuldade);

        boolean jogoRodando = true;

        while (jogoRodando) {
            tabuleiro.exeibir();

            System.out.println("\nEscolha uma Ação:");
            System.out.println("1 - Cavar\n2 - Marcar Bomba\n3 - Desmarcar Bomba");
            System.out.print("Opção: ");
            int opcaoAcao = sc.nextInt();

            Acao acao = switch (opcaoAcao) {
                case 1 -> Acao.CAVAR;
                case 2 -> Acao.MARCAR;
                case 3 -> Acao.DESMARCAR;
                default -> null;
            };

            if (acao != null) {
                System.out.println("opção inválida! Tente novamente.\n");
            }

            System.out.print("Digite a Linha: ");
            int linha = sc.nextInt();

            System.out.print("Digite a Coluna: ");
            int coluna = sc.nextInt();

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

    private Dificuldade escolherDificuldade(Scanner sc) {
        while (true) {
            System.out.println("\nEscolha a Dificulade:");
            System.out.println("1 - Fácil\n2 - Médio\n3 - Difícil");
            System.out.print("Opcao: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1: return Dificuldade.FACIL;
                case 2: return Dificuldade.MEDIO;
                case 3: return Dificuldade.DIFICIL;
                default: System.out.println("Opção inválida.");;
            }
        }
    }
}
