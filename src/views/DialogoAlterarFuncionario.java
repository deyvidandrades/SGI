package views;

import controllers.ControladorFuncionarios;
import controllers.ControladorUI;
import controllers.entidades.Funcionario;
import controllers.interfaces.Cores;
import controllers.interfaces.Strings;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogoAlterarFuncionario extends JDialog implements Cores {
    private JPanel contentPane;
    private JTextField fieldNome;
    private JTextField fieldCPF;
    private JTextField fieldEmail;
    private JButton btnSalvar;
    private JButton btnRemoverFuncionario;
    private JButton btnCancelar;
    private JTextField fieldSenha;
    private JRadioButton radioGerente;
    private JRadioButton radioFuncionario;
    private JTextField fieldSalario;
    private JPanel panelTop;
    private JPanel panelBot;
    private JPanel panelOpcoes;
    private JPanel panelHolderNome;
    private JPanel panelHolderCPF;
    private JPanel panelHolderSalario;
    private JPanel panelHolderEmail;
    private JPanel panelHolderSenha;
    private JPanel panelHolderCargo;
    private JLabel lblNome;
    private JLabel lblCPF;
    private JLabel lblSalario;
    private JLabel lblEmail;
    private JLabel lblSenha;
    private JLabel lblCargo;

    public DialogoAlterarFuncionario(Funcionario funcionario, String senha) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);
        configurarCores();

        /*Se existir o funcionário preenchemos os campos com os dados*/
        configurarUI(funcionario, senha);

        /*Configuração dos listeners de busca*/
        configurarFiltros();

        /*Configuração dos listeners de botes*/
        configurarBotoes(funcionario);
    }

    /*Recebe a senha decodificada para alteração*/
    private void configurarUI(Funcionario funcionario, String senha) {
        if (funcionario != null) {
            setTitle(Strings.ALTERAR_FUNCIONARIO);
            fieldNome.setText(funcionario.getNome());
            fieldCPF.setText(funcionario.getCpf());
            fieldSalario.setText(String.valueOf(funcionario.getSalario()));
            fieldEmail.setText(funcionario.getEmail());
            fieldSenha.setText(senha);

            radioFuncionario.setSelected(!funcionario.isGerente());
            radioGerente.setSelected(funcionario.isGerente());


            fieldNome.setEditable(funcionario.getFuid() != 0 && ControladorUI.funcionarioLogado.isGerente());
            fieldCPF.setEditable(funcionario.getFuid() != 0 && ControladorUI.funcionarioLogado.isGerente());
            fieldSalario.setEditable(funcionario.getFuid() != 0 && ControladorUI.funcionarioLogado.isGerente());
            fieldEmail.setEditable(funcionario.getFuid() != 0 && ControladorUI.funcionarioLogado.isGerente());
            fieldSenha.setEditable(ControladorUI.funcionarioLogado.isGerente() || ControladorUI.funcionarioLogado.getFuid() == funcionario.getFuid());

            radioGerente.setEnabled(ControladorUI.funcionarioLogado.isGerente());
            radioFuncionario.setEnabled(funcionario.getFuid() != 0 && ControladorUI.funcionarioLogado.isGerente());
            btnRemoverFuncionario.setVisible(funcionario.getFuid() != 0 && ControladorUI.funcionarioLogado.isGerente());

        } else {
            setTitle(Strings.CADASTRAR_FUNCIONARIO);
        }

        btnRemoverFuncionario.setVisible(funcionario != null);

        ButtonGroup grupoBtnRadio = new ButtonGroup();
        grupoBtnRadio.add(radioFuncionario);
        grupoBtnRadio.add(radioGerente);
    }

    private void configurarFiltros() {
        fieldSalario.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) ||
                        (c == KeyEvent.VK_COMMA))) {
                    e.consume();
                }
            }
        });

        fieldCPF.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) ||
                        (c == KeyEvent.VK_PERIOD) ||
                        (c == KeyEvent.VK_MINUS))) {
                    e.consume();
                }
            }
        });
    }

    private void configurarBotoes(Funcionario funcionario) {

        btnCancelar.addActionListener(e -> dispose());

        btnRemoverFuncionario.addActionListener(e -> {
            if (funcionario != null) {
                ControladorFuncionarios.removerFuncionario(funcionario.getFuid());

                ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
                dispose();
            }
        });

        btnSalvar.addActionListener(e -> {

            if (funcionario == null) {
                ControladorFuncionarios.adicionarFuncionario(
                        fieldNome.getText(),
                        fieldEmail.getText(),
                        fieldCPF.getText(),
                        fieldSenha.getText(),
                        fieldSalario.getText(),
                        radioGerente.isSelected()
                );
            } else {
                ControladorFuncionarios.atualizarFuncionario(
                        funcionario.getFuid(),
                        fieldNome.getText(),
                        fieldEmail.getText(),
                        fieldCPF.getText(),
                        fieldSenha.getText(),
                        fieldSalario.getText(),
                        radioGerente.isSelected()
                );
            }

            ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
            dispose();
        });
    }

    @Override
    public void configurarCores() {
        contentPane.setBackground(BACKGROUND);
        panelTop.setBackground(PAINEIS);
        panelTop.setBorder(BORDA);
        panelBot.setBackground(BACKGROUND);
        panelOpcoes.setBackground(BACKGROUND);

        panelHolderNome.setBackground(PAINEIS);
        panelHolderCPF.setBackground(PAINEIS);
        panelHolderSalario.setBackground(PAINEIS);
        panelHolderEmail.setBackground(PAINEIS);
        panelHolderSenha.setBackground(PAINEIS);
        panelHolderCargo.setBackground(PAINEIS);

        lblNome.setForeground(TEXTO);
        lblCPF.setForeground(TEXTO);
        lblSalario.setForeground(TEXTO);
        lblEmail.setForeground(TEXTO);
        lblSenha.setForeground(TEXTO);
        lblCargo.setForeground(TEXTO);

        radioGerente.setBackground(PAINEIS);
        radioGerente.setForeground(TEXTO);
        radioFuncionario.setBackground(PAINEIS);
        radioFuncionario.setForeground(TEXTO);

        fieldNome.setBackground(PAINEIS);
        fieldNome.setForeground(TEXTO);
        fieldNome.setBorder(BORDA);

        fieldCPF.setBackground(PAINEIS);
        fieldCPF.setForeground(TEXTO);
        fieldCPF.setBorder(BORDA);

        fieldSalario.setBackground(PAINEIS);
        fieldSalario.setForeground(TEXTO);
        fieldSalario.setBorder(BORDA);

        fieldEmail.setBackground(PAINEIS);
        fieldEmail.setForeground(TEXTO);
        fieldEmail.setBorder(BORDA);

        fieldSenha.setBackground(PAINEIS);
        fieldSenha.setForeground(TEXTO);
        fieldSenha.setBorder(BORDA);
    }
}
