package views;

import controllers.ControladorLogin;
import controllers.ControladorUI;
import controllers.interfaces.Dimensoes;
import controllers.interfaces.FrameInterface;
import controllers.interfaces.Strings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TelaLogin implements FrameInterface {

    private JPanel panel1;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JLabel img;
    private JButton acessarButton;
    private JLabel footer;

    public TelaLogin() {
        super();
        img.setIcon(icone_64);

        footer.setText(Strings.FOOTER + "  v" + Strings.VERSAO);

        acessarButton.addActionListener(actionEvent -> {
            if (ControladorLogin.autenticarFuncionario(textField1.getText(), String.valueOf(passwordField1.getPassword()))) {
                ControladorUI.exibirTelaDashboard();
            } else {
                JOptionPane.showMessageDialog(null, Strings.MENSAGEM_LOGIN_INVALIDO);
            }
        });

    }

    @Override
    public void show() {
        frame.setContentPane(new TelaLogin().panel1);
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
