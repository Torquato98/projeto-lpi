import java.util.ArrayList;

public class Agencia{

    private ArrayList<Projeto> projetos;

    public Agencia(){
        this.projetos = new ArrayList<Projeto>();
    }

    public int buscar(int codigo){
        int posicao = -1;
        for(int i = 0; i < projetos.size(); i++){
            if(projetos.get(i).getId() == codigo){
                posicao = i;
            }
        }
        return posicao;
    }

    public String buscarProjeto(int codigo){
        int posicao = this.buscar(codigo);
        if(posicao >= 0){
            return this.projetos.get(posicao);
        }else{
            return "Projeto nao encontrado";
        }
    }

    public void inserir(Projeto projeto){
        this.projetos.add(projeto);
    }

    public boolean alterarProjeto(int codigo, String resposta, String dataResposta){
        int posicao = this.buscar(codigo);
        if(posicao >= 0){
            this.projetos.get(posicao).setResposta(resposta);
            this.projetos.get(posicao).setDataResposta(dataResposta);
            return true;
        }else{
            return false;
        }
    }

    public boolean remover(int codigo){
        int posicao = this.buscar(codigo);
        if(posicao >= 0){
            this.projetos.remove(posicao);
            return true;
        }else{
            return false;
        }
    }

    public String listarProjetos(){
        String resp = "";
        for(Projeto projeto:projetos){
            resp += projeto + "\n";
        }
        return resp;
    }

}