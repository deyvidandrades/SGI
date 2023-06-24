package views;

import controllers.ControladorContratos;
import controllers.ControladorUI;
import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.entidades.Imovel;
import controllers.interfaces.Strings;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DialogoAlterarContrato extends JDialog {
    private JPanel contentPane;
    private JTextField fieldDataCriacao;
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboImoveis;
    private JRadioButton radioDisponivelVenda;
    private JRadioButton radioDisponivelLocacao;
    private JComboBox<String> comboVigencia;
    private JButton btnSalvar;
    private JButton btnRemoverContrato;
    private JButton btnCancelar;
    private JTextField fieldDataTermino;
    private JPanel panelCriacao;
    private JPanel panelTermino;
    private JLabel lblVigencia;

    @SuppressWarnings("deprecation")
    public DialogoAlterarContrato(Contrato contrato, ArrayList<Imovel> imoveis, ArrayList<Cliente> clientes) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnSalvar);

        for (int i = 0; i <= 10; i++) {
            comboVigencia.addItem(String.valueOf(i));
        }

        for (Cliente cliente : clientes)
            comboCliente.addItem(cliente.getClid() + ": " + cliente.getCpf() + " - " + cliente.getNome());

        for (Imovel imovel : imoveis)
            if (imovel.isDisponivel())
                comboImoveis.addItem(imovel.getImid() + ": " + imovel.getEndereco());

        fieldDataCriacao.setEditable(contrato == null);
        fieldDataTermino.setEditable(contrato == null);
        panelCriacao.setVisible(contrato != null);
        panelTermino.setVisible(contrato != null);
        lblVigencia.setText(contrato != null ? Strings.EXTENSAO_CONTRATO : Strings.EXTENSAO_VIGENCIA_CONTRATO);

        btnRemoverContrato.setVisible(contrato != null);

        if (contrato != null) {
            setTitle(Strings.ALTERAR_CONTRATO);
            DateFormat simple = new SimpleDateFormat("dd/MM/yyyy");

            fieldDataCriacao.setText(simple.format(contrato.getDataInicio()));
            fieldDataTermino.setText(simple.format(contrato.getDataFim()));

            comboCliente.setSelectedIndex(contrato.getCliente().getClid());
            comboImoveis.setSelectedIndex(contrato.getImovel().getImid());

            radioDisponivelLocacao.setSelected(contrato.isTipo());
            radioDisponivelVenda.setSelected(!contrato.isTipo());

            comboCliente.setEnabled(false);
            comboImoveis.setEnabled(false);
            radioDisponivelVenda.setEnabled(false);
            radioDisponivelLocacao.setEnabled(false);

            btnRemoverContrato.addActionListener(e -> {
                ControladorContratos.removerContrato(contrato.getCoid());

                ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
                dispose();
            });
        } else {
            setTitle(Strings.CADASTRAR_CONTRATO);
        }

        ButtonGroup grupoBtnRadio = new ButtonGroup();
        grupoBtnRadio.add(radioDisponivelVenda);
        grupoBtnRadio.add(radioDisponivelLocacao);

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
                        imoveis.get(comboImoveis.getSelectedIndex()),
                        dataCriacao,
                        dataFim,
                        radioDisponivelVenda.isSelected()
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
                        radioDisponivelVenda.isSelected()
                );
            }

            ControladorUI.instanciaTelaDashboard.atualizarDadosTabelas();
            dispose();
        });
    }
}
