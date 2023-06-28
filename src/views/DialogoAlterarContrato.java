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
    private final ArrayList<Imovel> arrayImoveis = new ArrayList<>();
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

    public DialogoAlterarContrato(Contrato contrato, ArrayList<Imovel> imoveis, ArrayList<Cliente> clientes, int posicaoImovel, int posicaoCliente) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);
        configurarCores();

        /*Adicionando imoveis para serem exibidos na seleção suspensa*/
        popularComboBoxes(clientes, imoveis, contrato);

        /*Se existir o imovel preenchemos os campos com os dados*/
        configurarUI(contrato, imoveis, posicaoCliente, posicaoImovel);

        /*Configuração dos listeners de botes*/
        configurarBotoes(contrato, clientes);
    }

    private void popularComboBoxes(ArrayList<Cliente> clientes, ArrayList<Imovel> imoveis, Contrato contrato) {
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
    }

    private void configurarUI(Contrato contrato, ArrayList<Imovel> imoveis, int posicaoCliente, int posicaoImovel) {
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

        } else {
            setTitle(Strings.CADASTRAR_CONTRATO);
        }
    }


    @SuppressWarnings("deprecation")
    private void configurarBotoes(Contrato contrato, ArrayList<Cliente> clientes) {
        btnCancelar.addActionListener(e -> dispose());

        btnRemoverContrato.addActionListener(e -> {
            if (contrato != null) {
                ControladorContratos.removerContrato(contrato.getCoid());
                ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
                dispose();
            }
        });

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
                        dataFim
                );
            } else {
                Date dataUpdate = contrato.getDataFim();
                dataUpdate.setYear(dataUpdate.getYear() + comboVigencia.getSelectedIndex());
                ControladorContratos.atualizarContrato(
                        contrato.getCoid(),
                        contrato.getCliente(),
                        contrato.getImovel(),
                        simple.format(contrato.getDataInicio()),
                        simple.format(dataUpdate)
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
