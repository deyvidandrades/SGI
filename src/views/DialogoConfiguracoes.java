package views;

import controllers.ControladorUI;
import controllers.interfaces.Cores;
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
    private JComboBox<String> comboTemaEscuro;
    private JPanel panelBot;
    private JPanel panelMid;
    private JPanel panelTop;
    private JPanel panelHolderConfiguracoes;
    private JPanel panelHolderCreditos;
    private JLabel lblConfiguracoes;
    private JLabel lblPosicao;
    private JLabel lblTema;

    public DialogoConfiguracoes() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(Strings.CONFIGURACOES);
        configurarCores();

        img.setIcon(icone_32);

        comboPosicaoMenus.addItem(Strings.MENU_TOPO);
        comboPosicaoMenus.addItem(Strings.MENU_ESQUERDA);

        comboTemaEscuro.addItem(Strings.TEMA_CLARO);
        comboTemaEscuro.addItem(Strings.TEMA_ESCURO);

        comboPosicaoMenus.addItemListener(e -> ControladorUI.mudarPosicaoMenus(comboPosicaoMenus.getSelectedIndex()));
        comboTemaEscuro.addItemListener(e -> ControladorUI.mudarTema(comboTemaEscuro.getSelectedIndex() == 1));

        comboPosicaoMenus.setSelectedIndex((int) ControladorUI.getConfiguracoes().get("posicaoMenus")-1);
        comboTemaEscuro.setSelectedIndex((boolean) ControladorUI.getConfiguracoes().get("temaEscuro") ? 1 : 0);

        lblVersao.setText(Strings.VERSAO);
        lblDesenvolvidoPor.setText(Strings.FOOTER);

        buttonOK.addActionListener(e -> dispose());
    }


    private void configurarCores() {
        contentPane.setBackground(Cores.BACKGROUND);

        panelTop.setBackground(Cores.PAINEIS);
        panelTop.setBorder(Cores.BORDA);
        panelMid.setBackground(Cores.PAINEIS);
        panelMid.setBorder(Cores.BORDA);

        panelHolderConfiguracoes.setBackground(Cores.PAINEIS);
        panelHolderCreditos.setBackground(Cores.PAINEIS);

        comboPosicaoMenus.setBackground(Cores.PAINEIS);
        comboPosicaoMenus.setBorder(Cores.BORDA);
        comboPosicaoMenus.setForeground(Cores.TEXTO);

        comboTemaEscuro.setBackground(Cores.PAINEIS);
        comboTemaEscuro.setBorder(Cores.BORDA);
        comboTemaEscuro.setForeground(Cores.TEXTO);

        panelBot.setBackground(Cores.BACKGROUND);
    }
}
