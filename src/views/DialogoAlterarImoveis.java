package views;

import controllers.ControladorImoveis;
import controllers.ControladorUI;
import controllers.entidades.Imovel;
import controllers.interfaces.Strings;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogoAlterarImoveis extends JDialog {
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

    public DialogoAlterarImoveis(Imovel imovel) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);

        if (imovel != null) {
            setTitle(Strings.ALTERAR_IMOVEL);
            fieldNomeProprietario.setText(imovel.getNomeProprietario());
            fieldEndereco.setText(imovel.getEndereco());
            fieldValorLocacao.setText(String.valueOf(imovel.getValorLocacao()));
            fieldValorVenda.setText(String.valueOf(imovel.getValorVenda()));
            fieldNumeroQuartos.setText(String.valueOf(imovel.getNumQuartos()));
            fieldNumeroBanheiros.setText(String.valueOf(imovel.getNumBanheiros()));
            radioDisponivelLocacao.setSelected(!imovel.isVenda());
            radioDisponivelVenda.setSelected(imovel.isVenda());

            btnRemoverImovel.addActionListener(e -> {
                ControladorImoveis.removerImovel(imovel.getImid());

                ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
                dispose();
            });

        } else {
            setTitle(Strings.CADASTRAR_IMOVEL);
        }


        btnRemoverImovel.setVisible(imovel != null);

        ButtonGroup grupoBtnRadio = new ButtonGroup();
        grupoBtnRadio.add(radioDisponivelVenda);
        grupoBtnRadio.add(radioDisponivelLocacao);

        btnCancelar.addActionListener(e -> dispose());

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
}
