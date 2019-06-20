import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;

public class AreaPesquisa{

   private int id;
   private String nome;
   private AreaDeConhecimento areaDeConhecimento;
   
   public AreaPesquisa(){
        
   }
   
   public AreaPesquisa(int id){
      this.id = id;
   }
   
   public AreaPesquisa(int id, String nome, AreaDeConhecimento areaDeConhecimento){
      this.id = id;
      this.nome = nome;
      this.areaConhecimento = areaDeConhecimento;
   }
   
   public void setId(int id){
      this.id = id;
   }
   
   public void setNome(String nome){
      this.nome = nome;
   }
   
   public void setAreaConhecimento(AreaConhecimento areaDeConhecimento){
      this.areaConhecimento = areaDeConhecimento;
   }
   
   public int getId(){
      return id;
   }
   
   public String getNome(){
      return nome;
   }
   
   public AreaConhecimento getAreaConhecimento(){
      return areaConhecimento;
   }
   
   private void getLastIdInserted(Connection conn){
      try{
         String query2 = "SELECT LAST_INSERT_ID()";
         PreparedStatement stmt2 = conn.prepareStatement(query2);
         ResultSet rs = stmt2.executeQuery();
         if(rs.next()){
            this.setId(rs.getInt(1));
         }
      }catch(Exception e){
         e.printStackTrace();
      }
   }
   
   public boolean insert(Connection conn){
      boolean result = false;
      String query = "INSERT INTO areas_pesquisa(nome, areas_conhecimento_id) VALUES(?,?)";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setString(1, getNome());
         stmt.setInt(2, getAreaConhecimento().getId());
         stmt.execute();
         
         this.getLastIdInserted(conn);
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public boolean update(Connection conn){
      boolean result = false;
      String query = "UPDATE areas_pesquisa SET nome = ?, areas_conhecimento_id = ? WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setString(1, getNome());
         stmt.setInt(2, getAreaConhecimento().getId());
         stmt.setInt(3, getId());
         stmt.execute();
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public AreaPesquisa select(Connection conn){
      String query = "SELECT nome, areas_conhecimento_id FROM areas_pesquisa WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getId());
         ResultSet rs = stmt.executeQuery();
         if(rs.next()){
            this.setNome(rs.getString("nome"));
            this.setAreaConhecimento(new AreaConhecimento(rs.getInt("areas_conhecimento_id")));
            
            this.getAreaConhecimento().select(conn);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return this;
   }

   public ArrayList<AreaPesquisa> getAll(Connection conn){
      ArrayList<AreaPesquisa> list = new ArrayList<>();
      String query = "SELECT nome, areas_conhecimento_id FROM areas_pesquisa";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();
         while(rs.next()){
            AreaPesquisa areaPesquisa = new AreaPesquisa();
            areaPesquisa.setId(rs.getInt("id"));
            areaPesquisa.setNome(rs.getString("nome"));
            areaPesquisa.setAreaConhecimento(new AreaConhecimento(rs.getInt("areas_conhecimento_id")));

            areaPesquisa.getAreaConhecimento().select(conn);
            list.add(areaPesquisa);
         }
      }catch(Exception e){
         e.printStackTrace();
      }

      return list;
   }

}