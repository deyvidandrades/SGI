package views;

import controllers.ControladorImoveis;
import controllers.ControladorUI;
import controllers.entidades.Imovel;
import controllers.interfaces.Cores;
import controllers.interfaces.Strings;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogoAlterarImoveis extends JDialog implements Cores {
    private JPanel contentPane;
    private JButton btnSalvar;
    private JButton btnRemoverImovel;
    private JButton btnCancelar;
    private JTextField fieldNomeProprietario;
    private JTextField fieldValorLocacao;
    private JTextField fieldValorVenda;
    private JTextField fieldNumeroQuartos;
    private JTextField fieldEndereco;
    private JTextField fieldNumeroBanheiros;
    private JRadioButton radioDisponivelVenda;
    private JRadioButton radioDisponivelLocacao;
    private JPanel panelTop;
    private JPanel panelBot;
    private JPanel panelOpcoes;
    private JPanel panelHolderNome;
    private JPanel panelHolderEndereco;
    private JPanel panelHolderValorLocacao;
    private JPanel panelHolderValorVenda;
    private JPanel panelHolderQuartos;
    private JPanel panelHolderBanheiros;
    private JPanel panelHolderDisponibilidade;
    private JLabel lblNome;
    private JLabel lblEndereco;
    private JLabel lblLocacao;
    private JLabel lblVenda;
    private JLabel lblQuartos;
    private JLabel lblBanheiros;
    private JLabel lblDisponibilidade;

    public DialogoAlterarImoveis(Imovel imovel) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);
        configurarCores();

        /*Se existir o imovel preenchemos os campos com os dados*/
        configurarUI(imovel);

        /*Configuração dos listeners de busca*/
        configurarFiltros();

        /*Configuração dos listeners de botes*/
        configurarBotoes(imovel);

    }

    private void configurarUI(Imovel imovel) {
        if (imovel != null) {
            setTitle(Strings.ALTERAR_IMOVEL);
            fieldNomeProprietario.setText(imovel.getNomeProprietario());
            fieldEndereco.setText(imovel.getEndereco());
            fieldValorLocacao.setText(String.valueOf(imovel.getValorLocacao()));
            fieldValorVenda.setText(String.valueOf(imovel.getValorVenda()));
            fieldNumeroQuartos.setText(String.valueOf(imovel.getNumQuartos()));
            fieldNumeroBanheiros.setText(String.valueOf(imovel.getNumBanheiros()));
            radioDisponivelLocacao.setSelected(imovel.isVenda());
            radioDisponivelVenda.setSelected(!imovel.isVenda());


        } else {
            setTitle(Strings.CADASTRAR_IMOVEL);
        }


        btnRemoverImovel.setVisible(imovel != null);

        ButtonGroup grupoBtnRadio = new ButtonGroup();
        grupoBtnRadio.add(radioDisponivelVenda);
        grupoBtnRadio.add(radioDisponivelLocacao);
    }

    private void configurarFiltros() {
        fieldValorVenda.addKeyListener(new KeyAdapter() {
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

        fieldValorLocacao.addKeyListener(new KeyAdapter() {
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

        fieldNumeroBanheiros.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });

        fieldNumeroQuartos.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
    }

    private void configurarBotoes(Imovel imovel) {
        btnCancelar.addActionListener(e -> dispose());

        btnRemoverImovel.addActionListener(e -> {
            if (imovel != null) {
                ControladorImoveis.removerImovel(imovel.getImid());

                ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
                dispose();
            }
        });

        btnSalvar.addActionListener(e -> {
            if (imovel == null)
                ControladorImoveis.adicionarImovel(
                        fieldNomeProprietario.getText(),
                        fieldEndereco.getText(),
                        fieldValorLocacao.getText(),
                        fieldValorVenda.getText(),
                        fieldNumeroQuartos.getText(),
                        fieldNumeroBanheiros.getText(),
                        radioDisponivelLocacao.isSelected()
                );
            else
                ControladorImoveis.atualizarImovel(
                        imovel.getImid(),
                        fieldNomeProprietario.getText(),
                        fieldEndereco.getText(),
                        fieldValorLocacao.getText(),
                        fieldValorVenda.getText(),
                        fieldNumeroQuartos.getText(),
                        fieldNumeroBanheiros.getText(),
                        radioDisponivelLocacao.isSelected()
                );

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
        panelHolderEndereco.setBackground(PAINEIS);
        panelHolderValorLocacao.setBackground(PAINEIS);
        panelHolderValorVenda.setBackground(PAINEIS);
        panelHolderQuartos.setBackground(PAINEIS);
        panelHolderBanheiros.setBackground(PAINEIS);
        panelHolderDisponibilidade.setBackground(PAINEIS);

        radioDisponivelLocacao.setBackground(PAINEIS);
        radioDisponivelLocacao.setForeground(TEXTO);
        radioDisponivelVenda.setBackground(PAINEIS);
        radioDisponivelVenda.setForeground(TEXTO);

        lblNome.setForeground(TEXTO);
        lblEndereco.setForeground(TEXTO);
        lblLocacao.setForeground(TEXTO);
        lblVenda.setForeground(TEXTO);
        lblQuartos.setForeground(TEXTO);
        lblBanheiros.setForeground(TEXTO);
        lblDisponibilidade.setForeground(TEXTO);


        fieldNomeProprietario.setBackground(PAINEIS);
        fieldNomeProprietario.setForeground(TEXTO);
        fieldNomeProprietario.setBorder(BORDA);

        fieldEndereco.setBackground(PAINEIS);
        fieldEndereco.setForeground(TEXTO);
        fieldEndereco.setBorder(BORDA);

        fieldValorLocacao.setBackground(PAINEIS);
        fieldValorLocacao.setForeground(TEXTO);
        fieldValorLocacao.setBorder(BORDA);

        fieldValorVenda.setBackground(PAINEIS);
        fieldValorVenda.setForeground(TEXTO);
        fieldValorVenda.setBorder(BORDA);

        fieldNumeroQuartos.setBackground(PAINEIS);
        fieldNumeroQuartos.setForeground(TEXTO);
        fieldNumeroQuartos.setBorder(BORDA);

        fieldNumeroBanheiros.setBackground(PAINEIS);
        fieldNumeroBanheiros.setForeground(TEXTO);
        fieldNumeroBanheiros.setBorder(BORDA);
    }
}
