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

    public boolean executarAcao(int li, int co, Acao acao){
        //Este if irá garantir que as escolhas do jogador sejam válidas.
        if (li < 0 || li >=linhas || co < 0 || co >= colunas){
            System.out.println("Posição inválida! Escolha dentro dos limites.");
            return true;
        }

        Celula celula = matriz[li][co];
        //Este if fará com que o jogador marque ou desmarque uma célula.
        if (acao == Acao.MARCAR || acao == Acao.DESMARCAR){
            celula.alterarMarcacao();
            return true;
        }
        //Este if permitirá que o jogador consiga cavar qualquer célula válida.
        if (acao == Acao.CAVAR){
            //Este if irá garantir que o jogador não cave uma célula marcada.
            if (celula.isMarcada() || celula.isRevelada()){
                return true;
            }

            if (!bombasGeradas){
                gerarBombas(li, co);
            }
            //Este if fará que quando o jogador cave um bomba, todas sejam reveladas.
            if (celula.isTemBomba()){
                celula.revelar();
                revelarTodasAsBombas();
                return false;
            }

            revelarEmCadeia(li, co);
        }

        return true;
    }

    private void revelarEmCadeia(int li, int co){
        if (li < 0 || li >=linhas || co < 0 || co >= colunas) {
            return;
        }

        Celula celula = matriz[li][co];

        if (celula.isRevelada() || celula.isMarcada()){
            return;
        }

        celula.revelar();

        if (celula.getBombasVizinhas() == 0 && !celula.isTemBomba()){
            for (int dl = -1; dl <= 1; dl++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dl != 0 || dc != 0){
                        revelarEmCadeia(li + dl, co + dc);
                    }
                }
            }
        }
    }

    private void revelarTodasAsBombas(){
        for (int li = 0; li < linhas; li++) {
            for (int co = 0; co < colunas; co++) {
                if (matriz[li][co].isTemBomba()){
                    matriz[li][co].revelar();
                }
            }
        }
    }

    public boolean verificarVitoria(){
        for (int li = 0; li < linhas; li++) {
            for (int co = 0; co < colunas; co++) {
                Celula celula = matriz[li][co];
                if (!celula.isTemBomba() && !celula.isRevelada()){
                    return false;
                }
            }
        }
        return true;
    }

    public void exeibir(){
        System.out.print("    ");
        for (int co = 0; co < colunas; co++) {
            System.out.printf("%2d ",co);
        }
        System.out.println();

        for (int li = 0; li < linhas; li++) {
            for (int co = 0; co < colunas; co++) {
                System.out.print("[" + matriz[li][co].getSimbolos() + "]");
            }
            System.out.println();
        }
    }

    public int getLinhas(){
        return linhas;
    }
    public int getColunas(){
        return colunas;
    }
}
