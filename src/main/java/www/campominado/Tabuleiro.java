package www.campominado;

import java.util.Random;

public class Tabuleiro {
    private final int  linhas;
    private final int colunas;
    private final int totalBombas;
    private final Celula[][] matriz;
    private boolean bombasGeradas;
    /*
    O duplo colchete [][] indica uma Matriz Bidimensional (uma grade de linhas e colunas).
    Cada posição dessa grade vai guardar um objeto Celula inteiro.
     */

    public Tabuleiro(Dificuldade dificuldade){
        this.linhas = dificuldade.getLinhas();
        this.colunas = dificuldade.getColunas();
        this.totalBombas = dificuldade.getBombas();
        this.matriz = new Celula[linhas][colunas];
        this.bombasGeradas = false; //Uma trava de controle. Começa em false porque só vamos distribuir as bombas no primeiro clique do jogador.

        inicializarTabuleiro();
    }

    private void inicializarTabuleiro(){
        //O primeiro 'for' (l) percorre o tabuleiro de CIMA para BAIXO (Linha por Linha).
        for (int li = 0; li < linhas; li++) {
            //O segundo 'for' (c) percorre a linha atual da ESQUERDA para a DIREITA (Coluna por Coluna).
            for (int co = 0; co < colunas; co++) {
                //Instancia uma nova Célula limpa/fechada e a coloca na posição exata [linha][coluna].
                matriz[li][co] = new Celula();
            }
        }
    }

    private void gerarBombas(int linhaInicial, int colunaInicial){
        Random random = new Random(); //Cria o gerador de números aleatórios do Java.
        int bombasColocadas = 0;

        while (bombasColocadas < totalBombas){
            //O laço while repete o sorteio até conseguirmos posicionar a quantidade exata de bombas no tabuleiro
            int li = random.nextInt(linhas);
            int co = random.nextInt(colunas);
            //Garante que o local sorteado não seja o mesmo quadrado do primeiro clique do jogador.
            if ((li != linhaInicial || co != colunaInicial) && !matriz[li][co].isTemBomba()){
                matriz[li][co].setTemBomba(true);
                bombasColocadas++;
            }
        }
        this.bombasGeradas = true;
        calcularBombasVizinhas(); //Chama a contagem assim que terminar de colocar as bombas
    }

    private void calcularBombasVizinhas(){
        for (int li = 0; li < linhas; li++) {
            for (int co = 0; co < colunas; co++) {

                // esse if significa que se a própria célula tem bomba, não precisa contar vizinhos para ela.
                if (matriz[li][co].isTemBomba()){
                    continue;
                }

                int contagem =0;

                for (int dl = -1; dl <= 1; dl++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int vizinhoL = li + dl;
                        int vizinhoC = co + dc;

                        if (vizinhoL >= 0 && vizinhoL < linhas && vizinhoC >= 0 && vizinhoC < colunas) {
                            if(matriz[li][co].isTemBomba()){
                                contagem++;
                            }
                        }
                    }
                }
                matriz[li][co].setBombasVizinhas(contagem);
            }
        }
    }
}
