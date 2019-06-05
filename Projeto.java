public class Projeto{

    private int id;
    private String titulo;
    private String duracao;
    private double orcamento;
    private GrandeAreaDeConhecimento grandeAreaDeConhecimento;
    private String dataResposta;
    private String resposta;

    public Projeto(){

    }
    public Projeto(int id, String titulo, String duracao, double orcamento, GrandeAreaDeConhecimento grandeAreaDeConhecimento){
        this.id = id;
        this.titulo = titulo;
        this.duracao = duracao;
        this.orcamento = orcamento;
        this.grandeAreaDeConhecimento = grandeAreaDeConhecimento;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setDuracao(String duracao){
        this.duracao = duracao;
    }

    public void setOrcamento(double orcamento){
        this.orcamento = orcamento;
    }

    public void setGrandeAreaDeConhecimento(GrandeAreaDeConhecimento grandeAreaDeConhecimento){
        this.grandeAreaDeConhecimento = grandeAreaDeConhecimento;
    }

    public void setDataResposta(String dataResposta){
        this.dataResposta = dataResposta;
    }

    public void setResposta(String resposta){
        this.resposta = resposta;
    }

    public int getId(){
        return this.id;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getDuracao(){
        return this.duracao;
    }

    public double getOrcamento(){
        return this.orcamento;
    }

    public GrandeAreaDeConhecimento getGrandeAreaDeConhecimento(){
        return this.grandeAreaDeConhecimento;
    }

    public String getDataResposta(){
        return this.dataResposta;
    }

    public String getResposta(){
        return this.resposta;
    }

    @Override
    public String toString(){
        String resp = "Projeto[ID=" + this.id + ", Titulo=" + this.titulo + ", Duracao=" + this.duracao + ", Orcamento=" + this.orcamento;
        if(this.resposta != null){
            resp += ", Resposta=" + this.resposta + ", Data da Resposta=" + this.dataResposta + "";
        }
        resp += ", Grande Area de Conhecimento=" + this.grandeAreaDeConhecimento +"]";
        return resp;
    }

}