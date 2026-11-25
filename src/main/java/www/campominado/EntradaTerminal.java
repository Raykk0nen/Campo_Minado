package www.campominado;

import java.util.Scanner;

public class EntradaTerminal {

    private final Scanner sc = new Scanner(System.in);

    public Acao escolherAcao() {
        System.out.println("\nEscolha uma Ação:");
        System.out.println("1 - Cavar\n2 - Marcar Bomba\n3 - Desmarcar Bomba");
        System.out.print("Opção: ");

        int opcao = sc.nextInt();

        return switch (opcao){
            case 1 -> Acao.CAVAR;
            case 2 -> Acao.MARCAR;
            case 3 -> Acao.DESMARCAR;
            default -> null;
        };
    }

    public Dificuldade escolherDificuldade() {
        while (true) {
            System.out.println("\nEscolha a Dificulade:");
            System.out.println("1 - Iniciante\n2 - Intermediário\n3 - Avançado\n4 - Mega-Brain\n5 - Tung-Tung-God");
            System.out.print("Opcao: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1: return Dificuldade.INICIANTE;
                case 2: return Dificuldade.INTERMEDIARIO;
                case 3: return Dificuldade.AVANCADO;
                case 4: return Dificuldade.MEGA_BRAIN;
                case 5: return Dificuldade.TUNG_TUNG_GOD;
                default: System.out.println("Opção inválida.");;
            }
        }
    }

    public int lerLinha() {
        System.out.print("Digite a Linha: ");
        return sc.nextInt();
    }
    public int lerColuna() {
        System.out.print("Digite a Coluna: ");
        return sc.nextInt();
    }

    public void fechar(){
        sc.close();
    }
}
