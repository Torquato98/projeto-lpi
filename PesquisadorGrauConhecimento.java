import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;

public class PesquisadorGrauConhecimento{

   private int id;
   private int pesquisador_id;
   private int grau_conhecimento_id;
   private GrauConhecimento grauConhecimento;
   
   public PesquisadorGrauConhecimento(){
   
   }
   
   public PesquisadorGrauConhecimento(int id){
      this.id = id;
   }
   
   public PesquisadorGrauConhecimento(int id, int pesquisador_id, int grau_conhecimento_id, GrauConhecimento grauConhecimento){
      this.id = id;
      this.pesquisador_id = pesquisador_id;
      this.grau_conhecimento_id = grau_conhecimento_id;
      this.grauConhecimento = grauConhecimento;
   }
   
   public void setId(int id){
      this.id = id;
   }
   
   public void setPesquisadorId(int pesquisador_id){
      this.pesquisador_id = pesquisador_id;
   }
   
   public void setGrauConhecimentoId(int grau_conhecimento_id){
      this.grau_conhecimento_id = grau_conhecimento_id;
   }
   
   public void setGrauConhecimento(GrauConhecimento grauConhecimento){
      this.grauConhecimento = grauConhecimento;
   }
   
   public int getId(){
      return id;
   }
   
   public int getPesquisadorId(){
      return pesquisador_id;
   }
   
   public int getGrauConhecimentoId(){
      return grau_conhecimento_id;
   }
   
   public GrauConhecimento getGrauConhecimento(){
      return grauConhecimento;
   }
   
   public boolean insert(Connection conn){
      boolean result = false;
      String query = "INSERT INTO pesquisadores_graus(pesquisador_id, grau_conhecimento_id) VALUES(?,?)";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getPesquisadorId());
         stmt.setInt(2, getGrauConhecimentoId());
         stmt.execute();
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public boolean update(Connection conn){
      boolean result = false;
      String query = "UPDATE pesquisadores_graus SET pesquisador_id = ?, grau_conhecimento_id = ? WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getPesquisadorId());
         stmt.setInt(2, getGrauConhecimentoId());
         stmt.execute();
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public PesquisadorGrauConhecimento select(Connection conn){
      String query = "SELECT pesquisador_id, grau_conhecimento_id FROM pesquisadores_graus WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getId());
         ResultSet rs = stmt.executeQuery();
         if(rs.next()){
            this.setPesquisadorId(rs.getInt("pesquisador_id"));
            this.setGrauConhecimentoId(rs.getInt("grau_conhecimento_id"));
            this.setGrauConhecimento(new GrauConhecimento(rs.getInt("grau_conhecimento_id")));
            
            this.getGrauConhecimento().carregar(conn); 
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return this;
   }
   
   public ArrayList<PesquisadorGrauConhecimento> getAll(Connection conn){
      ArrayList<PesquisadorGrauConhecimento> list = new ArrayList<>();
      String query = "SELECT pesquisador_id, grau_conhecimento_id FROM pesquisadores_graus";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();
         while(rs.next()){
            PesquisadorGrauConhecimento pg = new PesquisadorGrauConhecimento();
            pg.setPesquisadorId(rs.getInt("pesquisador_id"));
            pg.setGrauConhecimentoId(rs.getInt("grau_conhecimento_id"));
            pg.setGrauConhecimento(new GrauConhecimento(rs.getInt("grau_conhecimento_id")));
            pg.getGrauConhecimento().carregar(conn);
            list.add(pg);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return list;
   }

}