import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GrauConhecimento{
   private int id;
   private String nome;
   
   public GrauConhecimento(){
   
   }
   public GrauConhecimento(int id){
      this.id = id;
   }
   public GrauConhecimento(int id , String nome){
      this.id = id;
      this.nome = nome;
   }
   public int getId(){
      return this.id;
   }
   public String getNome(){
      return this.nome;
   } 
   public void setId(int id){
      this.id = id;
   }
   public void setNome(String nome){
      this.nome = nome;
   }
  
   public boolean incluir(Connection conn){
      boolean result = false;
      String query = "INSERT INTO grau_conhecimento (id, nome) VALUES (?,?) ";
      try {PreparedStatement stm = conn.prepareStatement(query);
         stm.setInt(1,getId());
         stm.setString(2,getNome());
         stm.execute();
      
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
    
   public boolean excluir(Connection conn){
      boolean result = false;
      String query = "DELETE FROM graus_conhecimento WHERE id = ?";
      try{PreparedStatement stm = conn.prepareStatement(query);
         stm.setInt(1, getId());
         stm.execute();
      
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   public boolean atualizar(Connection conn){
      boolean result = false;
      String query = "UPDATE graus_conhecimento SET nome = ? WHERE id = ? ";
      try{PreparedStatement stm = conn.prepareStatement(query);
         stm.setString(1,getNome());
         stm.setInt(2, getId());
         stm.execute();
      
         result = true;
      
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }

   public GrauConhecimento carregar(Connection conn){
      String query = "SELECT id, nome FROM graus_conhecimento WHERE id = ?";
      try{PreparedStatement stm = conn.prepareStatement(query);
         stm.setInt(1, getId());
         ResultSet rs = stm.executeQuery();      
         if(rs.next()){
            this.setNome(rs.getString("nome"));
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return this;
   }
   
   public ArrayList<GrauConhecimento> getAll(Connection conn){
      ArrayList<GrauConhecimento> list = new ArrayList<>();
      String query = "SELECT id, nome FROM graus_conhecimento";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();
         while(rs.next()){
            GrauConhecimento g = new GrauConhecimento();
            g.setId(rs.getInt("id"));
            g.setNome(rs.getString("nome"));
            list.add(g);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return list;
   }


}
  
  

