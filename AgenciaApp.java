import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AgenciaApp extends JFrame implements ActionListener{

   final int LARGURA_TELA = 600;
   final int ALTURA_TELA = 300;

   private JMenuBar menuBar;
   
   private JMenu menuProjetos;
   private JMenu menuPesquisadores;
   
   private JMenuItem menuProjetosInserir;
   private JMenuItem menuProjetosConsultar;
   
   private JMenuItem menuPesquisadoresInserir;
   private JMenuItem menuPesquisadoresConsultar;
   
   private Connection conn;
   
   public AgenciaApp(Connection conn){
      super("Agência");
      
      this.conn = conn;
      
      menuBar = new JMenuBar();
      menuProjetos = new JMenu("Projetos");
      menuPesquisadores = new JMenu("Pesquisadores");
      
      menuProjetosInserir = new JMenuItem("Inserir");
      menuProjetosConsultar = new JMenuItem("Consultar");
      
      menuPesquisadoresInserir = new JMenuItem("Inserir");
      menuPesquisadoresConsultar = new JMenuItem("Consultar");
      
      setJMenuBar(menuBar);
      menuBar.add(menuProjetos);
      menuBar.add(menuPesquisadores);
      
      menuProjetos.add(menuProjetosInserir);
      menuProjetos.add(menuProjetosConsultar);
      
      menuPesquisadores.add(menuPesquisadoresInserir);
      menuPesquisadores.add(menuPesquisadoresConsultar);
      
      menuProjetosInserir.addActionListener(this);
      menuProjetosConsultar.addActionListener(this);
      
      menuPesquisadoresInserir.addActionListener(this);
      menuPesquisadoresConsultar.addActionListener(this);
      
      setSize(LARGURA_TELA, ALTURA_TELA);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      
   }
   
   public void actionPerformed(ActionEvent e){
      if(e.getSource() == menuProjetosInserir){
         dispose();
         new Inserir(conn);
      }else if(e.getSource() == menuProjetosConsultar){
         dispose();
         new TelaProjetos(conn);
      }
   }
   
}