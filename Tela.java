import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Tela extends JFrame implements ActionListener,ListSelectionListener{

   final int LARGURA_TELA = 600;
   final int ALTURA_TELA = 300;
   final int LARGURA_SCROLL_PANE = LARGURA_TELA - 200;
   final int ALTURA_SCROLL_PANE = ALTURA_TELA - 110;
   private String[] colunas = {"ID", "Titulo", "Área de Conhecimento", "Duração","Orçamento"};
   private Object[][] projetos;
   
   private Container caixa;
   private Integer[] pedidos;
   private JTable tabelaProjetos;
   private JPanel pnlCentro,pnlBtn,pnlLbl;
   private JScrollPane rolagem;
   private JButton btnExcluir,btnAlterar,btnInserir;
   private JLabel lblProjeto;
   private Connection conn;

   
   public Tela (Connection conn){
      super("Menu");
      this.conn = conn;
      instanciaJTableEScrollPane(conn);
      btnExcluir = new JButton ("Excluir");
      btnInserir = new JButton("Inserir Projetos");
      btnAlterar = new JButton("Alterar");
      lblProjeto = new JLabel("Projetos");
      
      
      caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      pnlCentro = new JPanel(new FlowLayout());
      pnlBtn = new JPanel(new FlowLayout());
      pnlLbl = new JPanel(new FlowLayout());
      pnlLbl.add(lblProjeto);
      pnlBtn.add(btnInserir);
      pnlBtn.add(btnAlterar);
      pnlBtn.add(btnExcluir);
      pnlCentro.add(rolagem);
      caixa.add(pnlCentro, BorderLayout.CENTER);
      caixa.add(pnlBtn,BorderLayout.SOUTH);
      caixa.add(pnlLbl,BorderLayout.NORTH);
      
      btnExcluir.addActionListener(this);
      btnInserir.addActionListener(this);
      btnAlterar.addActionListener(this);
      
      setSize(LARGURA_TELA, ALTURA_TELA);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      
   }
   public void actionPerformed(ActionEvent e){
      conn = this.conn;
      if(e.getSource()==btnInserir){
         dispose();
         Inserir i = new Inserir(conn);
      }
      else if(e.getSource()==btnAlterar){
         dispose();
         Alterar a = new Alterar(conn,tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),0)+"");
      }
      else if(e.getSource()==btnExcluir){
         int i = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o projeto "+tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),1)+"?", "ATENÇÃO",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null); 
         if (i == JOptionPane.YES_OPTION) { 
            excluirProjeto(conn,tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),0)+"");
            JOptionPane.showMessageDialog(null,"Projeto excluido com sucesso!!");
            caixa.remove(pnlCentro);
            pnlCentro.remove(rolagem);
            instanciaJTableEScrollPane(conn); 
            pnlCentro.add(rolagem);
            caixa.add(pnlCentro, BorderLayout.CENTER);
            validate();
            repaint();
         } 
      }
   
   }
   public void valueChanged(ListSelectionEvent e){
      /* colocar dentro deste if porque o evento e disparado duas vezes e assim 
         filtra-se somente um deles */
      if(e.getValueIsAdjusting()){
         String resultado = 
            tabelaProjetos.getColumnName(0)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),0)+
            "\n"+tabelaProjetos.getColumnName(1)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),1)+
            "\n"+tabelaProjetos.getColumnName(2)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),2)+
            "\n"+tabelaProjetos.getColumnName(3)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),3)+
            "\n"+tabelaProjetos.getColumnName(4)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),4);
            
         JOptionPane.showMessageDialog(this, resultado);
      }
   }
   public String[][] carregaDados(Connection conn){
   
      Projeto p = new Projeto();
      
      ArrayList<Projeto> lista = p.getAll(conn);

      DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      
      String[][] saida = new String[lista.size()][colunas.length];
      Projeto projeto;
      for(int i = 0; i < lista.size(); i++){
         projeto = lista.get(i);
         
         saida[i][0] = projeto.getId()+"";
         saida[i][1] = projeto.getTitulo();
         saida[i][2] = projeto.getAreaDeConhecimento().getNome();
         saida[i][3] = projeto.getDuracao();
         //formata o numero com 2 casas decimais
         saida[i][4] = String.format("%.2f", projeto.getOrcamento());
         
      }
      
      return saida;
   }
   //metodo para centralizar a instanciacao da JTable e nao ficar repetindo codigo
   public void instanciaJTableEScrollPane(Connection conn){
      
      //carrega a matriz de pedidos para instanciar a JTable
      projetos = carregaDados(this.conn);
      tabelaProjetos = new JTable(projetos, colunas);
      tabelaProjetos.getSelectionModel().addListSelectionListener(this);
      //coloca a JTable em um scroll pane para ter a barra de rolagem
      rolagem = new JScrollPane(tabelaProjetos);
      rolagem.setPreferredSize(new Dimension(LARGURA_SCROLL_PANE, 
                              ALTURA_SCROLL_PANE));
   }
   
   public void excluirProjeto(Connection conn,String id) {
      String query = 
         "DELETE FROM projetos WHERE id = ?";
   
      try (PreparedStatement stm = conn.prepareStatement(query);) {
      
         stm.setInt(1,Integer.parseInt(id));
      
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