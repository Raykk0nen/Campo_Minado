package www.campominado;

public enum Acao {
    CAVAR,
    MARCAR,
    DESMARCAR;

    /*
    Este Enum é serve apenas como uma lista de etiquetas para representar as ações do jogador a cada turno.

    Agora, no resto do jogo, podemos criar variáveis assim:
    Acao escolhaDoJogador;

    Essa variável escolhaDoJogador só aceitará estritamente três valores: Acao.CAVAR, Acao.MARCAR ou Acao.DESMARCAR.
    Se alguém tentar passar um valor inválido, o próprio Java impede a compilação.
     */
}
