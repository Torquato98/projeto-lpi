import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;

public class Avaliador{

   private int id;
   private String grau;
   private String nome;
   private char sexo;
   private String rg;
   private String cpf;
   private Date dataNasc;
   private Instituicao instituicao;
   private AreaPesquisa areaPesquisa;
   
   public Avaliador(){
   
   }
   
   public Avaliador(int id){
      this.id = id;
   }
   
   public Avaliador(int id, String grau, String nome, char sexo, String rg, String cpf, Date dataNasc, Instituicao instituicao, AreaPesquisa areaPesquisa){
      this.id = id;
      this.grau = grau;
      this.nome = nome;
      this.sexo = sexo;
      this.rg = rg;
      this.cpf = cpf;
      this.dataNasc = dataNasc;
      this.instituicao = instituicao;
      this.areaPesquisa = areaPesquisa;
   }
   
   public void setId(int id){
      this.id = id;
   } 
   
   public void setGrau(String grau){
      this.grau = grau; 
   }
   
   public void setNome(String nome){
      this.nome = nome;
   }
   
   public void setSexo(char sexo){
      this.sexo = sexo;
   }
   
   public void setRg(String rg){
      this.rg = rg;
   }
   
   public void setCpf(String cpf){
      this.cpf = cpf;
   }
   
   public void setDataNasc(Date dataNasc){
      this.dataNasc = dataNasc;
   } 
   
   public void setInstituicao(Instituicao instituicao){
      this.instituicao = instituicao;
   }
   
   public void setAreaPesquisa(AreaPesquisa areaPesquisa){
      this.areaPesquisa = areaPesquisa;
   }
   
   public int getId(){
      return id;
   }
   
   public String getGrau(){
      return grau;
   }
   
   public String getNome(){
      return nome;
   }
   
   public char getSexo(){
      return sexo;
   }
   
   public String getRg(){
      return rg;
   }
   
   public String getCpf(){
      return cpf;
   }
   
   public Date getDataNasc(){
      return dataNasc;
   }
   
   public Instituicao getInstituicao(){
      return instituicao;
   }
   
   public AreaPesquisa getAreaPesquisa(){
      return areaPesquisa;
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
      String query = "INSERT INTO avaliadores(grau, nome, sexo, rg, cpf, dt_nasc, instituicao_id, areas_pesquisa_id) VALUES(?,?,?,?,?,?,?,?)";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setString(1, getGrau());
         stmt.setString(2, getNome());
         stmt.setString(3, getSexo() + "");
         stmt.setString(4, getRg());
         stmt.setString(5, getCpf());
         stmt.setDate(6, getDataNasc());
         stmt.setInt(7, getInstituicao().getId());
         stmt.setInt(8, getAreaPesquisa().getId());
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
      String query = "UPDATE avaliadores SET grau = ?, nome = ?, sexo = ?, rg = ?, cpf = ?, data_nasc = ?, instituicao_id = ?, areas_pesquisa_id = ? WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setString(1, getGrau());
         stmt.setString(2, getNome());
         stmt.setString(3, getSexo() + "");
         stmt.setString(4, getRg());
         stmt.setString(5, getCpf());
         stmt.setDate(6, getDataNasc());
         stmt.setInt(7, getInstituicao().getId());
         stmt.setInt(8, getAreaPesquisa().getId());
         stmt.setInt(9, getId());
         stmt.execute();
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public Avaliador select(Connection conn){
      String query = "SELECT grau, nome, sexo, rg, cpf, dt_nasc, instituicao_id, areas_pesquisa_id FROM avaliadores WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();
         if(rs.next()){
            this.setGrau(rs.getString("grau"));
            this.setNome(rs.getString("nome"));
            this.setSexo(rs.getString("sexo").charAt(0));
            this.setRg(rs.getString("rg"));
            this.setCpf(rs.getString("cpf"));
            this.setDataNasc(rs.getDate("dt_nasc"));
            this.setInstituicao(new Instituicao(rs.getInt("instituicao_id")));
            this.setAreaPesquisa(new AreaPesquisa(rs.getInt("areas_pesquisa_id")));
            
            this.getInstituicao().select(conn);
            this.getAreaPesquisa().select(conn);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return this;
   }
   
   public ArrayList<Avaliador> getAll(Connection conn){
      ArrayList<Avaliador> lista = new ArrayList<>();
      String query = "SELECT id, grau, nome, sexo, rg, cpf, dt_nasc, instituicao_id, areas_pesquisa_id FROM avaliadores";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();
         while(rs.next()){
            Avaliador avaliador = new Avaliador(rs.getInt("id"));
            avaliador.setGrau(rs.getString("grau"));
            avaliador.setNome(rs.getString("nome"));
            avaliador.setSexo(rs.getString("sexo").charAt(0));
            avaliador.setRg(rs.getString("rg"));
            avaliador.setCpf(rs.getString("cpf"));
            avaliador.setDataNasc(rs.getDate("dt_nasc"));
            avaliador.setInstituicao(new Instituicao(rs.getInt("instituicao_id")));
            avaliador.setAreaPesquisa(new AreaPesquisa(rs.getInt("areas_pesquisa_id")));
            
            avaliador.getInstituicao().select(conn);
            avaliador.getAreaPesquisa().select(conn);
            lista.add(avaliador);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return lista;
   }

}