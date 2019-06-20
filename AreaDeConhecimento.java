import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AreaDeConhecimento{

    private int id;
    private String nome;
    private GrandeAreaDeConhecimento grandeAreaDeConhecimento;

    public AreaDeConhecimento(){

    }
    
    public AreaDeConhecimento(int id){
         this.id = id;
    }

    public AreaDeConhecimento(int id, String nome, GrandeAreaDeConhecimento grandeAreaDeConhecimento){
        this.id = id;
        this.nome = nome;
        this.grandeAreaDeConhecimento = grandeAreaDeConhecimento;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setGrandeAreaDeConhecimento(GrandeAreaDeConhecimento grandeAreaDeConhecimento){
        this.grandeAreaDeConhecimento = grandeAreaDeConhecimento;
    }

    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }
    
    public GrandeAreaDeConhecimento getGrandeAreaDeConhecimento(){
        return this.grandeAreaDeConhecimento;
    }

    @Override
    public String toString(){
        return "Area de Conhecimento[ID=" + this.id + ", Nome=" + this.nome + ", Grande area de conhecimento=" + this.grandeAreaDeConhecimento + "]";
    }
    
    public AreaDeConhecimento select(Connection conn){
         String query = "SELECT id, nome, grandes_areas_conhecimento_id FROM areas_conhecimento WHERE id = ?";
         try{
               PreparedStatement stmt = conn.prepareStatement(query);
               stmt.setInt(1, getId());
               ResultSet rs = stmt.executeQuery();
               
               if(rs.next()){
                    this.setId(rs.getInt("id"));
                    this.setNome(rs.getString("nome"));
                    GrandeAreaDeConhecimento grandeAreaDeConhecimento = new GrandeAreaDeConhecimento(rs.getInt("grandes_areas_conhecimento_id"));
                    grandeAreaDeConhecimento.select(conn);
                    this.grandeAreaDeConhecimento = grandeAreaDeConhecimento;
               }
         }catch(Exception e){
               e.printStackTrace();
         }
         return this;
    }
    
    public ArrayList<AreaDeConhecimento> getAll(Connection conn){
         String query = "SELECT id, nome, grandes_areas_conhecimento_id FROM areas_conhecimento";
         ArrayList<AreaDeConhecimento> areasDeConhecimento = new ArrayList<>();
         try{
               PreparedStatement stmt = conn.prepareStatement(query);
               ResultSet rs = stmt.executeQuery();
               
               while(rs.next()){
                     AreaDeConhecimento areaDeConhecimento = new AreaDeConhecimento();
                     areaDeConhecimento.setId(rs.getInt("id"));
                     areaDeConhecimento.setNome(rs.getString("nome"));
                     GrandeAreaDeConhecimento grandeAreaDeConhecimento = new GrandeAreaDeConhecimento(rs.getInt("grandes_areas_conhecimento_id"));
                     grandeAreaDeConhecimento.select(conn);
                     areaDeConhecimento.setGrandeAreaDeConhecimento(grandeAreaDeConhecimento);
                     areasDeConhecimento.add(areaDeConhecimento);
               }
         }catch(Exception e){
               e.printStackTrace();
         }
         return areasDeConhecimento;
    }
    
}