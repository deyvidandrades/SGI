package views;

import controllers.interfaces.Cores;
import controllers.interfaces.Strings;

import javax.swing.*;

public class DialogoMensagem extends JDialog implements Cores {
    private JPanel contentPane;
    private JButton btnOK;
    private JLabel lblMensagem;
    private JPanel panelBot;
    private JPanel panelTop;

    public DialogoMensagem(String mensagem) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnOK);
        setTitle(Strings.SGI);
        configurarCores();

        /*Carrega a mensagem*/
        lblMensagem.setText(mensagem);

        /*Fecha o dialogo*/
        btnOK.addActionListener(e -> dispose());
    }


    @Override
    public void configurarCores() {
        panelTop.setBackground(PAINEIS);
        panelTop.setBorder(BORDA);

        lblMensagem.setForeground(TEXTO);

        contentPane.setBackground(BACKGROUND);
        panelBot.setBackground(BACKGROUND);
    }
}
