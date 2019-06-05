import javax.swing.JOptionPane;

public class AgenciaApp{

    public static void main(String[] args){
        
        Agencia agencia = new Agencia();
        int resp = 0;

        do{

            resp = Integer.parseInt(JOptionPane.showInputDialog("1 - Cadastrar Projeto\n2 - Buscar projeto\n3 - Remover Projeto\n4 - Avaliar projeto\n5 - Listar Projetos\n6 - Sair"));

            if(resp == 1){
                AreaDeConhecimento areaDeConhecimento = new AreaDeConhecimento(
                    Integer.parseInt(JOptionPane.showInputDialog("Digite o codigo da area de conhecimento")),
                    JOptionPane.showInputDialog("Digite o nome da area de conhecimento")
                );
                GrandeAreaDeConhecimento grandeAreaDeConhecimento = new GrandeAreaDeConhecimento(
                    Integer.parseInt(JOptionPane.showInputDialog("Digite o codigo da grande area de conhecimento")),
                    JOptionPane.showInputDialog("Digite o nome da grande area de conhecimento"),
                    areaDeConhecimento
                );
                Projeto projeto = new Projeto(
                    Integer.parseInt(JOptionPane.showInputDialog("Digite o codigo do projeto")),
                    JOptionPane.showInputDialog("Digite o titulo do projeto"),
                    JOptionPane.showInputDialog("Digite a duracacao do projeto"),
                    Double.parseDouble(JOptionPane.showInputDialog("Digite o orcamento do projeto")),
                    grandeAreaDeConhecimento
                );
                agencia.inserir(projeto);
                JOptionPane.showMessageDialog(null, "Projeto cadastrado com sucesso");
            }else if(resp == 2){
                String projeto = agencia.buscarProjeto(
                    Integer.parseInt(JOptionPane.showInputDialog("Digite o código do projeto"))
                );
                if(projeto != null){
                    JOptionPane.showMessageDialog(null, projeto);
                }else{
                    JOptionPane.showMessageDialog(null, "Projeto nao encontrado");
                }
            }else if(resp == 3){
                boolean success = agencia.remover(
                    Integer.parseInt(JOptionPane.showInputDialog("Digite o código do projeto"))
                );
                if(success){
                    JOptionPane.showMessageDialog(null, "Projeto apagado com sucesso");
                }else{
                    JOptionPane.showMessageDialog(null, "Projeto nao encontrado");
                }
            }else if(resp == 4){
                boolean success = agencia.alterarProjeto(
                    Integer.parseInt(JOptionPane.showInputDialog("Digite o código do projeto")),
                    JOptionPane.showInputDialog("Digite a avaliacao do projeto"),
                    JOptionPane.showInputDialog("Digite a data de avaliacao do projeto")
                );
                if(success){
                    JOptionPane.showMessageDialog(null, "Projeto avaliado com sucesso");
                }else{
                    JOptionPane.showMessageDialog(null, "Projeto nao encontrado");
                }
            }else if(resp == 5){
                JOptionPane.showMessageDialog(null, agencia.listarProjetos());
            }
        }while(resp != 6);

    }

}