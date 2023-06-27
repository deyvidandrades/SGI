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

    public DialogoAlterarFuncionario(Funcionario funcionario, String senha) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);


        btnRemoverFuncionario.setVisible(funcionario != null);

        if (funcionario != null) {
            setTitle(Strings.ALTERAR_FUNCIONARIO);
            fieldNome.setText(funcionario.getNome());
            fieldCPF.setText(funcionario.getCpf());
            fieldSalario.setText(String.valueOf(funcionario.getSalario()));
            fieldEmail.setText(funcionario.getEmail());
            fieldSenha.setText(senha);

            radioFuncionario.setSelected(!funcionario.isGerente());
            radioGerente.setSelected(funcionario.isGerente());

            btnRemoverFuncionario.addActionListener(e -> {
                ControladorFuncionarios.removerFuncionario(funcionario.getFuid());

                ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
                dispose();
            });

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

        fieldSalario.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) ||
                        (c == KeyEvent.VK_COMMA))) {
                    getToolkit().beep();
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
                    getToolkit().beep();
                    e.consume();
                }
            }
        });

        ButtonGroup grupoBtnRadio = new ButtonGroup();
        grupoBtnRadio.add(radioFuncionario);
        grupoBtnRadio.add(radioGerente);

        btnCancelar.addActionListener(e -> dispose());

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

    }
}
