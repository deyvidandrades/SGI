package views;

import controllers.ControladorContratos;
import controllers.ControladorUI;
import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.entidades.Imovel;
import controllers.interfaces.Cores;
import controllers.interfaces.Strings;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DialogoAlterarContrato extends JDialog implements Cores {
    private JPanel contentPane;
    private JTextField fieldDataCriacao;
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboImoveis;
    private JComboBox<String> comboVigencia;
    private JButton btnSalvar;
    private JButton btnRemoverContrato;
    private JButton btnCancelar;
    private JTextField fieldDataTermino;
    private JPanel panelCriacao;
    private JPanel panelTermino;
    private JLabel lblVigencia;
    private JLabel lblTipoContrato;
    private JLabel lblCliente;
    private JLabel lblImovel;
    private JLabel lblCriacao;
    private JLabel lblTermino;
    private JLabel lblTipo;
    private JPanel panelHolderTipo;
    private JPanel panelExtensao;
    private JPanel panelHolderImovel;
    private JPanel panelHolderCliente;
    private JPanel panelTop;
    private JPanel panelOpcoes;
    private JPanel panelBot;

    private final ArrayList<Imovel> arrayImoveis = new ArrayList<>();

    @SuppressWarnings("deprecation")
    public DialogoAlterarContrato(Contrato contrato, ArrayList<Imovel> imoveis, ArrayList<Cliente> clientes, int posicaoImovel, int posicaoCliente) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);
        configurarCores();

        for (int i = 0; i <= 10; i++) {
            comboVigencia.addItem(String.valueOf(i));
        }

        for (Cliente cliente : clientes) {
            comboCliente.addItem(cliente.getClid() + ": " + cliente.getCpf() + " - " + cliente.getNome());
        }

        for (Imovel imovel : imoveis) {
            if (imovel.isDisponivel() || contrato != null) {
                comboImoveis.addItem(imovel.getImid() + ": " + imovel.getEndereco());
                arrayImoveis.add(imovel);
            }
        }

        fieldDataCriacao.setEditable(contrato == null);
        fieldDataTermino.setEditable(contrato == null);
        panelCriacao.setVisible(contrato != null);
        panelTermino.setVisible(contrato != null);
        lblVigencia.setText(contrato != null ? Strings.EXTENSAO_CONTRATO : Strings.EXTENSAO_VIGENCIA_CONTRATO);

        btnRemoverContrato.setVisible(contrato != null);


        boolean venda = contrato != null ? arrayImoveis.get(comboImoveis.getSelectedIndex()).isVenda() : imoveis.get(0).isVenda();

        comboImoveis.addActionListener(e -> lblTipoContrato.setText(venda ? Strings.VENDA : Strings.LOCACAO));

        if (contrato != null) {
            setTitle(Strings.ALTERAR_CONTRATO);
            DateFormat simple = new SimpleDateFormat("dd/MM/yyyy");

            fieldDataCriacao.setText(simple.format(contrato.getDataInicio()));
            fieldDataTermino.setText(simple.format(contrato.getDataFim()));

            comboCliente.setEnabled(false);
            comboImoveis.setEnabled(false);

            comboCliente.setSelectedIndex(posicaoCliente);
            comboImoveis.setSelectedIndex(posicaoImovel);
            lblTipoContrato.setText(venda ? Strings.VENDA : Strings.LOCACAO);

            btnRemoverContrato.addActionListener(e -> {
                ControladorContratos.removerContrato(contrato.getCoid());

                ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
                dispose();
            });
        } else {
            setTitle(Strings.CADASTRAR_CONTRATO);
        }

        btnCancelar.addActionListener(e -> dispose());

        btnSalvar.addActionListener(e -> {
            DateFormat simple = new SimpleDateFormat("dd/MM/yyyy");

            if (contrato == null) {
                Date data = new Date();

                data.setYear(data.getYear() + comboVigencia.getSelectedIndex() + 1);

                String dataCriacao = simple.format(new Date());
                String dataFim = simple.format(data);

                ControladorContratos.adicionarContrato(
                        clientes.get(comboCliente.getSelectedIndex()),
                        arrayImoveis.get(comboImoveis.getSelectedIndex()),
                        dataCriacao,
                        dataFim,
                        arrayImoveis.get(comboImoveis.getSelectedIndex()).isVenda()
                );
            } else {
                Date dataUpdate = contrato.getDataFim();
                dataUpdate.setYear(dataUpdate.getYear() + comboVigencia.getSelectedIndex());
                ControladorContratos.atualizarContrato(
                        contrato.getCoid(),
                        contrato.getCliente(),
                        contrato.getImovel(),
                        simple.format(contrato.getDataInicio()),
                        simple.format(dataUpdate),
                        contrato.getImovel().isVenda()
                );
            }

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

        panelHolderCliente.setBackground(PAINEIS);
        panelHolderImovel.setBackground(PAINEIS);
        panelHolderTipo.setBackground(PAINEIS);
        panelExtensao.setBackground(PAINEIS);
        panelCriacao.setBackground(PAINEIS);
        panelTermino.setBackground(PAINEIS);

        lblTipo.setForeground(TEXTO);
        lblCliente.setForeground(TEXTO);
        lblTipoContrato.setForeground(TEXTO);
        lblCriacao.setForeground(TEXTO);
        lblImovel.setForeground(TEXTO);
        lblTermino.setForeground(TEXTO);
        lblVigencia.setForeground(TEXTO);

        comboImoveis.setBackground(PAINEIS);
        comboImoveis.setBorder(BORDA);
        comboImoveis.setForeground(TEXTO);
        comboCliente.setBackground(PAINEIS);
        comboCliente.setBorder(BORDA);
        comboCliente.setForeground(TEXTO);
        comboVigencia.setBackground(PAINEIS);
        comboVigencia.setBorder(BORDA);
        comboVigencia.setForeground(TEXTO);


    }
}
