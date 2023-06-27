package views;

import controllers.ControladorClientes;
import controllers.ControladorUI;
import controllers.entidades.Cliente;
import controllers.interfaces.Cores;
import controllers.interfaces.Strings;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogoAlterarCliente extends JDialog implements Cores {
    private JPanel contentPane;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private JTextField fieldEmail;
    private JTextField fieldNome;
    private JTextField fieldCPF;
    private JTextField fieldRenda;
    private JButton btnRemoverCliente;
    private JPanel panelBot;
    private JPanel panelTop;
    private JPanel panelOpcoes;
    private JPanel panelHolderNome;
    private JPanel panelHolderCPF;
    private JPanel panelHolderRenda;
    private JPanel panelHolderEmail;
    private JLabel lblNome;
    private JLabel lblCPF;
    private JLabel lblRenda;
    private JLabel lblEmail;

    public DialogoAlterarCliente(Cliente cliente) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);
        configurarCores();

        if (cliente != null) {
            setTitle(Strings.ALTERAR_CLIENTE);
            fieldNome.setText(cliente.getNome());
            fieldCPF.setText(cliente.getCpf());
            fieldRenda.setText(String.valueOf(cliente.getRenda()));
            fieldEmail.setText(cliente.getEmail());


            btnRemoverCliente.addActionListener(e -> {
                ControladorClientes.removerCliente(cliente.getClid());

                ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
                dispose();
            });
        } else {
            setTitle(Strings.CADASTRAR_CLIENTE);
        }


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

        fieldRenda.addKeyListener(new KeyAdapter() {
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

        btnRemoverCliente.setVisible(cliente != null);

        btnCancelar.addActionListener(e -> dispose());

        btnSalvar.addActionListener(e -> {
            if (cliente == null)
                ControladorClientes.adicionarCliente(fieldNome.getText(), fieldEmail.getText(), fieldCPF.getText(), fieldRenda.getText());
            else
                ControladorClientes.atualizarCliente(fieldNome.getText(), fieldEmail.getText(), fieldCPF.getText(), fieldRenda.getText());

            ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
            dispose();
        });
    }

    private void configurarCores() {
        contentPane.setBackground(BACKGROUND);
        panelTop.setBackground(PAINEIS);
        panelTop.setBorder(BORDA);

        panelBot.setBackground(BACKGROUND);
        panelOpcoes.setBackground(BACKGROUND);


        panelHolderNome.setBackground(PAINEIS);
        panelHolderCPF.setBackground(PAINEIS);
        panelHolderEmail.setBackground(PAINEIS);
        panelHolderRenda.setBackground(PAINEIS);

        fieldNome.setBackground(PAINEIS);
        fieldNome.setForeground(TEXTO);
        fieldNome.setBorder(BORDA);

        fieldEmail.setBackground(PAINEIS);
        fieldEmail.setForeground(TEXTO);
        fieldEmail.setBorder(BORDA);

        fieldRenda.setBackground(PAINEIS);
        fieldRenda.setForeground(TEXTO);
        fieldRenda.setBorder(BORDA);

        fieldCPF.setBackground(PAINEIS);
        fieldCPF.setForeground(TEXTO);
        fieldCPF.setBorder(BORDA);

        lblCPF.setForeground(TEXTO);
        lblEmail.setForeground(TEXTO);
        lblNome.setForeground(TEXTO);
        lblRenda.setForeground(TEXTO);


    }
}
