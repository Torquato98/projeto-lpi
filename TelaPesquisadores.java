import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class TelaPesquisadores extends JFrame implements ActionListener, ListSelectionListener{

   final int LARGURA_TELA = 800;
   final int ALTURA_TELA = 400;
   final int LARGURA_SCROLL_PANE = LARGURA_TELA - 100;
   final int ALTURA_SCROLL_PANE = ALTURA_TELA - 110;

   private Container caixa;
   private JTable tabelaPesquisadores;
   private JPanel pnlCentro,pnlBtn,pnlLbl;
   private JScrollPane rolagem;
   private JButton btnExcluir,btnAlterar,btnInserir, btnVoltar;
   private JLabel lblPesquisador;
   private Connection conn;
   
   private Object[][] pesquisadores;
   private String[] colunas = {"ID", "Nome", "Sexo", "RG", "CPF", "Data de Nascimento", "Grau de Conhecimento", "Instituição"};
   
   public TelaPesquisadores(Connection conn){
      super("Lista de Pesquisadores");
      
      this.conn = conn;
      
      instanciaJTableEScrollPane(conn);
      btnExcluir = new JButton ("Excluir");
      btnInserir = new JButton("Inserir");
      btnAlterar = new JButton("Alterar");
      btnVoltar = new JButton("Voltar");
      
      lblPesquisador = new JLabel("Pesquisadores");
      
      caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      pnlCentro = new JPanel(new FlowLayout());
      pnlBtn = new JPanel(new FlowLayout());
      pnlLbl = new JPanel(new FlowLayout());
      
      pnlCentro.add(rolagem);
      caixa.add(pnlCentro, BorderLayout.CENTER);
      caixa.add(pnlBtn,BorderLayout.SOUTH);
      caixa.add(pnlLbl,BorderLayout.NORTH);
      
      pnlLbl.add(lblPesquisador);
      pnlBtn.add(btnInserir);
      pnlBtn.add(btnAlterar);
      pnlBtn.add(btnExcluir);
      pnlBtn.add(btnVoltar);
      
      pnlCentro.add(rolagem);
      caixa.add(pnlCentro, BorderLayout.CENTER);
      caixa.add(pnlBtn,BorderLayout.SOUTH);
      caixa.add(pnlLbl,BorderLayout.NORTH);
      
      btnExcluir.addActionListener(this);
      btnInserir.addActionListener(this);
      btnAlterar.addActionListener(this);
      btnVoltar.addActionListener(this);
      
      setSize(LARGURA_TELA, ALTURA_TELA);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
   
   public void actionPerformed(ActionEvent e){
      if(e.getSource() == btnInserir){
         dispose();
         new InserirPesquisador(this.conn);
      }else if(e.getSource() == btnAlterar){
         try{
            AlterarPesquisador a = new AlterarPesquisador(conn,Integer.parseInt(tabelaPesquisadores.getValueAt(tabelaPesquisadores.getSelectedRow(),0)+""));
            dispose();
         }catch(Exception ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Você deve selecionar um item da tabela para alterar");
         }
      }
      else if(e.getSource()==btnExcluir){
         try{
            int i = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o pesquisador "+tabelaPesquisadores.getValueAt(tabelaPesquisadores.getSelectedRow(),1)+"?", "ATENÇÃO",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null); 
            if (i == JOptionPane.YES_OPTION) { 
               Pesquisador pesquisador = new Pesquisador(Integer.parseInt(tabelaPesquisadores.getValueAt(tabelaPesquisadores.getSelectedRow(),0)+""));
               pesquisador.remove(conn);
               instanciaJTableEScrollPane(conn); 
               pnlCentro.add(rolagem);
               caixa.add(pnlCentro, BorderLayout.CENTER);
               validate();
               repaint();
               dispose();
               new TelaPesquisadores(this.conn);
            } 
         }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Você deve selecionar um pesquisador para excluir");
         }
      }
      else if(e.getSource() == btnVoltar){
         dispose();
         AgenciaApp a = new AgenciaApp(conn);
      }
   }
   
   public void valueChanged(ListSelectionEvent e){
      /* colocar dentro deste if porque o evento e disparado duas vezes e assim 
         filtra-se somente um deles */
      if(e.getValueIsAdjusting()){
         String resultado = 
            tabelaPesquisadores.getColumnName(0)+": "+
            tabelaPesquisadores.getValueAt(tabelaPesquisadores.getSelectedRow(),0)+
            "\n"+tabelaPesquisadores.getColumnName(1)+": "+
            tabelaPesquisadores.getValueAt(tabelaPesquisadores.getSelectedRow(),1)+
            "\n"+tabelaPesquisadores.getColumnName(2)+": "+
            tabelaPesquisadores.getValueAt(tabelaPesquisadores.getSelectedRow(),2)+
            "\n"+tabelaPesquisadores.getColumnName(3)+": "+
            tabelaPesquisadores.getValueAt(tabelaPesquisadores.getSelectedRow(),3)+
            "\n"+tabelaPesquisadores.getColumnName(4)+": "+
            tabelaPesquisadores.getValueAt(tabelaPesquisadores.getSelectedRow(),4);
            
         JOptionPane.showMessageDialog(this, resultado);
      }
   }
   
   public String[][] carregaDados(Connection conn){
      Pesquisador p = new Pesquisador();
      ArrayList<Pesquisador> lista = p.getAll(conn);
      DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      
      String[][] saida = new String[lista.size()][colunas.length];
      Pesquisador pesquisador;
      for(int i = 0; i < lista.size(); i++){
         pesquisador = lista.get(i);
         saida[i][0] = pesquisador.getId()+"";
         saida[i][1] = pesquisador.getNome()+"";
         saida[i][2] = pesquisador.getSexo()+"";
         saida[i][3] = pesquisador.getRg();
         saida[i][4] = pesquisador.getCpf();
         saida[i][5] = pesquisador.getDataNasc()+"";
         saida[i][6] = pesquisador.getPesquisadorGrauConhecimento().getGrauConhecimento().getNome();
         saida[i][7] = pesquisador.getInstituicao().getNome();
      }
      
      return saida;
   }
   
   public void instanciaJTableEScrollPane(Connection conn){
      pesquisadores = carregaDados(this.conn);
      tabelaPesquisadores = new JTable(pesquisadores, colunas);
      tabelaPesquisadores.getSelectionModel().addListSelectionListener(this);
      //coloca a JTable em um scroll pane para ter a barra de rolagem
      rolagem = new JScrollPane(tabelaPesquisadores);
      rolagem.setPreferredSize(new Dimension(LARGURA_SCROLL_PANE, ALTURA_SCROLL_PANE));
   }

}