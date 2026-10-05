package www.campominado;

public class Celula {
    private boolean temBomba;
    private boolean revelada;
    private boolean marcada;
    private int bombasVizinhas;

    public Celula() {
        this.temBomba = false; //Garante que toda celula nasce sem bomba (as bombas serão distribuídas depois).
        this.revelada = false; //Garante que a célula nasce escondida.
        this.marcada = false; //Garante que nasce sem bandeira.
        this.bombasVizinhas = 0; //Começa a contagem de vizinhos zerada.
    }
    //void -> significa que o metodo executa uma ação, mas não devolve nenhum valor.
    public void revelar() {
        this.revelada = true;
    }
    public void alterarMarcacao() {
        //Este if significa que só podemos marcar/desmarcar se a célula não estiver revelada ainda.
        if (!this.revelada) {
            this.marcada = !this.marcada; //Inverte o estado atual. Se era false, vira true. Se era true, vira false.
        }
    }

    public String getSimbolos() {
        if (this.marcada) {
            return "⚐"; //Se tiver marcada -> Mostra "⚐"
        }
        if (this.revelada) {
            return "◼"; //Se ainda não foi cavada -> Mostra "◼"
        }
        if (this.temBomba) {
            return "✹"; //Se foi cavada e tinha bomba -> Mostra "✹"
        }
        if (this.bombasVizinhas == 0) {
            return "◻"; //Se foi cavada, não tem bomba e o número vizinho é zero -> Mostra "◻"
        }
        //String.valueOf() serve para converter qualquer tipo de dado (como números, booleanos, etc.) num texto.
        return String.valueOf(bombasVizinhas); //Converte o número de vizinhos para texto e exibe (ex: "1", "2")
    }

    public boolean isTemBomba() {
        return this.temBomba;
    } //Ele pega o valor atual de temBomba e devolve esse valor.
    public void setTemBomba(boolean temBomba) {
        this.temBomba = temBomba;
    } //Ele recebe true ou false e coloca esse valor na variável.
    public boolean isRevelada() {
        return this.revelada;
    }
    public boolean isMarcada() {
        return this.marcada;
    }

    public int getBombasVizinhas() {
        return this.bombasVizinhas;
    }
    public void setBombasVizinhas(int bombasVizinhas) {
        this.bombasVizinhas = bombasVizinhas;
    }
}
/*
    get → getter: normalmente indica que o metodo retorna/consulta um valor.
    set → setter: normalmente indica que o metodo modifica/define um valor.
    is → getter de boolean: indica que o metodo consulta uma condição/estado que resulta em true ou false.
 */