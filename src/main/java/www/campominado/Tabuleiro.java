package www.campominado;

import java.util.Random;

public class Tabuleiro {

    //Quantidade de linhas que o tabuleiro terá.
    private final int  linhas;

    //Quantidade de colunas que o tabuleiro terá.
    private final int colunas;

    //Quantidade total de bombas que serão colocadas no tabuleiro.
    private final int totalBombas;

    /*
     * Matriz bidimensional de objetos Celula.
     * O primeiro índice [li] representa a linha.
     * O segundo índice [co] representa a coluna.
     */
    private final Celula[][] matriz;

    //Trava se segurança do jogo.
    private boolean bombasGeradas;

    //
    private static final int larguraConsole = 180;

    //Construtor
    public Tabuleiro(Dificuldade dificuldade){

        //Pega da dificuldade a quantidade de linhas.
        this.linhas = dificuldade.getLinhas();

        //Pega da dificuldade a quantidade de colunas.
        this.colunas = dificuldade.getColunas();

        //Pega da dificuldade a quantidade de bombas.
        this.totalBombas = dificuldade.getBombas();

        //Criação da matriz
        this.matriz = new Celula[linhas][colunas];

        //As bombas ainda não foram colocadas.
        this.bombasGeradas = false;

        //Cria uma Celula em cada posição da matriz.
        inicializarTabuleiro();
    }
    /*
     * A função desse método é percorrer todas as posições da matriz
     * e colocar uma nova Celula em cada uma delas.
     */
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

    //Gera e distribui as bombas aleatoriamente pelo tabuleiro.
    private void gerarBombas(int linhaInicial, int colunaInicial){

        //Cria o gerador de números aleatórios do Java.
        Random random = new Random();

        //Contador que começa em zero e registra quantas bombas já foram colocadas.
        int bombasColocadas = 0;

        //O laço while repete o sorteio até conseguirmos posicionar a quantidade exata de bombas no tabuleiro.
        while (bombasColocadas < totalBombas){

            //Sorteia uma linha válida.
            int li = random.nextInt(linhas);

            //Sorteia uma coluna válida.
            int co = random.nextInt(colunas);

            //Garante que o local sorteado não seja o mesmo quadrado do primeiro clique do jogador.
            if ((li != linhaInicial || co != colunaInicial) && !matriz[li][co].isTemBomba()){

                //Marca a célula sorteada como contendo uma bomba.
                matriz[li][co].setTemBomba(true);

                //Aumenta o contador de bombas colocadas em 1.
                bombasColocadas++;
            }
        }

        this.bombasGeradas = true;

        //Chama a contagem assim que terminar de colocar as bombas
        calcularBombasVizinhas();
    }
    //Calcula quantas bombas existem ao redor de cada célula.
    private void calcularBombasVizinhas(){

        //Percorre todas as linhas do tabuleiro.
        for (int li = 0; li < linhas; li++) {

            //Para cada linha, percorre todas as colunas.
            for (int co = 0; co < colunas; co++) {

                //Esse if significa que se a própria célula tem bomba, não precisa contar vizinhos para ela.
                if (matriz[li][co].isTemBomba()){
                    continue;
                }

                //Começamos a contagem de bombas vizinhas em zero.
                int contagem =0;

                /*
                 * dl = deslocamento da LINHA.
                 *
                 * Ele assume os valores:
                 *
                 * -1 -> linha anterior (acima)
                 *  0 -> mesma linha
                 * +1 -> próxima linha (abaixo)
                 */
                for (int dl = -1; dl <= 1; dl++) {

                    /*
                     * dc = deslocamento da COLUNA.
                     *
                     * Ele também assume:
                     *
                     * -1 -> coluna anterior (esquerda)
                     *  0 -> mesma coluna
                     * +1 -> próxima coluna (direita)
                     */
                    for (int dc = -1; dc <= 1; dc++) {

                        //Calculamos a posição do possível vizinho.
                        int vizinhoL = li + dl;

                        //Faz a mesma coisa para a coluna.
                        int vizinhoC = co + dc;

                        //Esse IF é a proteção contra posições inexistentes.
                        if (vizinhoL >= 0 && vizinhoL < linhas && vizinhoC >= 0 && vizinhoC < colunas) {

                            /*
                             * Quando dl = 0 e dc = 0,
                             * estamos olhando para a própria célula.
                             *
                             * Como queremos somente os vizinhos,
                             * ignoramos essa combinação.
                             */
                            if (dl != 0 || dc != 0) {

                                /*
                                 * Agora verificam se o VIZINHO
                                 * possui uma bomba.
                                 */
                                if(matriz[vizinhoL][vizinhoC].isTemBomba()){

                                    //Se alguma bomba for encontrada o valor de contagem sobe.
                                    contagem++;
                                }
                            }
                        }
                    }
                }
                /*
                 * Depois de analisar todos os possíveis vizinhos,
                 * guardamos na célula a quantidade encontrada.
                 */
                matriz[li][co].setBombasVizinhas(contagem);
            }
        }
    }

    //Executa a ação escolhida pelo jogador.
    public boolean executarAcao(int li, int co, Acao acao){

        //Primeiro verifica se a posição escolhida existe.
        if (li < 0 || li >=linhas || co < 0 || co >= colunas){
            System.out.println("Posição inválida! Escolha dentro dos limites.");
            return true;
        }

        //Pega a Celula correspondente à posição escolhida.
        Celula celula = matriz[li][co];

        //Este if fará com que o jogador marque ou desmarque uma célula.
        if (acao == Acao.MARCAR || acao == Acao.DESMARCAR){
            celula.alterarMarcacao();
            return true;
        }

        //Verifica se a ação escolhida foi CAVAR.
        if (acao == Acao.CAVAR){

            /*
             * Não permitimos cavar uma célula marcada
             * ou que já foi revelada.
             */
            if (celula.isMarcada() || celula.isRevelada()){
                return true;
            }

            /*
             * Se as bombas ainda não foram geradas,
             * significa que este é o primeiro clique.
             */
            if (!bombasGeradas){

                /*
                 * Gera as bombas e envia a posição do primeiro clique
                 * para garantir que essa posição não receba uma bomba.
                 */
                gerarBombas(li, co);
            }
            //Agora verificamos se a célula escolhida contém uma bomba.
            if (celula.isTemBomba()){

                //Revela a bomba que o jogador clicou.
                celula.revelar();

                //Revela todas as outras bombas do tabuleiro.
                revelarTodasAsBombas();
                return false;
            }

            /*
             * Se a célula não possui bomba,
             * iniciamos a revelação em cadeia.
             */
            revelarEmCadeia(li, co);
        }

        return true;
    }

    //Revela uma célula e, quando ela possui 0 bombas vizinhas.
    private void revelarEmCadeia(int li, int co){

        //Proteção contra posições inexistentes.
        if (li < 0 || li >=linhas || co < 0 || co >= colunas) {
            return;
        }
        //Pega a célula da posição informada.
        Celula celula = matriz[li][co];

        /*
         * Se a célula já foi revelada ou está marcada,
         * não precisamos processá-la novamente.
         */
        if (celula.isRevelada() || celula.isMarcada()){
            return;
        }
        //Revela a célula atual.
        celula.revelar();

        /*
         * Só precisamos continuar a cadeia quando:
         *
         * 1. A célula possui ZERO bombas vizinhas.
         * 2. A célula não é uma bomba.
         */
        if (celula.getBombasVizinhas() == 0 && !celula.isTemBomba()){

            //Percorremos deslocamentos de -1 até +1
            for (int dl = -1; dl <= 1; dl++) {

                //Percorremos deslocamentos de -1 até +1
                for (int dc = -1; dc <= 1; dc++) {

                    /*
                     * dl = 0 e dc = 0 representa
                     * a própria célula.
                     *
                     * Queremos somente os vizinhos,
                     * então ignoramos essa combinação.
                     */
                    if (dl != 0 || dc != 0){
                        revelarEmCadeia(li + dl, co + dc);
                    }
                }
            }
        }
    }
    /*
     * Revela TODAS as bombas existentes no tabuleiro.
     *
     * Esse método é chamado quando o jogador
     * cava uma bomba e perde.
     */
    private void revelarTodasAsBombas(){

        //Percorre todas as linhas.
        for (int li = 0; li < linhas; li++) {

            //Para cada linha, percorre todas as colunas.
            for (int co = 0; co < colunas; co++) {

                //Verifica se a posição atual possui uma bomba.
                if (matriz[li][co].isTemBomba()){

                    //Se tiver bomba, revela a célula.
                    matriz[li][co].revelar();
                }
            }
        }
    }

    //Verifica se o jogador venceu.
    public boolean verificarVitoria(){

        //Percorre todas as linhas.
        for (int li = 0; li < linhas; li++) {

            //Para cada linha, percorre todas as colunas.
            for (int co = 0; co < colunas; co++) {

                // Pega a célula atual.
                Celula celula = matriz[li][co];

                /*
                 * o programa procura situação que signifique:
                 *
                 * "Existe uma célula que não é bomba
                 *  e ainda não foi revelada."
                 *
                 * Se encontrarmos uma, o jogo ainda não acabou.
                 */
                if (!celula.isTemBomba() && !celula.isRevelada()){
                    return false;
                }
            }
        }
        return true;
    }

    //Exibe o tabuleiro no terminal.
    public void exeibir(){

        /*
         * Imprime alguns espaços para separar os números
         * das colunas da primeira linha do tabuleiro.
         */
        System.out.print("    ");

        //Percorre as colunas para mostrar seus números.
        for (int co = 0; co < colunas; co++) {
            System.out.printf("%2d ",co);
        }

        //Pula para a próxima linha.
        System.out.println();

        //Percorre todas as linhas do tabuleiro.
        for (int li = 0; li < linhas; li++) {

            //Para cada linha, percorre todas as colunas.
            for (int co = 0; co < colunas; co++) {
                System.out.print("[" + matriz[li][co].getSimbolos() + "]");
            }

            //pulamos para a próxima linha.
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
