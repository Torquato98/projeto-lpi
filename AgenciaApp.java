import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AgenciaApp extends JFrame implements ActionListener{

   final int LARGURA_TELA = 600;
   final int ALTURA_TELA = 300;

   private JMenuBar menuBar;
   
   private JMenu menuProjetos;
   private JMenu menuPesquisadores;
   private JMenu menuAvaliadores;
   
   private JMenuItem menuProjetosInserir;
   private JMenuItem menuProjetosConsultar;
   
   private JMenuItem menuPesquisadoresInserir;
   private JMenuItem menuPesquisadoresConsultar;
   
   private JMenuItem menuAvaliadoresInserir;
   private JMenuItem menuAvaliadoresConsultar;
   
   private Container caixa;
   
   private JLabel lblWelcome;
   
   private Connection conn;
   
   public AgenciaApp(Connection conn){
      super("Agência");
      
      this.conn = conn;
      
      lblWelcome = new JLabel("Bem vindo", SwingConstants.CENTER);
      
      menuBar = new JMenuBar();
      menuProjetos = new JMenu("Projetos");
      menuPesquisadores = new JMenu("Pesquisadores");
      menuAvaliadores = new JMenu("Avaliadores");
      
      menuProjetosInserir = new JMenuItem("Inserir");
      menuProjetosConsultar = new JMenuItem("Consultar");
      
      menuPesquisadoresInserir = new JMenuItem("Inserir");
      menuPesquisadoresConsultar = new JMenuItem("Consultar");
      
      menuAvaliadoresInserir = new JMenuItem("Inserir");
      menuAvaliadoresConsultar = new JMenuItem("Consultar");
      
      setJMenuBar(menuBar);
      menuBar.add(menuProjetos);
      menuBar.add(menuPesquisadores);
      menuBar.add(menuAvaliadores);
      
      menuProjetos.add(menuProjetosInserir);
      menuProjetos.add(menuProjetosConsultar);
      
      menuPesquisadores.add(menuPesquisadoresInserir);
      menuPesquisadores.add(menuPesquisadoresConsultar);
      
      menuAvaliadores.add(menuAvaliadoresInserir);
      menuAvaliadores.add(menuAvaliadoresConsultar);
      
      menuProjetosInserir.addActionListener(this);
      menuProjetosConsultar.addActionListener(this);
      
      menuPesquisadoresInserir.addActionListener(this);
      menuPesquisadoresConsultar.addActionListener(this);
      
      menuAvaliadoresInserir.addActionListener(this);
      menuAvaliadoresConsultar.addActionListener(this);
      
      caixa = getContentPane();
      caixa.setLayout(new FlowLayout());
      caixa.add(lblWelcome);
      
      setSize(LARGURA_TELA, ALTURA_TELA);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      
   }
   
   public void actionPerformed(ActionEvent e){
      if(e.getSource() == menuProjetosInserir){
         dispose();
         new InserirProjeto(conn);
      }else if(e.getSource() == menuProjetosConsultar){
         dispose();
         new TelaProjetos(conn);
      }else if(e.getSource() == menuPesquisadoresInserir){
         dispose();
         new InserirPesquisador(conn);
      }else if(e.getSource() == menuPesquisadoresConsultar){
         dispose();
         new TelaPesquisadores(conn);
      }else if(e.getSource() == menuAvaliadoresInserir){
         dispose();
         new InserirAvaliador(conn);
      }else if(e.getSource() == menuAvaliadoresConsultar){
         dispose();
         new TelaAvaliadores(conn);
      }
   }
   
}