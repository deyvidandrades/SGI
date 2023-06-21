package views;

import controllers.ControladorUI;
import controllers.interfaces.FrameInterface;
import controllers.interfaces.Strings;

import javax.swing.*;

public class DialogoConfiguracoes extends JDialog implements FrameInterface {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel img;
    private JLabel lblDesenvolvidoPor;
    private JLabel lblVersao;
    private JComboBox<String> comboPosicaoMenus;

    public DialogoConfiguracoes() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(Strings.CONFIGURACOES);

        img.setIcon(icone_32);

        comboPosicaoMenus.addItem(Strings.MENU_TOPO);
        comboPosicaoMenus.addItem(Strings.MENU_ESQUERDA);

        comboPosicaoMenus.addItemListener(e -> ControladorUI.mudarPosicaoMenus(comboPosicaoMenus.getSelectedIndex()));

        lblVersao.setText(Strings.VERSAO);
        lblDesenvolvidoPor.setText(Strings.FOOTER);

        buttonOK.addActionListener(e -> dispose());
    }
}
