import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.sql.Connection;

public class TelaAvaliadores extends JFrame implements ActionListener, ListSelectionListener{

   final int LARGURA_TELA = 800;
   final int ALTURA_TELA = 400;
   final int LARGURA_SCROLL_PANE = LARGURA_TELA - 100;
   final int ALTURA_SCROLL_PANE = ALTURA_TELA - 110;

   private JButton btnInserir;
   private JButton btnAlterar;
   private JButton btnExcluir;
   private JButton btnVoltar;
   
   private JPanel pnlCentro;
   private JPanel pnlBtn;
   private JPanel pnlLbl;
   
   private JScrollPane rolagem;
   
   private Container caixa;
   
   private JLabel lblAvaliador;
   
   private Object[][] avaliadores;
   private String[] colunas = {"Nome", "Sexo", "RG", "CPF", "Data de Nascimento", "Grau de Conhecimento", "Institui��o", "Area de Pesquisa"};
   
   private JTable tabelaAvaliadores;
   
   private Connection conn;

   public TelaAvaliadores(Connection conn){
      
      super("Lista de Avaliadores");
      
      this.conn = conn;
      
      instanciaJTableEScrollPane(conn);
      
      btnInserir = new JButton("Inserir");
      btnAlterar = new JButton("Alterar");
      btnExcluir = new JButton("Excluir");
      btnVoltar = new JButton("Voltar");
      
      lblAvaliador = new JLabel("Avaliadores");
      
      caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      pnlCentro = new JPanel(new FlowLayout());
      pnlBtn = new JPanel(new FlowLayout());
      pnlLbl = new JPanel(new FlowLayout());
      
      pnlLbl.add(lblAvaliador);
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
      if(e.getSource() == btnVoltar){
         dispose();
         AgenciaApp a = new AgenciaApp(conn);
      }
   }
   
   public void valueChanged(ListSelectionEvent e){
      /* colocar dentro deste if porque o evento e disparado duas vezes e assim 
         filtra-se somente um deles */
      if(e.getValueIsAdjusting()){
         String resultado = 
            tabelaAvaliadores.getColumnName(0)+": "+
            tabelaAvaliadores.getValueAt(tabelaAvaliadores.getSelectedRow(),0)+
            "\n"+tabelaAvaliadores.getColumnName(1)+": "+
            tabelaAvaliadores.getValueAt(tabelaAvaliadores.getSelectedRow(),1)+
            "\n"+tabelaAvaliadores.getColumnName(2)+": "+
            tabelaAvaliadores.getValueAt(tabelaAvaliadores.getSelectedRow(),2)+
            "\n"+tabelaAvaliadores.getColumnName(3)+": "+
            tabelaAvaliadores.getValueAt(tabelaAvaliadores.getSelectedRow(),3)+
            "\n"+tabelaAvaliadores.getColumnName(4)+": "+
            tabelaAvaliadores.getValueAt(tabelaAvaliadores.getSelectedRow(),4);
            
         JOptionPane.showMessageDialog(this, resultado);
      }
   }
   
   public String[][] carregaDados(Connection conn){
      Avaliador a = new Avaliador();
      ArrayList<Avaliador> lista = a.getAll(conn);
      DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      
      String[][] saida = new String[lista.size()][colunas.length];
      Avaliador avaliador;
      for(int i = 0; i < lista.size(); i++){
         avaliador = lista.get(i);
         saida[i][0] = avaliador.getNome()+"";
         saida[i][1] = avaliador.getSexo()+"";
         saida[i][2] = avaliador.getRg();
         saida[i][3] = avaliador.getCpf();
         saida[i][4] = avaliador.getDataNasc()+"";
         saida[i][5] = avaliador.getGrau();
         saida[i][6] = avaliador.getInstituicao().getNome();
         saida[i][7] = avaliador.getAreaPesquisa().getNome(); 
      }
      
      return saida;
   }
   
   public void instanciaJTableEScrollPane(Connection conn){
      avaliadores = carregaDados(this.conn);
      tabelaAvaliadores = new JTable(avaliadores, colunas);
      tabelaAvaliadores.getSelectionModel().addListSelectionListener(this);
      //coloca a JTable em um scroll pane para ter a barra de rolagem
      rolagem = new JScrollPane(tabelaAvaliadores);
      rolagem.setPreferredSize(new Dimension(LARGURA_SCROLL_PANE, ALTURA_SCROLL_PANE));
   }

}