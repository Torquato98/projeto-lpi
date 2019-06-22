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
         Projeto projeto = new Projeto();
         projeto.setTitulo(txtTitulo.getText());
         projeto.setDuracao(txtDuracao.getText());
         projeto.setOrcamento(Double.parseDouble(txtOrcamento.getText()));
         
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

}
