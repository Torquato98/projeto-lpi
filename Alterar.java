import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Alterar extends JFrame implements ActionListener  {

   private JTextField txtTitulo, txtDuracao, txtOrcamento, txtPesquisador, txtInstituicao;
   private JLabel lblTitulo,lblDuracao,lblOrcamento,lblPesquisador,lblInstituicao,lblAlterar;
   private JButton btnAlterar,btnVoltar;
   private Connection conn;
   private String id;
   
   public Alterar(Connection conn,String id) {
      
      super("Alterar Projetos");
      this.conn = conn;
      this.id = id;
      
      lblTitulo = new JLabel ("Titulo");
      lblDuracao = new JLabel ("Duração");
      lblOrcamento = new JLabel ("Orçamento");
      lblAlterar = new JLabel("Alteração");

      lblPesquisador = new JLabel("Pesquisador");
      lblInstituicao = new JLabel("Instituição");
      
      txtTitulo = new JTextField(20);
      txtDuracao = new JTextField(10);
      txtOrcamento = new JTextField(10);
      txtPesquisador = new JTextField(20);
      txtInstituicao = new JTextField(25);
      
      btnAlterar = new JButton("Alterar");
      btnVoltar = new JButton("Voltar");
      

      
      Container cx = getContentPane();
      cx.setLayout(new BorderLayout());
      JPanel pnlBtn = new JPanel(new FlowLayout());
      JPanel pnlCentro = new JPanel(new GridLayout(6,1));
      JPanel pnlLbl = new JPanel(new FlowLayout());
      JPanel pnl1 = new JPanel(new FlowLayout ());
      JPanel pnl2 = new JPanel(new FlowLayout (FlowLayout.LEFT));
      JPanel pnl3 = new JPanel(new FlowLayout (FlowLayout.LEFT));
      JPanel pnl4 = new JPanel(new FlowLayout (FlowLayout.LEFT));
      JPanel pnl5 = new JPanel(new FlowLayout (FlowLayout.LEFT));
      JPanel pnl6 = new JPanel(new FlowLayout (FlowLayout.LEFT));
      
      
      pnlLbl.add(lblAlterar);
      pnl2.add(lblTitulo);
      pnl2.add(txtTitulo);
      pnl3.add(lblDuracao);
      pnl3.add(txtDuracao);
      pnl4.add(lblOrcamento);
      pnl4.add(txtOrcamento);
      pnl5.add(lblPesquisador);
      pnl5.add(txtPesquisador);
      pnl6.add(lblInstituicao);
      pnl6.add(txtInstituicao);
      pnlCentro.add(pnl2);
      pnlCentro.add(pnl3);
      pnlCentro.add(pnl4);
      pnlCentro.add(pnl5);
      pnlCentro.add(pnl6);
      pnlBtn.add(btnAlterar);
      pnlBtn.add(btnVoltar);
      cx.add(pnlLbl,BorderLayout.NORTH);
      cx.add(pnlCentro,BorderLayout.CENTER);
      cx.add(pnlBtn,BorderLayout.SOUTH);
      
      preencher(id);
      
      btnVoltar.addActionListener(this);
      btnAlterar.addActionListener(this);
      
      setSize(400,300);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   
   }
   public void actionPerformed (ActionEvent e){
      if(e.getSource()==btnVoltar){
         dispose();
         ConexaoBD bd = new ConexaoBD();
         try{
            Connection conn = bd.conectar();
            new Tela(conn);
         } 
         catch (SQLException f){
            f.printStackTrace();
         }
         
      }
      else if(e.getSource()==btnAlterar){
         alterar(id);
         JOptionPane.showMessageDialog(null,"Projeto alterado com sucesso!!");
         dispose();
         ConexaoBD bd = new ConexaoBD();
         try{
            Connection conn = bd.conectar();
            new Tela(conn);
         } 
         catch (SQLException m){
            m.printStackTrace();
         }
      }
        }

   public void preencher(String id){
      String query = "SELECT * FROM projetos WHERE id = ? ";
      
      try (PreparedStatement stm = conn.prepareStatement(query);) {
         stm.setInt(1, Integer.parseInt(id));
         try (ResultSet rs = stm.executeQuery();) {
               
            while(rs.next()){
               txtTitulo.setText(rs.getString("titulo"));
               txtDuracao.setText(rs.getString("duracao"));
               txtOrcamento.setText(rs.getString("orcamento"));
               listarPesquisador(rs.getString("pesquisadores_id"));
               listarInstituicao(rs.getString("instituicao_id"));
            }
         
         }catch(Exception e){
            e.printStackTrace();
         }
      }catch (SQLException e1) {
         System.out.print(e1.getStackTrace()+e1.getMessage());
      
      }
   
   }
   public void listarPesquisador(String id){
      String query = "SELECT id,nome FROM pesquisadores  WHERE id = ? ";  
      try (PreparedStatement stm = conn.prepareStatement(query);) {
         stm.setInt(1, Integer.parseInt(id));
         try (ResultSet rs = stm.executeQuery();) {
               
            while(rs.next()){
               txtPesquisador.setText(String.valueOf(rs.getInt("id"))+ " - " +(rs.getString("nome")));
            }
         
         }catch(Exception e){
            e.printStackTrace();
         }
      }catch (SQLException e1) {
         System.out.print(e1.getStackTrace()+e1.getMessage());
      
      }
         
   }
   public void listarInstituicao(String id){
      String query = "SELECT id,nome FROM instituicao  WHERE id = ? ";  
      try (PreparedStatement stm = conn.prepareStatement(query);) {
         stm.setInt(1, Integer.parseInt(id));
         try (ResultSet rs = stm.executeQuery();) {
               
            while(rs.next()){
               txtInstituicao.setText(String.valueOf(rs.getInt("id"))+ " - " +(rs.getString("nome")));
            }
         
         }catch(Exception e){
            e.printStackTrace();
         }
      }catch (SQLException e1) {
         System.out.print(e1.getStackTrace()+e1.getMessage());
      
      }
         
   }
   public void alterar(String id) {
      String sqlUpdate = 
         "UPDATE projetos SET titulo=?, duracao=?, orcamento=? WHERE id = ?";
   
      try (PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
         stm.setString(1, txtTitulo.getText());
         stm.setString(2, txtDuracao.getText());
         stm.setDouble(3, Double.parseDouble(txtOrcamento.getText()));
         stm.setInt(4, Integer.parseInt(id));
      
         stm.execute();
      } catch (Exception e) {
         e.printStackTrace();
         try {
            conn.rollback();
         } catch (SQLException e1) {
            System.out.print(e1.getStackTrace());
         }
      }
   }
   
   
   
}