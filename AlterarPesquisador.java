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

public class AlterarPesquisador extends JFrame implements ActionListener{

   private JLabel lblRg;
   private JLabel lblCpf;
   private JLabel lblNome;
   private JLabel lblSexo;
   private JLabel lblDataNasc;
   private JLabel lblInstituicao;
   private JLabel lblGrauConhecimento;
   
   private JTextField txtRg;
   private JTextField txtCpf;
   private JTextField txtNome;
   private JTextField txtDataNasc;
   
   private JComboBox cmbSexo;
   private JComboBox cmbGrauConhecimento;
   private JComboBox cmbInstituicao;
   
   private JButton btnSalvar;
   private JButton btnVoltar;
   
   private JPanel pnlNorte;
   private JPanel pnlCentroNorte1;
   private JPanel pnlCentroNorte2;
   private JPanel pnlCentro;
   private JPanel pnlBtn;
   
   private Pesquisador pesquisador;
   
   private Container caixa;

   private Connection conn;
   
   public AlterarPesquisador(Connection conn, int pesquisador_id){
      super("Alterar de Pesquisador");
      
      this.conn = conn;
      this.pesquisador = new Pesquisador(pesquisador_id);
      this.pesquisador.select(this.conn);
      
      lblNome = new JLabel("Nome: ");
      lblRg = new JLabel("RG: ");
      lblCpf = new JLabel("CPF: ");
      lblSexo = new JLabel("Sexo: ");
      lblDataNasc = new JLabel("Data de Nascimento: ");
      lblInstituicao = new JLabel("Instituição: ");
      lblGrauConhecimento = new JLabel("Grau de Conhecimento: ");
      
      txtNome = new JTextField(15);
      txtRg = new JTextField(15);
      txtCpf = new JTextField(15);
      txtDataNasc = new JTextField(15);
      
      btnSalvar = new JButton("Salvar");
      btnVoltar = new JButton("Voltar");
      
      cmbGrauConhecimento = new JComboBox<String>();
      cmbInstituicao = new JComboBox<String>();
      cmbSexo = new JComboBox<String>();
      
      cmbGrauConhecimento.addItem("Escolha");
      cmbInstituicao.addItem("Escolha");
      
      cmbSexo.addItem("Escolha");
      cmbSexo.addItem("M");
      cmbSexo.addItem("F");
      
      listarInstituicao();
      listarGrauDeConhecimento();
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
      
      pnlCentro.add(lblInstituicao);
      pnlCentro.add(cmbInstituicao);
      pnlCentro.add(lblGrauConhecimento);
      pnlCentro.add(cmbGrauConhecimento);
      
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
      setSize(650,200);
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
         int grau_conhecimento_id = Integer.parseInt(String.valueOf(cmbGrauConhecimento.getSelectedItem()).split(" - ")[0]);
         int instituicao_id = Integer.parseInt(String.valueOf(cmbInstituicao.getSelectedItem()).split(" - ")[0]);
      
         pesquisador.setNome(txtNome.getText());
         pesquisador.setRg(txtRg.getText());
         pesquisador.setCpf(txtCpf.getText());
         pesquisador.setSexo(String.valueOf(cmbSexo.getSelectedItem()).charAt(0));
         pesquisador.setDataNasc(Date.valueOf(formatData(txtDataNasc.getText())));
         pesquisador.setPesquisadorGrauConhecimento(new PesquisadorGrauConhecimento());
         pesquisador.getPesquisadorGrauConhecimento().setGrauConhecimento(new GrauConhecimento(grau_conhecimento_id));
         pesquisador.setInstituicao(new Instituicao(instituicao_id));
         boolean response = pesquisador.update(this.conn);
         if(response){
            JOptionPane.showMessageDialog(this, "Pesquisador alterado com sucesso");
         }else{
            JOptionPane.showMessageDialog(this, "Falha ao alterar o pesquisador");
         }
      }else if(e.getSource() == btnVoltar){
         dispose();
         new TelaPesquisadores(this.conn);
      }
   }
   
   public void listarDados(){
      txtNome.setText(pesquisador.getNome());
      txtRg.setText(pesquisador.getRg());
      txtCpf.setText(pesquisador.getCpf());
      txtDataNasc.setText(formatDataBr(pesquisador.getDataNasc() + ""));

      cmbSexo.setSelectedItem(pesquisador.getSexo()+"");
      cmbInstituicao.setSelectedItem(pesquisador.getInstituicao().getId() + " - " + pesquisador.getInstituicao().getNome());
      cmbGrauConhecimento.setSelectedItem(pesquisador.getPesquisadorGrauConhecimento().getGrauConhecimento().getId() + " - " + pesquisador.getPesquisadorGrauConhecimento().getGrauConhecimento().getNome());
   }
   
   public void listarInstituicao(){
      Instituicao i = new Instituicao();
      ArrayList<Instituicao> list = i.getAll(this.conn);
      for(Instituicao item: list){
         cmbInstituicao.addItem(item.getId() + " - " + item.getNome());
      }
   }
   
   public void listarGrauDeConhecimento(){
      GrauConhecimento g = new GrauConhecimento();
      ArrayList<GrauConhecimento> list = g.getAll(this.conn);
      for(GrauConhecimento item: list){
         cmbGrauConhecimento.addItem(item.getId() + " - " + item.getNome());
      }
   }

}