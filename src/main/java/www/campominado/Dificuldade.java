package www.campominado;

    //enum -> em Java é uma estrutura especial usada para definir um conjunto de valores fixos e constantes.
public enum Dificuldade {
    //Em vez de class, usamos enum para dizer ao Java que esse arquivo conterá apenas opções fixas pré-definidas.
    FACIL (8, 8, 10),
    MEDIO (12, 12, 22),
    DIFICIL (16, 16, 40);
    //Os números entre parênteses são os valores iniciais que estamos passando para cada dificuldade: (linhas, colunas, bombas).

    private final int linhas;
    private final int colunas;
    private final int bombas;
    //final -> Significa que o valor é imutável. Uma vez definido ele nunca mais mudará durante o jogo.

    Dificuldade(int linhas, int colunas, int bombas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.bombas = bombas;
    }
    /*
    Dificuldade(...): Nome do "molde" que executa quando o Java cria cada uma das opções (FACIL, MEDIO, DIFICIL).

    (int linhas, int colunas, int bombas): São os parâmetros que o molde recebe de fora quando escrevemos
    FACIL(8, 8, 10).

    this.linhas = linhas;: Essa linha diz: "Pegue o número recebido no parâmetro linhas e guarde na variável
    linhas deste Enum".
     */

    public int getLinhas() {
        return linhas;
    }
    public int getColunas() {
        return colunas;
    }
    public int getBombas() {
        return bombas;
    }
    /*
    Como as nossas variáveis são private, as outras partes do jogo não conseguem lê-las diretamente. Criamos métodos
    públicos para permitir a leitura.
     */

}
