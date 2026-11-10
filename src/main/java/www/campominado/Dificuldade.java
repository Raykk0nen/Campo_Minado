package www.campominado;

//Enum é um tipo de class que utilizado para definir valores fixos
public enum Dificuldade {
    INICIANTE (9, 9, 10, true),
    INTERMEDIARIO (16, 16, 40, true),
    AVANCADO (16, 30, 99, true),
    MEGA_BRAIN (50, 50, 500, false),
    TUNG_TUNG_GOD (100, 100, 2000, false);

    private final int linhas;
    private final int colunas;
    private final int bombas;
    private final boolean permitirCompetitivo;

    Dificuldade(int linhas, int colunas, int bombas,  boolean permitirCompetitivo) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.bombas = bombas;
        this.permitirCompetitivo = permitirCompetitivo;
    }

    public int getLinhas() {
        return linhas;
    }
    public int getColunas() {
        return colunas;
    }
    public int getBombas() {
        return bombas;
    }
    public boolean isPermitirCompetitivo() {
        return permitirCompetitivo;
    }

}
