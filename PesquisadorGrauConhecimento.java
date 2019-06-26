import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;

public class PesquisadorGrauConhecimento{

   private int id;
   private int pesquisadores_id;
   private GrauConhecimento grauConhecimento;
   
   public PesquisadorGrauConhecimento(){
   
   }
   
   public PesquisadorGrauConhecimento(int id){
      this.id = id;
   }
   
   public PesquisadorGrauConhecimento(String pesquisadores_id){
      this.pesquisadores_id = Integer.parseInt(pesquisadores_id);
   }
   
   public PesquisadorGrauConhecimento(int id, int pesquisadores_id, GrauConhecimento grauConhecimento){
      this.id = id;
      this.pesquisadores_id = pesquisadores_id;
      this.grauConhecimento = grauConhecimento;
   }
   
   public void setId(int id){
      this.id = id;
   }
   
   public void setPesquisadoresId(int pesquisadores_id){
      this.pesquisadores_id = pesquisadores_id;
   }
   
   public void setGrauConhecimento(GrauConhecimento grauConhecimento){
      this.grauConhecimento = grauConhecimento;
   }
   
   public int getId(){
      return id;
   }
   
   public int getPesquisadoresId(){
      return pesquisadores_id;
   }
   
   
   public GrauConhecimento getGrauConhecimento(){
      return grauConhecimento;
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
      String query = "INSERT INTO pesquisadores_graus(pesquisadores_id, graus_conhecimento_id) VALUES(?,?)";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getPesquisadoresId());
         stmt.setInt(2, getGrauConhecimento().getId());
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
      String query = "UPDATE pesquisadores_graus SET pesquisadores_id = ?, graus_conhecimento_id = ? WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getPesquisadoresId());
         stmt.setInt(2, getGrauConhecimento().getId());
         stmt.setInt(3, getId());
         stmt.execute();
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public PesquisadorGrauConhecimento select(Connection conn){
      String query = "SELECT id, pesquisadores_id, graus_conhecimento_id FROM pesquisadores_graus WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getId());
         ResultSet rs = stmt.executeQuery();
         if(rs.next()){
            this.setId(rs.getInt("id"));
            this.setPesquisadoresId(rs.getInt("pesquisadores_id"));
            this.setGrauConhecimento(new GrauConhecimento(rs.getInt("graus_conhecimento_id")));
            
            this.getGrauConhecimento().carregar(conn); 
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return this;
   }
   
   public PesquisadorGrauConhecimento getByPesquisadorId(Connection conn){
      String query = "SELECT id, pesquisadores_id, graus_conhecimento_id FROM pesquisadores_graus WHERE pesquisadores_id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getPesquisadoresId());
         ResultSet rs = stmt.executeQuery();
         if(rs.next()){
            this.setId(rs.getInt("id"));
            this.setPesquisadoresId(rs.getInt("pesquisadores_id"));
            this.setGrauConhecimento(new GrauConhecimento(rs.getInt("graus_conhecimento_id")));
            
            this.getGrauConhecimento().carregar(conn);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return this;
   }
   
   public ArrayList<PesquisadorGrauConhecimento> getAll(Connection conn){
      ArrayList<PesquisadorGrauConhecimento> list = new ArrayList<>();
      String query = "SELECT pesquisadores_id, graus_conhecimento_id FROM pesquisadores_graus";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();
         while(rs.next()){
            PesquisadorGrauConhecimento pg = new PesquisadorGrauConhecimento();
            pg.setPesquisadoresId(rs.getInt("pesquisadores_id"));
            pg.setGrauConhecimento(new GrauConhecimento(rs.getInt("graus_conhecimento_id")));
            pg.getGrauConhecimento().carregar(conn);
            list.add(pg);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return list;
   }

}