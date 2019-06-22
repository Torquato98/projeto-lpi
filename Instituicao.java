import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Instituicao{

    private int id;
    private String nome;
    private  ArrayList<Instituicao> instituicoes;
    
    public Instituicao(){

    }

    public Instituicao(int id){
        this.id = id;
    }

    public Instituicao(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public void setInstituicoes (ArrayList<Instituicao> instituicoes){
      this.instituicoes = instituicoes;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public ArrayList<Instituicao> getInstituicoes (){
      return instituicoes; 
    }
    
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public Instituicao select(Connection conn){
        String query = "SELECT id, nome FROM instituicao WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(0, this.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                this.setNome(rs.getString("nome"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return this;

    }

    public ArrayList<Instituicao> getAll(Connection conn){
        String query = "SELECT id, nome FROM instituicao";
        ArrayList<Instituicao> instituicoes = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Instituicao instituicao = new Instituicao();
                instituicao.setId(rs.getInt("id"));
                instituicao.setNome(rs.getString("nome"));
                instituicoes.add(instituicao);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return instituicoes;
    }

    public boolean insert(Connection conn){
        boolean result = false;
        String query = "INSERT INTO instituicao(nome) VALUES(?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(0, this.getNome());
            stmt.execute();

            result = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean update(Connection conn){
        boolean result = false;
        String query = "UPDATE instituicao SET nome = ? WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(0, this.getId());
            stmt.setString(1, this.getNome());
            stmt.execute();

            result = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean delete(Connection conn){
        boolean result = false;
        //Isso aqui é um comentário para teste
        String query = "DELETE FROM instituicao WHERE id = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(0, this.getNome());
            stmt.setInt(1, this.getId());
            stmt.execute();

            result = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString(){
        return "Instituicao[id=" + this.id + ", nome=" + this.nome + "]";
    }

}