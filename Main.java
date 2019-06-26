import java.sql.*;

public class Main{
   public static void main(String[] args){
   // obtem conexao com o banco, que sera usada todo o tempo
      ConexaoBD bd = new ConexaoBD();
      try{
         Connection conn = bd.conectar();
         new AgenciaApp(conn);
      } 
      catch (SQLException e){
         e.printStackTrace();
      }
   }
}