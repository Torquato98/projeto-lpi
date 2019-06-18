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
   private Time duracao;
   private double orcamento;
   private AreaDeConhecimento areaDeConhecimento;
   private Date dataResposta;
   private int resposta = -1;
   private Instituicao instituicao;

   public Projeto(){
    
   }

   public Projeto(int id, String titulo, Time duracao, double orcamento, AreaDeConhecimento areaDeConhecimento, Date dataDeResposta, int resposta){
      this.id = id;
      this.titulo = titulo;
      this.duracao = duracao;
      this.orcamento = orcamento;
      this.areaDeConhecimento = areaDeConhecimento;
   }

   public void setId(int id){
      this.id = id;
   }

   public void setTitulo(String titulo){
      this.titulo = titulo;
   }

   public void setDuracao(Time duracao){
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

   public int getId(){
      return this.id;
   }

   public String getTitulo(){
      return this.titulo;
   }

   public Time getDuracao(){
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
    
   public boolean incluir(Connection conn){
      boolean result = false;
       
      String sqlInsert = "INSERT INTO projetos(titulo, duracao, orcamento, areas_conhecimento_id, instituicao_id) VALUES (?,?,?,?,?)";
          
      try(PreparedStatement stm = conn.prepareStatement(sqlInsert);){
         stm.setString(1, getTitulo());
         stm.setTime(2, getDuracao());
         stm.setDouble(3, getOrcamento());
         stm.setInt(4, getAreaDeConhecimento().getId());
         stm.setInt(5, getInstituicao().getId());
         stm.execute();
         result = true;
      }catch (Exception e) {
         e.printStackTrace();
         try {
            conn.rollback();
         } 
         catch (SQLException e1) {
            System.out.print(e1.getStackTrace());
         }
      } 
      return result;
   }
    
   public boolean excluir(Connection conn){
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
    
   public boolean atualizar(Connection conn){
      boolean result = false;
       
      String query = "UPDATE projetos SET titulo = ?, duracao = ?, orcamento = ?, areas_conhecimento_id = ?, instituicao_id = ? WHERE id = ?";
       
      try{
         PreparedStatement stm = conn.prepareStatement(query);
      
         stm.setString(1, getTitulo());
         stm.setTime(2, getDuracao());
         stm.setDouble(3, getOrcamento());
         stm.setInt(4, getAreaDeConhecimento().getId());
         stm.setInt(5, getInstituicao().getId());
         stm.setInt(6, getId());
         stm.execute();
         
         result = true;
      }catch(Exception e) {
         e.printStackTrace();
      } 
      return result;
   }
   
   public boolean updateAnswer(Connection conn){
      boolean result = false;
      String query = "UPDATE projetos SET resposta = ?, data_resposta = ? WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(0, getResposta());
         stmt.setDate(1, getDataResposta());
         stmt.setInt(2, getId());
         stmt.execute();
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
    
   public Projeto select(Connection conn){
      String sqlSelect = "SELECT id, titulo, duracao, orcamento, areas_conhecimento_id, data_resposta, resposta FROM projetos WHERE id = ?";
        
      try (PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
         stm.setInt(1, getId());
         try (ResultSet rs = stm.executeQuery();) {
         
            if (rs.next()) {
               setTitulo(rs.getString("titulo"));
               setDuracao(rs.getTime("duracao"));
               setOrcamento(rs.getDouble("orcamento"));
               setAreaDeConhecimento(new AreaDeConhecimento(rs.getInt("areas_conhecimento_id")));
               setDataResposta(rs.getDate("data_resposta"));
               setResposta(rs.getInt("resposta"));
               
               this.getAreaDeConhecimento().select(conn);
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
      String query = "SELECT id, titulo, duracao, orcamento, areas_conhecimento_id, data_resposta, resposta FROM projetos WHERE id = ?";
        
      ArrayList<Projeto> projetos = new ArrayList<>();
      try{
         PreparedStatement stm = conn.prepareStatement(query);
         ResultSet rs = stm.executeQuery();
         while(rs.next()){
            Projeto projeto = new Projeto();
            projeto.setId(rs.getInt("id"));
            projeto.setTitulo(rs.getString("titulo"));
            projeto.setDuracao(rs.getTime("duracao"));
            projeto.setOrcamento(rs.getDouble("orcamento"));
            projeto.setAreaDeConhecimento(new AreaDeConhecimento(rs.getInt("areas_conhecimento_id")));
            projeto.setDataResposta(rs.getDate("data_resposta"));
            projeto.setResposta(rs.getInt("resposta"));
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
      if(this.resposta != -1){
         String res = "Reprovado";
         if(this.resposta == 1){
            res =  "Aprovado";
         }
         resp += ", Resposta=" + res + ", Data da Resposta=" + this.dataResposta + "";
      }
      resp += ", Area de Conhecimento=" + this.areaDeConhecimento +"]";
    
      return resp;
   }
       
}