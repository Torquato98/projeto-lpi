import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

public class Projeto{

   private int id;
   private String titulo;
   private String duracao;
   private double orcamento;
   private AreaDeConhecimento areaDeConhecimento;
   private Date dataResposta;
   private int resposta = 0;
   private Pesquisador pesquisador;
   private Instituicao instituicao;
   private Avaliador avaliador;

   public Projeto(){
    
   }
   
   public Projeto(int id){
      this.id = id;
   }

   public Projeto(int id, String titulo, String duracao, double orcamento, AreaDeConhecimento areaDeConhecimento, Date dataDeResposta, int resposta, Avaliador avaliador, Pesquisador pesquisador){
      this.id = id;
      this.titulo = titulo;
      this.duracao = duracao;
      this.orcamento = orcamento;
      this.areaDeConhecimento = areaDeConhecimento;
      this.avaliador = avaliador;
      this.pesquisador = pesquisador;
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

   public void setAreaDeConhecimento(AreaDeConhecimento areaDeConhecimento){
      this.areaDeConhecimento = areaDeConhecimento;
   }

   public void setDataResposta(Date dataResposta){
      this.dataResposta = dataResposta;
   }

   public void setResposta(int resposta){
      this.resposta = resposta;
   }
   
   public void setInstituicao(Instituicao instituicao){
      this.instituicao = instituicao;
   }
   
   public void setAvaliador(Avaliador avaliador){
      this.avaliador = avaliador;
   }
   
   public void setPesquisador(Pesquisador pesquisador){
      this.pesquisador = pesquisador;
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

   public AreaDeConhecimento getAreaDeConhecimento(){
      return this.areaDeConhecimento;
   }

   public Date getDataResposta(){
      return this.dataResposta;
   }

   public int getResposta(){
      return this.resposta;
   }
   
   public Instituicao getInstituicao(){
      return this.instituicao;
   }
   
   public Avaliador getAvaliador(){
      return this.avaliador;
   } 
   
   public Pesquisador getPesquisador(){
      return pesquisador;
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
       
      String sqlInsert = "INSERT INTO projetos(titulo, duracao, orcamento, areas_conhecimento_id, instituicao_id, avaliadores_id, pesquisadores_id, data_envio, resposta) VALUES (?,?,?,?,?,?,?,NOW(),0)";
          
      try(PreparedStatement stm = conn.prepareStatement(sqlInsert);){
         stm.setString(1, getTitulo());
         stm.setString(2, getDuracao());
         stm.setDouble(3, getOrcamento());
         stm.setInt(4, getAreaDeConhecimento().getId());
         stm.setInt(5, getInstituicao().getId());
         stm.setInt(6, getAvaliador().getId());
         stm.setInt(7, getPesquisador().getId());
         stm.execute();
         
         this.getLastIdInserted(conn);
         result = true;
      }catch (Exception e) {
         e.printStackTrace();
      } 
      return result;
   }
    
   public boolean remove(Connection conn){
      boolean result = false;
       
      String query = "DELETE FROM projetos WHERE id = ?";
       
      try{
         PreparedStatement stm = conn.prepareStatement(query);
         stm.setInt(1, getId());
          
         stm.execute();  
         result = true;     
      }catch (Exception e) {
         e.printStackTrace();
      } 
      return result;
   }
    
   public boolean update(Connection conn){
      boolean result = false;
       
      String query = "UPDATE projetos SET titulo = ?, duracao = ?, orcamento = ?, areas_conhecimento_id = ?, instituicao_id = ?, avaliadores_id = ?, pesquisadores_id = ? WHERE id = ?";
       
      try{
         PreparedStatement stm = conn.prepareStatement(query);
      
         stm.setString(1, getTitulo());
         stm.setString(2, getDuracao());
         stm.setDouble(3, getOrcamento());
         stm.setInt(4, getAreaDeConhecimento().getId());
         stm.setInt(5, getInstituicao().getId());
         stm.setInt(6, getAvaliador().getId());
         stm.setInt(7, getPesquisador().getId());
         stm.setInt(8, getId());
         stm.execute();
         
         result = true;
      }catch(Exception e) {
         e.printStackTrace();
      } 
      return result;
   }
   
   public boolean updateAnswer(Connection conn){
      boolean result = false;
      String query = "UPDATE projetos SET resposta = ?, data_resposta = NOW() WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getResposta());
         stmt.setInt(2, getId());
         stmt.execute();
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
    
   public Projeto select(Connection conn){
      String sqlSelect = "SELECT id, titulo, duracao, orcamento, areas_conhecimento_id, data_resposta, resposta, pesquisadores_id, instituicao_id, avaliadores_id FROM projetos WHERE id = ?";
        
      try (PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
         stm.setInt(1, getId());
         try (ResultSet rs = stm.executeQuery();) {
         
            if (rs.next()) {
               setTitulo(rs.getString("titulo"));
               setDuracao(rs.getString("duracao"));
               setOrcamento(rs.getDouble("orcamento"));
               setAreaDeConhecimento(new AreaDeConhecimento(rs.getInt("areas_conhecimento_id")));
               setDataResposta(rs.getDate("data_resposta"));
               System.out.println(rs.getInt("resposta"));
               setResposta(rs.getInt("resposta"));
               setPesquisador(new Pesquisador(rs.getInt("pesquisadores_id")));
               setInstituicao(new Instituicao(rs.getInt("instituicao_id")));
               setAvaliador(new Avaliador(rs.getInt("avaliadores_id")));
               
               this.getAreaDeConhecimento().select(conn);
               this.getPesquisador().select(conn);
               this.getInstituicao().select(conn);
               this.getAvaliador().select(conn);
            }	
         }  catch (Exception e) {
            e.printStackTrace();
         }
      } catch (SQLException e1) {
         System.out.print(e1.getStackTrace());
      }
      return this;
   }   
     
   public ArrayList<Projeto> getAll(Connection conn){
      String query = "SELECT id, titulo, duracao, orcamento, areas_conhecimento_id, data_resposta, resposta, pesquisadores_id, instituicao_id, avaliadores_id FROM projetos";
        
      ArrayList<Projeto> projetos = new ArrayList<>();
      try{
         PreparedStatement stm = conn.prepareStatement(query);
         ResultSet rs = stm.executeQuery();
         while(rs.next()){
            Projeto projeto = new Projeto();
            projeto.setId(rs.getInt("id"));
            projeto.setTitulo(rs.getString("titulo"));
            projeto.setDuracao(rs.getString("duracao"));
            projeto.setOrcamento(rs.getDouble("orcamento"));
            projeto.setAreaDeConhecimento(new AreaDeConhecimento(rs.getInt("areas_conhecimento_id")));
            projeto.setDataResposta(rs.getDate("data_resposta"));
            projeto.setResposta(rs.getInt("resposta"));
            projeto.setPesquisador(new Pesquisador(rs.getInt("pesquisadores_id")));
            projeto.setInstituicao(new Instituicao(rs.getInt("instituicao_id")));
            projeto.setAvaliador(new Avaliador(rs.getInt("avaliadores_id")));
            
            projeto.getAreaDeConhecimento().select(conn);
            projeto.getPesquisador().select(conn);
            projeto.getInstituicao().select(conn);
            projeto.getAvaliador().select(conn);
            projetos.add(projeto);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return projetos;
   }    
   
   @Override
    public String toString(){
      String resp = "Projeto[ID=" + this.id + ", Titulo=" + this.titulo + ", Duracao=" + this.duracao + ", Orcamento=" + this.orcamento;
      if(this.resposta != 0){
         String res = "Reprovado";
         if(this.resposta == 2){
            res =  "Aprovado";
         }
         resp += ", Resposta=" + res + ", Data da Resposta=" + this.dataResposta + "";
      }
      resp += ", Area de Conhecimento=" + this.areaDeConhecimento +"]";
    
      return resp;
   }
       
}