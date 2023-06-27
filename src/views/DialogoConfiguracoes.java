package views;

import controllers.ControladorUI;
import controllers.interfaces.Cores;
import controllers.interfaces.FrameInterface;
import controllers.interfaces.Strings;

import javax.swing.*;

public class DialogoConfiguracoes extends JDialog implements FrameInterface, Cores {
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
    private JLabel lblTitulo;
    private JLabel lblDescricao;
    private JLabel lblVersao1;

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

        comboPosicaoMenus.setSelectedIndex((int) ControladorUI.getConfiguracoes().get("posicaoMenus") - 1);
        comboTemaEscuro.setSelectedIndex((boolean) ControladorUI.getConfiguracoes().get("temaEscuro") ? 1 : 0);

        comboPosicaoMenus.addItemListener(e -> ControladorUI.mudarPosicaoMenus(comboPosicaoMenus.getSelectedIndex()));
        comboTemaEscuro.addItemListener(e -> ControladorUI.mudarTema(comboTemaEscuro.getSelectedIndex() == 1));

        lblVersao.setText(Strings.VERSAO);
        lblDesenvolvidoPor.setText(Strings.FOOTER);

        buttonOK.addActionListener(e -> dispose());
    }

    @Override
    public void configurarCores() {
        contentPane.setBackground(BACKGROUND);

        panelTop.setBackground(PAINEIS);
        panelTop.setBorder(BORDA);
        panelMid.setBackground(PAINEIS);
        panelMid.setBorder(BORDA);

        panelHolderConfiguracoes.setBackground(PAINEIS);
        panelHolderCreditos.setBackground(PAINEIS);

        comboPosicaoMenus.setBackground(PAINEIS);
        comboPosicaoMenus.setBorder(BORDA);
        comboPosicaoMenus.setForeground(TEXTO);

        comboTemaEscuro.setBackground(PAINEIS);
        comboTemaEscuro.setBorder(BORDA);
        comboTemaEscuro.setForeground(TEXTO);

        lblConfiguracoes.setForeground(TEXTO);
        lblPosicao.setForeground(TEXTO);
        lblTema.setForeground(TEXTO);
        lblTitulo.setForeground(TEXTO);
        lblDescricao.setForeground(TEXTO);
        lblVersao1.setForeground(TEXTO);
        lblVersao.setForeground(TEXTO);
        lblDesenvolvidoPor.setForeground(TEXTO);

        panelBot.setBackground(BACKGROUND);
    }
}
