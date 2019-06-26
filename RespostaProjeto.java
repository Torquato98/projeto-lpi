import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.Connection;

public class RespostaProjeto extends JFrame implements ActionListener{

   final int LARGURA_TELA = 400;
   final int ALTURA_TELA = 150;

   private JLabel lblResposta;
   private JLabel lblTitle;
   private JComboBox cmbResposta;
   
   private JButton btnSalvar;
   private JButton btnVoltar;
   
   private JPanel pnlNorte;
   private JPanel pnlCentro;
   private JPanel pnlSul;
   
   private Connection conn;
   private Projeto projeto;

   public RespostaProjeto(Connection conn, Projeto projeto){
      super("Atualizar resposta de projeto");
      
      this.conn = conn;
      this.projeto = projeto;
      
      lblTitle = new JLabel("Projeto: " + this.projeto.getTitulo());
      lblResposta = new JLabel("Resposta do Projeto");
      cmbResposta = new JComboBox<String>();
      cmbResposta.addItem("Aprovado");
      cmbResposta.addItem("Reprovado");
      
      btnSalvar = new JButton("Salvar");
      btnVoltar = new JButton("Voltar");
      
      Container caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      pnlNorte = new JPanel(new FlowLayout());
      pnlCentro = new JPanel(new FlowLayout());
      pnlSul = new JPanel(new FlowLayout());
      
      pnlNorte.add(lblTitle);
      
      pnlCentro.add(lblResposta);
      pnlCentro.add(cmbResposta);
      
      pnlSul.add(btnSalvar);
      pnlSul.add(btnVoltar);
      
      caixa.add(pnlNorte, BorderLayout.NORTH);
      caixa.add(pnlCentro, BorderLayout.CENTER);
      caixa.add(pnlSul, BorderLayout.SOUTH);
      
      btnSalvar.addActionListener(this);
      btnVoltar.addActionListener(this);
      
      setSize(LARGURA_TELA, ALTURA_TELA);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      setVisible(true);
   }
   
   public void actionPerformed(ActionEvent e){
      if(e.getSource() == btnSalvar){
         String strResposta = String.valueOf(cmbResposta.getSelectedItem());
         int resposta = 1;
         if(strResposta.equals("Aprovado")){
            resposta = 2;
         }
         this.projeto.setResposta(resposta);
         this.projeto.updateAnswer(this.conn);
         dispose();
         JOptionPane.showMessageDialog(this, "Projeto " + this.projeto.getTitulo() + " " + strResposta + " com sucesso");
         TelaProjetos projeto = new TelaProjetos(conn);
      }else if(e.getSource() == btnVoltar){
         dispose();
         TelaProjetos projeto = new TelaProjetos(conn);
         
      }
   }
   
}