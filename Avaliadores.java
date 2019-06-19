import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Avaliadores{
    private int id;
    private String nome, rg , cpf, dataDeNascimento;
    private char sexo;

    public Avaliadores(){
        
    }

    public Avaliadores(int id, String nome, String rg, String cpf, String dataDeNascimento, char sexo){
        this.id = id;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.sexo = sexo;
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getRg(){
        return rg;
    }

    public String getCpf(){
        return cpf;
    }

    public String getDataDeNascimento(){
        return dataDeNascimento;
    }

    public char getSexo(){
        return sexo;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setRg(String rg){
        this.rg = rg;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public void setSexo(char sexo){
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Avaliador\n" + "id= " + id + "\nnome= " + nome + "\nrg= " + rg + "\ncpf= " + cpf + "\ndata de nascimento= " + dataDeNascimento + "\nsexo= " + sexo;
    }

    public Avaliadores select(Connection conn){
         String query = "SELECT avaliadores.id, avaliadores.nome, projetos.resposta FROM avaliadores INNER JOIN projetos ON avaliadores.avaliadores_id = projetos.avaliadores_id WHERE id = ?, nome = ?, resposta = ?";
         try{
               PreparedStatement stmt = conn.prepareStatement(query);
               stmt.setInt(0, this.getId());
               stmt.setInt(1, this.getNome());
               
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

    public Avaliadores aprovados(Connection conn){
         String query = "UPDATE projetos SET resposta = 1";
         try{
               PreparedStatement stmt = conn.prepareStatement(query);
               stmt.setInt(0, this.getId());
               stmt.setInt(1, this.getNome());
               
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

        public Avaliadores reprovados(Connection conn){
         String query = "UPDATE projetos SET resposta = 0";
         try{
               PreparedStatement stmt = conn.prepareStatement(query);
               stmt.setInt(0, this.getId());
               stmt.setInt(1, this.getNome());
               
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
}
