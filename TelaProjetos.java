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

public class TelaProjetos extends JFrame implements ActionListener,ListSelectionListener{

   final int LARGURA_TELA = 600;
   final int ALTURA_TELA = 300;
   final int LARGURA_SCROLL_PANE = LARGURA_TELA - 200;
   final int ALTURA_SCROLL_PANE = ALTURA_TELA - 110;
   private String[] colunas = {"ID", "Titulo", "Area de Conhecimento", "Duração","Orçamento"};
   private Object[][] projetos;
   
   
   private Container caixa;
   private Integer[] pedidos;
   private JTable tabelaProjetos;
   private JPanel pnlCentro,pnlBtn,pnlLbl;
   private JScrollPane rolagem;
   private JButton btnExcluir,btnAlterar,btnInserir, btnResposta, btnVoltar;
   private JLabel lblProjeto;
   private Connection conn;

   
   public TelaProjetos(Connection conn){
      super("Lista De Projetos");
      this.conn = conn;
      instanciaJTableEScrollPane(conn);
      btnExcluir = new JButton ("Excluir");
      btnInserir = new JButton("Inserir Projetos");
      btnAlterar = new JButton("Alterar");
      btnResposta = new JButton("Resposta");
      btnVoltar = new JButton("Voltar");
      
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
      pnlBtn.add(btnResposta);
      pnlBtn.add(btnVoltar);
      
      pnlCentro.add(rolagem);
      caixa.add(pnlCentro, BorderLayout.CENTER);
      caixa.add(pnlBtn,BorderLayout.SOUTH);
      caixa.add(pnlLbl,BorderLayout.NORTH);
      
      btnExcluir.addActionListener(this);
      btnInserir.addActionListener(this);
      btnAlterar.addActionListener(this);
      btnResposta.addActionListener(this);
      btnVoltar.addActionListener(this);
      
      setSize(LARGURA_TELA, ALTURA_TELA);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      
   }
   public void actionPerformed(ActionEvent e){
      conn = this.conn;
      if(e.getSource()==btnInserir){
         dispose();
         InserirProjeto i = new InserirProjeto(conn);
      }
      else if(e.getSource()==btnResposta){
        int id = Integer.parseInt(tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),0)+"");
         Projeto projeto = new Projeto(id);
         projeto.select(this.conn);
         if(projeto.getResposta() != 0){
            JOptionPane.showMessageDialog(this, "Você não pode alterar a resposta de um projeto já avaliado");
         }else{
            RespostaProjeto r = new RespostaProjeto(this.conn, projeto);
         }
      }
      else if(e.getSource()==btnVoltar){
         dispose();
         AgenciaApp a = new AgenciaApp(conn);
      }
      else if(e.getSource()==btnAlterar){
         try{
            Alterar a = new Alterar(conn,tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),0)+"");
            dispose();
         }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Você deve selecionar um item da tabela para alterar");
         }
      }
      else if(e.getSource()==btnExcluir){
         int i = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o projeto "+tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),1)+"?", "ATENÃ‡ÃƒO",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null); 
         if (i == JOptionPane.YES_OPTION) { 
            Projeto projeto = new Projeto(Integer.parseInt(tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),0)+""));
            projeto.remove(conn);
            instanciaJTableEScrollPane(conn); 
            pnlCentro.add(rolagem);
            caixa.add(pnlCentro, BorderLayout.CENTER);
            validate();
            repaint();
            dispose();
            new TelaProjetos(this.conn);
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
      rolagem.setPreferredSize(new Dimension(LARGURA_SCROLL_PANE, ALTURA_SCROLL_PANE));
   }   
}