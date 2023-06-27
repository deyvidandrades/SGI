package views;

import controllers.ControladorLogin;
import controllers.ControladorUI;
import controllers.interfaces.Cores;
import controllers.interfaces.Dimensoes;
import controllers.interfaces.FrameInterface;
import controllers.interfaces.Strings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TelaLogin implements FrameInterface ,Cores{

    private JPanel panelBG;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JLabel img;
    private JButton acessarButton;
    private JLabel footer;
    private JPanel panellogin;
    private JPanel panelTitulo;
    private JPanel panelSeparador;
    private JLabel lblTitulo;
    private JLabel lblEmail;
    private JLabel lblSenha;

    public TelaLogin() {
        super();
        img.setIcon(icone_64);
        configurarCores();

        footer.setText(Strings.FOOTER + "  v" + Strings.VERSAO);

        acessarButton.addActionListener(actionEvent -> {
            if (ControladorLogin.autenticarFuncionario(textField1.getText(), String.valueOf(passwordField1.getPassword()))) {
                ControladorUI.exibirTelaDashboard();
            } else {
                JOptionPane.showMessageDialog(null, Strings.MENSAGEM_LOGIN_INVALIDO);
            }
        });

    }

    private void configurarCores() {
        panelBG.setBackground(BACKGROUND);
        panellogin.setBackground(PAINEIS);
        panellogin.setBorder(BORDA);

        panelSeparador.setBackground(PAINEIS);

        panelTitulo.setBackground(BACKGROUND);

        lblEmail.setForeground(TEXTO);
        lblSenha.setForeground(TEXTO);

        lblTitulo.setForeground(TEXTO);
    }

    @Override
    public void show() {
        frame.setContentPane(new TelaLogin().panelBG);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle(String.format("%s %s", Strings.LOGIN, Strings.SGI));
        frame.setSize(Dimensoes.LOGIN);
        frame.setLocation(new Point((tamanhoTela.width - frame.getWidth()) / 2, (tamanhoTela.height - frame.getHeight()) / 2));

        try {
            frame.setIconImage(ImageIO.read(new File(Strings.ICONE_64)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }
}
