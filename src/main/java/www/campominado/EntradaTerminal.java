package www.campominado;

import java.util.Scanner;

public class EntradaTerminal {
    private Acao escolherAcao(Scanner sc) {
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
}
