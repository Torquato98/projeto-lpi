import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Inserir extends JFrame implements ActionListener  {
   private JLabel lblTitulo,lblDuracao,lblOrcamento,lblCb,lblCb2,lblCbI,lblCbP, lblCbAvaliador;
   private JTextField txtTitulo, txtDuracao, txtOrcamento;
   private JButton btnInserir,btnVoltar;
   private JComboBox<String> cbGa,cbA,cbI, cbAvaliador;
   private JComboBox<String> cbP;
   private Connection conn;
   private String guarda[],guarda2[],guarda3[];
   private Date dt = new Date();
   private java.sql.Date data = new java.sql.Date (dt.getTime());;

   
   public Inserir (Connection conn){
      super("Inserir Projetos");
      this.conn = conn;
      
      lblTitulo = new JLabel("Titulo");
      lblDuracao = new JLabel("Duração");
      lblOrcamento = new JLabel("Orçamento");
      lblCb = new JLabel("Grande Area de Conhecimento");
      lblCb2 = new JLabel("Area de Conhecimento");
      lblCbI = new JLabel("Instituição");
      lblCbP = new JLabel("IDPesquisador");
      lblCbAvaliador = new JLabel("Avaliador");
      
      txtTitulo = new JTextField(10);
      txtDuracao = new JTextField(10);
      txtOrcamento = new JTextField(10);
      
      btnInserir = new JButton("Salvar");
      btnVoltar = new JButton("Voltar");
             
      cbGa = new JComboBox<String>();
      cbA = new JComboBox<String>();
      cbI = new JComboBox<String>();
      cbP = new JComboBox<String>();
      cbAvaliador = new JComboBox<String>();
      cbGa.addItem("Escolha");
      cbA.addItem("Escolha");
      cbI.addItem("Escolha");
      cbP.addItem("Escolha");
      cbAvaliador.addItem("Escolha");
      
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
      pnlCombo3.add(lblCbAvaliador);
      pnlCombo3.add(cbAvaliador);
      
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
      
      setSize(600,250);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
   
   public void actionPerformed (ActionEvent e){
      //Pegando dados do combobox
      if(e.getSource()==btnVoltar){
         dispose();
         ConexaoBD bd = new ConexaoBD();
         try{
            Connection conn = bd.conectar();
            new TelaProjetos(conn);
         } 
         catch (SQLException x){
            x.printStackTrace();
         }
      }
      else if (e.getSource()==btnInserir){
         //Pegando dados do combobox
         int instituicao = Integer.parseInt(String.valueOf(cbI.getSelectedItem()).split(" - ")[0]);
         int area_conhecimento = Integer.parseInt(String.valueOf(cbA.getSelectedItem()).split(" - ")[0]);
         int pesquisador = Integer.parseInt(String.valueOf(cbP.getSelectedItem()).split(" - ")[0]);
         int avaliador = Integer.parseInt(String.valueOf(cbAvaliador.getSelectedItem()).split(" - ")[0]);
      
         Projeto projeto = new Projeto();
         projeto.setTitulo(txtTitulo.getText());
         projeto.setDuracao(txtDuracao.getText());
         projeto.setOrcamento(Double.parseDouble(txtOrcamento.getText()));
         projeto.setInstituicao(new Instituicao(instituicao));
         projeto.setAvaliador(new Avaliador(avaliador));
         projeto.setAreaDeConhecimento(new AreaDeConhecimento(area_conhecimento));
         projeto.setPesquisador(new Pesquisador(pesquisador));
         projeto.insert(this.conn);
         
         JOptionPane.showMessageDialog(null, "Projeto cadastrado com sucesso");
      }
      else if(e.getSource() == cbGa){
         String dados[] = String.valueOf(cbGa.getSelectedItem()).split(" - ");
         if(!dados[0].equalsIgnoreCase("Escolha")){
            cbA.removeAllItems();
            cbA.addItem("--Escolha--");
            listarArea(Integer.parseInt(dados[0]));
         }
      }
      else if(e.getSource()==cbI){
         String dados[] = String.valueOf(cbI.getSelectedItem()).split(" - ");
         if(!dados[0].equalsIgnoreCase("Escolha")){
            cbP.removeAllItems();
            cbP.addItem("Escolha");
            cbAvaliador.removeAllItems();
            cbAvaliador.addItem("Escolha");
            listarPesquisador(Integer.parseInt(dados[0]));
            listarAvaliador(Integer.parseInt(dados[0]));
         }
      }
   }
   
   public void listarGrandeArea(){
      GrandeAreaDeConhecimento grande = new GrandeAreaDeConhecimento();
      ArrayList<GrandeAreaDeConhecimento> listAreas = grande.getAll(this.conn);
      for(GrandeAreaDeConhecimento item: listAreas){
         cbGa.addItem(item.getId() + " - " + item.getNome());
      }
   }
   
   public void listarInstituicao(){
      Instituicao instituicao = new Instituicao();
      ArrayList<Instituicao> listInstituicao = instituicao.getAll(this.conn);
      for(Instituicao item: listInstituicao){
         cbI.addItem(item.getId() + " - " + item.getNome());
      }
   }
   
   public void listarArea(int id){
      AreaDeConhecimento areaDeConhecimento = new AreaDeConhecimento(new GrandeAreaDeConhecimento(id));
      areaDeConhecimento.getGrandeAreaDeConhecimento().select(this.conn);
      ArrayList<AreaDeConhecimento> listAreas = areaDeConhecimento.getAll(this.conn);
      for(AreaDeConhecimento item: listAreas){
         cbA.addItem(item.getId() + " - " + item.getNome());
      }
   }
   
   public void listarPesquisador(int id){
      Pesquisador pesquisador = new Pesquisador(new Instituicao(id));
      pesquisador.getInstituicao().select(this.conn);
      ArrayList<Pesquisador> listPesquisador = pesquisador.getAll(this.conn);
      for(Pesquisador item: listPesquisador){
         cbP.addItem(item.getId() + " - " + item.getNome());
      }
   }
   
   public void listarAvaliador(int id){
      Avaliador avaliador = new Avaliador(new Instituicao(id));
      avaliador.getInstituicao().select(this.conn);
      ArrayList<Avaliador> listAvaliador = avaliador.getAll(this.conn);
      for(Avaliador item: listAvaliador){
         cbAvaliador.addItem(item.getId() + " - " + item.getNome());
      }
   }
      
}