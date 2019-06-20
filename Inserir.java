import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Inserir extends JFrame implements ActionListener  {
   private JLabel lblTitulo,lblDuracao,lblOrcamento,lblCb,lblCb2,lblCbI,lblCbP;
   private JTextField txtTitulo, txtDuracao, txtOrcamento;
   private JButton btnInserir,btnVoltar;
   private JComboBox<String> cbGa,cbA,cbI;
   private JComboBox<String> cbP;
   private Connection conn;
   private String guarda[],guarda2[],guarda3[];
   private Date dt = new Date();
   private java.sql.Date data = new java.sql.Date (dt.getTime());;

   
   public Inserir (Connection conn){
      super("Inserir Projetos");
      this.conn = conn;
      
      lblTitulo = new JLabel ("Titulo");
      lblDuracao = new JLabel ("Duração");
      lblOrcamento = new JLabel ("Orçamento");
      lblCb = new JLabel ("G. Área de Conhecimento");
      lblCb2 = new JLabel ("Área de Conhecimento");
      lblCbI = new JLabel ("Instituição");
      lblCbP = new JLabel ("IDPesquisador");
      
      txtTitulo = new JTextField(10);
      txtDuracao = new JTextField(10);
      txtOrcamento = new JTextField(10);
      
      btnInserir = new JButton ("Salvar");
      btnVoltar = new JButton ("Voltar");
             
      cbGa = new JComboBox<String>();
      cbA = new JComboBox<String>();
      cbI = new JComboBox<String>();
      cbP = new JComboBox<String>();
      cbGa.addItem("Escolha");
      cbA.addItem("Escolha");
      cbI.addItem("Escolha");
      cbP.addItem("Escolha");
      listarGrandeArea();
      listarInstituicao();
      
      Container cx = getContentPane();
      cx.setLayout(new BorderLayout());
      JPanel pnlBtn = new JPanel(new FlowLayout());
      JPanel pnlResto = new JPanel(new FlowLayout());
      JPanel pnlCentro = new JPanel(new GridLayout(4,1));
      JPanel pnlCombo1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JPanel pnlCombo2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JPanel pnlCombo3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JPanel pnlCombo4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
      
      pnlBtn.add(btnInserir);
      pnlBtn.add(btnVoltar);
      pnlResto.add(lblTitulo);
      pnlResto.add(txtTitulo);
      pnlResto.add(lblDuracao);
      pnlResto.add(txtDuracao);
      pnlResto.add(lblOrcamento);
      pnlResto.add(txtOrcamento);
      pnlCombo1.add(lblCb);
      pnlCombo1.add(cbGa);
      pnlCombo2.add(lblCb2);
      pnlCombo2.add(cbA);
      pnlCombo3.add(lblCbP);
      pnlCombo3.add(cbP);
      pnlCombo4.add(lblCbI);
      pnlCombo4.add(cbI); 
      pnlCentro.add(pnlCombo1);
      pnlCentro.add(pnlCombo2);
      pnlCentro.add(pnlCombo4);
      pnlCentro.add(pnlCombo3);
      cx.add(pnlResto,BorderLayout.NORTH);
      cx.add(pnlCentro,BorderLayout.CENTER);
      cx.add(pnlBtn,BorderLayout.SOUTH);
      
      btnVoltar.addActionListener(this);
      btnInserir.addActionListener(this);
      cbGa.addActionListener(this);
      cbI.addActionListener(this);
      
      setSize(520,250);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
   
   public void actionPerformed (ActionEvent e){
      //Pegando dados do combobox
      int avaliador = Integer.parseInt(String.valueOf(cbI.getSelectedItem()).split(" - ")[0]);
      int instituicao = Integer.parseInt(String.valueOf(cbP.getSelectedItem()).split(" - ")[0]);
      int area_conhecimento = Integer.parseInt(String.valueOf(cbA.getSelectedItem()).split(" - ")[0]);
   
      if(e.getSource()==btnVoltar){
         dispose();
         ConexaoBD bd = new ConexaoBD();
         try{
            Connection conn = bd.conectar();
            new Tela(conn);
         } 
         catch (SQLException x){
            x.printStackTrace();
         }
      }
      else if (e.getSource()==btnInserir){
         Projeto projeto = new Projeto();
         projeto.setTitulo(txtTitulo.getText());
         projeto.setDuracao(txtDuracao.getText());
         projeto.setOrcamento(Double.parseDouble(txtOrcamento.getText()));
         projeto.setInstituicao(new Instituicao(instituicao));
         projeto.setAvaliador(new Avaliador(avaliador));
         projeto.setAreaDeConhecimento(new AreaDeConhecimento(area_conhecimento));
         projeto.insert(this.conn);
         
         JOptionPane.showMessageDialog(null, "Projeto cadastrado com sucesso");
      }
      else if(e.getSource() == cbGa){
         String dados[] = String.valueOf(cbGa.getSelectedItem()).split(" - ");
         if(!dados[0].equalsIgnoreCase("Escolha")){
            cbA.removeAllItems();
            cbA.addItem("--Escolha--");
            listarArea(dados[0]);
         }
      }
      else if(e.getSource()==cbI){
      
         String dadis[] = String.valueOf(cbI.getSelectedItem()).split(" - ");
         if(!dadis[0].equalsIgnoreCase("Escolha")){
            cbP.removeAllItems();
            cbP.addItem("Escolha");
            listarPesquisador(dadis[0]);
         }
      }
      
      
   }
      
     
    
  
   
   public void listarArea(String id){
      String query = "SELECT DISTINCT a.nome,a.id FROM areas_conhecimento a,grandes_areas_conhecimento b WHERE a.grandes_areas_conhecimento_id = ? ";
      
      try (PreparedStatement stm = conn.prepareStatement(query);) {
         stm.setInt(1, Integer.parseInt(id));
         try (ResultSet rs = stm.executeQuery();) {
               
            while(rs.next()){
               cbA.addItem(String.valueOf(rs.getInt("id"))+ " - " +(rs.getString("nome")));
            }
         
         }catch(Exception e){
            e.printStackTrace();
         }
      }catch (SQLException e1) {
         System.out.print(e1.getStackTrace()+e1.getMessage());
      
      }
   
   }
   public void listarGrandeArea(){
      String query = "SELECT DISTINCT * FROM grandes_areas_conhecimento";
      
      try (PreparedStatement stm = conn.prepareStatement(query);){
            
         ResultSet rs = stm.executeQuery();
               
         while(rs.next()){
               
            cbGa.addItem(String.valueOf(rs.getInt("id"))+ " - " +(rs.getString("nome")));
         }
         
      
      }catch(Exception e){
         e.printStackTrace(); e.getMessage();
      }
         
   }
   
   public void listarInstituicao(){
      String query = "SELECT DISTINCT * FROM instituicao";
      
      try (PreparedStatement stm = conn.prepareStatement(query);){
            
         ResultSet rs = stm.executeQuery();
               
         while(rs.next()){
            cbI.addItem(String.valueOf(rs.getInt("id"))+ " - " +(rs.getString("nome")));
         }
         
      
      }catch(Exception e){
         e.printStackTrace(); e.getMessage();
      }
         
   }
   public void listarPesquisador(String id){
      String query = "SELECT id,nome FROM pesquisadores WHERE instituicao_id = ? ";  
      try (PreparedStatement stm = conn.prepareStatement(query);) {
         stm.setInt(1, Integer.parseInt(id));
         try (ResultSet rs = stm.executeQuery();) {
               
            while(rs.next()){
               cbP.addItem(String.valueOf(rs.getInt("id"))+ " - " +(rs.getString("nome")));
            }
         
         }catch(Exception e){
            e.printStackTrace();
         }
      }catch (SQLException e1) {
         System.out.print(e1.getStackTrace()+e1.getMessage());
      
      }
         
   }

}