import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;

public class Pesquisador{

   private int id;
   private String rg;
   private String cpf;
   private String nome;
   private char sexo;
   private Date dataNasc;
   private Instituicao instituicao;
   private PesquisadorGrauConhecimento pesquisadorGrauConhecimento;
   
   public Pesquisador(){
   
   }
   
   public Pesquisador(Instituicao instituicao){
      this.instituicao = instituicao;
   }
   
   public Pesquisador(int id){
      this.id = id;
   }
   
   public Pesquisador(int id, String rg, String cpf, String nome, char sexo, Date dataNasc, Instituicao instituicao, PesquisadorGrauConhecimento pesquisadorGrauConhecimento){
      this.id = id;
      this.rg = rg;
      this.cpf = cpf;
      this.nome = nome;
      this.sexo = sexo;
      this.dataNasc = dataNasc;
      this.instituicao = instituicao;
      this.pesquisadorGrauConhecimento = pesquisadorGrauConhecimento;
   }
   
   public void setId(int id){
      this.id = id;
   }
   
   public void setRg(String rg){
      this.rg = rg;
   }
   
   public void setCpf(String cpf){
      this.cpf = cpf;
   }
   
   public void setNome(String nome){
      this.nome = nome;
   }
   
   public void setSexo(char sexo){
      this.sexo = sexo;
   }
   
   public void setDataNasc(Date dataNasc){
      this.dataNasc = dataNasc;
   }
   
   public void setInstituicao(Instituicao instituicao){
      this.instituicao = instituicao;
   }
   
   public void setPesquisadorGrauConhecimento(PesquisadorGrauConhecimento pesquisadorGrauConhecimento){
      this.pesquisadorGrauConhecimento = pesquisadorGrauConhecimento;
   }
   
   public int getId(){
      return id;
   }
   
   public String getRg(){
      return rg;
   }
   
   public String getCpf(){
      return cpf;
   }
   
   public String getNome(){
      return nome;
   }
   
   public char getSexo(){
      return sexo;
   }
   
   public Date getDataNasc(){
      return dataNasc;
   }
   
   public Instituicao getInstituicao(){
      return instituicao;
   }
   
   public PesquisadorGrauConhecimento getPesquisadorGrauConhecimento(){
      return pesquisadorGrauConhecimento;
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
      String query = "INSERT INTO pesquisadores(rg, cpf, nome, sexo, data_nasc, instituicao_id) VALUES(?,?,?,?,?,?)";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setString(1, getRg());
         stmt.setString(2, getCpf());
         stmt.setString(3, getNome());
         stmt.setString(4, getSexo() + "");
         stmt.setDate(5, getDataNasc());
         stmt.setInt(6, getInstituicao().getId());
         stmt.execute();
         
         this.getLastIdInserted(conn);
         this.getPesquisadorGrauConhecimento().setPesquisadoresId(this.getId());
         this.getPesquisadorGrauConhecimento().insert(conn);
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public boolean update(Connection conn){
      boolean result = false;
      String query = "UPDATE pesquisadores SET rg = ?, cpf = ?, nome = ?, sexo = ?, data_nasc = ?, instituicao_id = ? WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setString(1, getRg());
         stmt.setString(2, getCpf());
         stmt.setString(3, getNome());
         stmt.setString(4, getSexo() + "");
         stmt.setDate(5, getDataNasc());
         stmt.setInt(6, getInstituicao().getId());
         stmt.setInt(7, getId());
         stmt.execute();
         
         this.getPesquisadorGrauConhecimento().setPesquisadoresId(this.getId());
         this.getPesquisadorGrauConhecimento().update(conn);
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public boolean remove(Connection conn){
      boolean result = false;
      String query = "DELETE FROM pesquisadores WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getId());
         stmt.execute();
         
         result = true;
      }catch(Exception e){
         e.printStackTrace();
      }
      return result;
   }
   
   public Pesquisador select(Connection conn){
      String query = "SELECT id, rg, cpf, nome, sexo, data_nasc, instituicao_id FROM pesquisadores WHERE id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getId());
         ResultSet rs = stmt.executeQuery();
         if(rs.next()){
            this.setId(rs.getInt("id"));
            this.setNome(rs.getString("nome"));
            this.setRg(rs.getString("rg"));
            this.setCpf(rs.getString("cpf"));
            this.setSexo(rs.getString("sexo").charAt(0));
            this.setDataNasc(rs.getDate("data_nasc"));
            this.setInstituicao(new Instituicao(rs.getInt("instituicao_id")));
            this.setPesquisadorGrauConhecimento(new PesquisadorGrauConhecimento(rs.getInt("id")+""));
            
            this.getPesquisadorGrauConhecimento().getByPesquisadorId(conn);
            this.getInstituicao().select(conn);
            
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return this;
   }
   
   public ArrayList<Pesquisador> getAllByInstituicao(Connection conn){
      ArrayList<Pesquisador> lista = new ArrayList<>();
      String query = "SELECT id, rg, cpf, nome, sexo, data_nasc, instituicao_id FROM pesquisadores WHERE instituicao_id = ?";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         stmt.setInt(1, getInstituicao().getId());
         ResultSet rs = stmt.executeQuery();
         while(rs.next()){
            Pesquisador pesquisador = new Pesquisador();
            pesquisador.setId(rs.getInt("id"));
            pesquisador.setRg(rs.getString("rg"));
            pesquisador.setCpf(rs.getString("cpf"));
            pesquisador.setNome(rs.getString("nome"));
            pesquisador.setSexo(rs.getString("sexo").charAt(0));
            pesquisador.setDataNasc(rs.getDate("data_nasc"));
            pesquisador.setInstituicao(new Instituicao(rs.getInt("instituicao_id")));
            
            pesquisador.getInstituicao().select(conn);
            lista.add(pesquisador);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return lista;
   }
   
   public ArrayList<Pesquisador> getAll(Connection conn){
      ArrayList<Pesquisador> lista = new ArrayList<>();
      String query = "SELECT id, rg, cpf, nome, sexo, data_nasc, instituicao_id FROM pesquisadores";
      try{
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();
         while(rs.next()){
            Pesquisador pesquisador = new Pesquisador();
            pesquisador.setId(rs.getInt("id"));
            pesquisador.setRg(rs.getString("rg"));
            pesquisador.setCpf(rs.getString("cpf"));
            pesquisador.setNome(rs.getString("nome"));
            pesquisador.setSexo(rs.getString("sexo").charAt(0));
            pesquisador.setDataNasc(rs.getDate("data_nasc"));
            pesquisador.setInstituicao(new Instituicao(rs.getInt("instituicao_id")));
            pesquisador.setPesquisadorGrauConhecimento(new PesquisadorGrauConhecimento(rs.getInt("id")+""));
            
            pesquisador.getInstituicao().select(conn);
            pesquisador.getPesquisadorGrauConhecimento().getByPesquisadorId(conn);
            lista.add(pesquisador);
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      return lista;
   }
   
   @Override
   public String toString(){
      return "Pesquisador[ID = " + id + ", nome = " + nome + ", sexo = " + sexo + ", data de nascimento " + dataNasc + ", instituicao = " + instituicao +  ", grau de conhecimento = " + pesquisadorGrauConhecimento.getGrauConhecimento() + " ]";
   }
   
}