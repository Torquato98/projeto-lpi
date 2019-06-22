import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GrandeAreaDeConhecimento{

    private int id;
    private String nome;

    public GrandeAreaDeConhecimento(){

    }
    
    public GrandeAreaDeConhecimento(int id){
        this.id = id;
    }

    public GrandeAreaDeConhecimento(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }
    
    public GrandeAreaDeConhecimento select(Connection conn){
         String query = "SELECT id, nome FROM grandes_areas_conhecimento WHERE id = ?";
         try{
               PreparedStatement stmt = conn.prepareStatement(query);
               stmt.setInt(1, this.getId());
               
               ResultSet rs = stmt.executeQuery();
               if(rs.next()){
                    this.setId(rs.getInt("id"));
                    this.setNome(rs.getString("nome"));
               }
         }catch(Exception e){
              e.printStackTrace();
         }
         return this;
    }
    
    public ArrayList<GrandeAreaDeConhecimento> getAll(Connection conn){
         String query = "SELECT id, nome FROM grandes_areas_conhecimento";
         ArrayList<GrandeAreaDeConhecimento> grandesAreasDeConhecimento = new ArrayList<>();
         try{
               PreparedStatement stmt = conn.prepareStatement(query);
               ResultSet rs = stmt.executeQuery();
               
               while(rs.next()){
                    GrandeAreaDeConhecimento grandeAreaDeConhecimento = new GrandeAreaDeConhecimento();
                    grandeAreaDeConhecimento.setId(rs.getInt("id"));
                    grandeAreaDeConhecimento.setNome(rs.getString("nome"));
                    grandesAreasDeConhecimento.add(grandeAreaDeConhecimento);
               }
         }catch(Exception e){
              e.printStackTrace();
         }
         
         return grandesAreasDeConhecimento;
    }
    
    @Override
    public String toString(){
        return "Grande Area de Conhecimento[ID=" + this.id + ", Nome=" + this.nome + "]";
    }

}