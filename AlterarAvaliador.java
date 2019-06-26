import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.util.ArrayList;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Date;

public class AlterarAvaliador extends JFrame implements ActionListener{

   private JLabel lblRg;
   private JLabel lblCpf;
   private JLabel lblNome;
   private JLabel lblSexo;
   private JLabel lblDataNasc;
   private JLabel lblInstituicao;
   private JLabel lblAreaPesquisa;
   private JLabel lblGrau;
   
   private JTextField txtRg;
   private JTextField txtCpf;
   private JTextField txtNome;
   private JTextField txtDataNasc;
   
   private JComboBox cmbSexo;
   private JComboBox cmbAreaPesquisa;
   private JComboBox cmbInstituicao;
   private JComboBox cmbGrau;
   
   private JButton btnSalvar;
   private JButton btnVoltar;
   
   private JPanel pnlNorte;
   private JPanel pnlCentroNorte1;
   private JPanel pnlCentroNorte2;
   private JPanel pnlCentro;
   private JPanel pnlBtn;
   
   private Container caixa;

   private Connection conn;
   
   private Avaliador avaliador;
   
   public AlterarAvaliador(Connection conn, int avaliador_id){
      super("Alterar de Avaliador");
      
      this.conn = conn;
      
      this.avaliador = new Avaliador(avaliador_id);
      this.avaliador.select(this.conn);
      
      lblNome = new JLabel("Nome: ");
      lblRg = new JLabel("RG: ");
      lblCpf = new JLabel("CPF: ");
      lblSexo = new JLabel("Sexo: ");
      lblDataNasc = new JLabel("Data de Nascimento: ");
      lblInstituicao = new JLabel("Instituição: ");
      lblAreaPesquisa = new JLabel("Area de Pesquisa: ");
      lblGrau = new JLabel("Grau de Conhecimento: ");
      
      txtNome = new JTextField(15);
      txtRg = new JTextField(15);
      txtCpf = new JTextField(15);
      txtDataNasc = new JTextField(15);
      
      btnSalvar = new JButton("Salvar");
      btnVoltar = new JButton("Voltar");
      
      cmbAreaPesquisa = new JComboBox<String>();
      cmbInstituicao = new JComboBox<String>();
      cmbSexo = new JComboBox<String>();
      cmbGrau = new JComboBox<String>();
      
      cmbAreaPesquisa.addItem("Escolha");
      cmbInstituicao.addItem("Escolha");
      cmbGrau.addItem("Escolha");
      cmbGrau.addItem("Mestre");
      cmbGrau.addItem("Doutor");
      
      cmbSexo.addItem("Escolha");
      cmbSexo.addItem("M");
      cmbSexo.addItem("F");
      
      listarInstituicao();
      listarAreaPesquisa();
      listarDados();
      
      caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      pnlNorte = new JPanel(new BorderLayout());
      pnlCentroNorte1 = new JPanel(new FlowLayout());
      pnlCentroNorte2 = new JPanel(new FlowLayout());
      pnlCentro = new JPanel(new FlowLayout());
      pnlBtn = new JPanel(new FlowLayout());
      
      pnlCentroNorte1.add(lblNome);
      pnlCentroNorte1.add(txtNome);
      pnlCentroNorte1.add(lblRg);
      pnlCentroNorte1.add(txtRg);
      pnlCentroNorte1.add(lblSexo);
      pnlCentroNorte1.add(cmbSexo);
      
      pnlCentroNorte2.add(lblCpf);
      pnlCentroNorte2.add(txtCpf);
      pnlCentroNorte2.add(lblDataNasc);
      pnlCentroNorte2.add(txtDataNasc);
      pnlCentroNorte2.add(lblGrau);
      pnlCentroNorte2.add(cmbGrau);
      
      pnlCentro.add(lblInstituicao);
      pnlCentro.add(cmbInstituicao);
      pnlCentro.add(lblAreaPesquisa);
      pnlCentro.add(cmbAreaPesquisa);
      
      pnlBtn.add(btnSalvar);
      pnlBtn.add(btnVoltar);
      
      pnlNorte.add(pnlCentroNorte1, BorderLayout.NORTH);
      pnlNorte.add(pnlCentroNorte2, BorderLayout.SOUTH);
      
      caixa.add(pnlNorte, BorderLayout.NORTH);
      caixa.add(pnlCentro, BorderLayout.CENTER);
      caixa.add(pnlBtn, BorderLayout.SOUTH);
      
      btnSalvar.addActionListener(this);
      btnVoltar.addActionListener(this);
      
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(800,200);
      setLocationRelativeTo(null);
   }
   
   public String formatData(String data){
      String[] info = data.split("/");
      return info[2] + "-" + info[1] + "-" + info[0];
   }
   
   public String formatDataBr(String data){
      String[] info = data.split("-");
      return info[2] + "/" + info[1] + "/" + info[0];
   }
   
   public void actionPerformed(ActionEvent e){
      if(e.getSource() == btnSalvar){
         int area_pesquisa_id = Integer.parseInt(String.valueOf(cmbAreaPesquisa.getSelectedItem()).split(" - ")[0]);
         int instituicao_id = Integer.parseInt(String.valueOf(cmbInstituicao.getSelectedItem()).split(" - ")[0]);
      
         avaliador.setNome(txtNome.getText());
         avaliador.setRg(txtRg.getText());
         avaliador.setCpf(txtCpf.getText());
         avaliador.setSexo(String.valueOf(cmbSexo.getSelectedItem()).charAt(0));
         avaliador.setDataNasc(Date.valueOf(formatData(txtDataNasc.getText())));
         avaliador.setGrau(String.valueOf(cmbGrau.getSelectedItem()));
         avaliador.setAreaPesquisa(new AreaPesquisa(area_pesquisa_id));
         avaliador.setInstituicao(new Instituicao(instituicao_id));
         avaliador.setGrau(String.valueOf(cmbGrau.getSelectedItem()));
         boolean response = avaliador.update(this.conn);
         if(response){
            JOptionPane.showMessageDialog(this, "Avaliador alterado com sucesso");
         }else{
            JOptionPane.showMessageDialog(this, "Falha ao alterado o pesquisador");
         }
      }else if(e.getSource() == btnVoltar){
         dispose();
         new TelaAvaliadores(this.conn);
      }
   }
   
   public void listarInstituicao(){
      Instituicao i = new Instituicao();
      ArrayList<Instituicao> list = i.getAll(this.conn);
      for(Instituicao item: list){
         cmbInstituicao.addItem(item.getId() + " - " + item.getNome());
      }
   }
   
   public void listarAreaPesquisa(){
      AreaPesquisa a = new AreaPesquisa();
      ArrayList<AreaPesquisa> list = a.getAll(this.conn);
      for(AreaPesquisa item: list){
         cmbAreaPesquisa.addItem(item.getId() + " - " + item.getNome());
      }
   }
   
   public void listarDados(){
      txtNome.setText(this.avaliador.getNome());
      txtRg.setText(this.avaliador.getRg());
      txtCpf.setText(this.avaliador.getCpf());
      txtDataNasc.setText(formatDataBr(this.avaliador.getDataNasc()+""));
      
      cmbSexo.setSelectedItem(this.avaliador.getSexo()+"");
      cmbAreaPesquisa.setSelectedItem(this.avaliador.getAreaPesquisa().getId() + " - " + this.avaliador.getAreaPesquisa().getNome());
      cmbInstituicao.setSelectedItem(this.avaliador.getInstituicao().getId() + " - " + this.avaliador.getInstituicao().getNome());
      cmbGrau.setSelectedItem(this.avaliador.getGrau());
   } 

}