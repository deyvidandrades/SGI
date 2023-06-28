package views;

import controllers.*;
import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.entidades.Funcionario;
import controllers.entidades.Imovel;
import controllers.interfaces.Cores;
import controllers.interfaces.Dimensoes;
import controllers.interfaces.FrameInterface;
import controllers.interfaces.Strings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TelaDashboard implements FrameInterface, Strings, Cores {

    public JPanel panel1;
    private JTable tabelaClientes;
    private JTextField buscarClientes;
    private JLabel lbl_ola;
    private JButton btnSair;
    private JLabel img;
    private JButton btnCadastrarCliente;
    private JTextField buscarImoveis;
    private JButton btnCadastrarImovel;
    private JTable tabelaImoveis;
    private JTextField buscarContratosAtivos;
    private JTable tabelaContratosAtivos;
    private JButton btnCadastrarContrato;
    private JButton btnSettings;

    private JLabel lblTipoFuncionario;
    private JLabel lblStats;
    private JLabel imgClientesBuscar;
    private JLabel imgContratosAtivosBuscar;
    private JLabel imgImoveisBuscar;
    private JButton btnAtualizar;
    private JTextField buscarFuncionarios;
    private JTable tabelaFuncionarios;
    private JButton btnCadastrarFuncionario;
    private JLabel imgFuncionariosBuscar;
    private JTabbedPane tabbedMenus;
    private JTextField buscarContratosTerminados;
    private JTable tabelaContratosTerminados;
    private JLabel imgContratosTerminadosBuscar;
    private JPanel panelBarraMenu;
    private JPanel panelStatus;
    private JPanel panelBenvindo;
    private JPanel panelMenus;
    private JPanel panelBgCliente;
    private JPanel panelBgImovel;
    private JPanel panelBgContrato;
    private JPanel panelBgFuncionario;
    private JPanel panelHolderCliente;
    private JPanel panelTopClientes;
    private JPanel panelBotClientes;
    private JPanel panelBuscaCliente;
    private JScrollPane scrollClientes;
    private JLabel lblAcesso;
    private JLabel lblClientesCadastrados;
    private JLabel lblImoveisCadastrados;
    private JLabel lblContratosVigencia;
    private JLabel lblContratosTerminados;
    private JLabel lblFuncionariosCadastrados;
    private JPanel panelHolderImovel;
    private JPanel panelHolderContrato;
    private JPanel panelHolderFuncionario;
    private JPanel panelTopImovel;
    private JPanel panelTopContratoAtivo;
    private JPanel panelTopFuncionario;
    private JPanel panelBotImovel;
    private JPanel panelBotContrato;
    private JPanel panelBotFuncionario;
    private JScrollPane scrollImovel;
    private JScrollPane scrollContratoAtivo;
    private JScrollPane scrollContratoTerminado;
    private JScrollPane scrollFuncionario;
    private JPanel panelBuscaContratoAtivo;
    private JPanel panelBuscaContratoTerminado;
    private JPanel panelBuscaImovel;
    private JPanel panelBuscaFuncionario;
    private JPanel panelTopContratoTerminado;

    public TelaDashboard(Funcionario funcionarioLogado, Map<String, Object> configuracoes) {
        super();
        img.setIcon(icone_32);
        configurarCores();

        /*Configura os valores para a UI*/
        configurarUI(funcionarioLogado, configuracoes);

        /*Adiciona os listeners aos botões*/
        configurarBotoes();

        /*Cria os mouse listener das tabelas */
        inicializarTabela(tabelaClientes, DADOS_CLIENTES);
        inicializarTabela(tabelaImoveis, DADOS_IMOVEIS);
        inicializarTabela(tabelaContratosAtivos, DADOS_CONTRATOS_ATIVOS);
        inicializarTabela(tabelaContratosTerminados, DADOS_CONTRATOS_TERMINADOS);
        inicializarTabela(tabelaFuncionarios, DADOS_FUNCIONARIOS);

        /*Configura o listener dos campos de busca*/
        configurarBusca();

        /*Atualiza as tabelas*/
        atualizarDadosTabelas();
    }

    private void configurarUI(Funcionario funcionarioLogado, Map<String, Object> configuracoes) {
        btnSair.setIcon(icone_sair);
        btnSettings.setIcon(icone_configuracoes);
        btnAtualizar.setIcon(icone_atualizar);

        btnCadastrarCliente.setIcon(icone_add);
        btnCadastrarContrato.setIcon(icone_add);
        btnCadastrarImovel.setIcon(icone_add);
        btnCadastrarFuncionario.setIcon(icone_add);

        imgClientesBuscar.setIcon(icone_buscar);
        imgContratosAtivosBuscar.setIcon(icone_buscar);
        imgContratosTerminadosBuscar.setIcon(icone_buscar);
        imgImoveisBuscar.setIcon(icone_buscar);
        imgFuncionariosBuscar.setIcon(icone_buscar);

        tabbedMenus.setTabPlacement((int) configuracoes.get("posicaoMenus"));


        lbl_ola.setText(OLA + funcionarioLogado.getNome().split(" ")[0] + "," + BEM_VINDO);

        lblTipoFuncionario.setText(funcionarioLogado.isGerente() ? GERENTE : FUNCIONARIO);


        lblStats.setText(
                ControladorClientes.getNumeroClientes() +
                        ControladorContratos.getNumeroContratos(true) +
                        ControladorContratos.getNumeroContratos(false) +
                        ControladorImoveis.getNumeroImoveis() +
                        ControladorFuncionarios.getNumeroFuncionarios()
        );

        btnCadastrarContrato.setEnabled(funcionarioLogado.isGerente());
        btnCadastrarImovel.setEnabled(funcionarioLogado.isGerente());
        btnCadastrarFuncionario.setEnabled(funcionarioLogado.isGerente());
    }

    private void configurarBotoes() {
        btnCadastrarCliente.addActionListener(e -> ControladorUI.exibirDialogoAlterarCliente(-1));
        btnCadastrarImovel.addActionListener(e -> ControladorUI.exibirDialogoAlterarImoveis(-1));
        btnCadastrarContrato.addActionListener(e -> ControladorUI.exibirDialogoAlterarContratos(-1));
        btnCadastrarFuncionario.addActionListener(e -> ControladorUI.exibirDialogoAlterarFuncionarios(-1));

        btnSettings.addActionListener(e -> ControladorUI.exibirDialogoConfiguracoes());
        btnSair.addActionListener(e -> ControladorUI.exibirTelaLogin());
        btnAtualizar.addActionListener(e -> atualizarDadosTabelas());
    }

    public void atualizarDadosTabelas() {
        configurarTabela(tabelaClientes, DADOS_CLIENTES, COLUNAS_CLIENTES);
        configurarTabela(tabelaImoveis, DADOS_IMOVEIS, COLUNAS_IMOVEIS);
        configurarTabela(tabelaContratosAtivos, DADOS_CONTRATOS_ATIVOS, COLUNAS_CONTRATOS);
        configurarTabela(tabelaContratosTerminados, DADOS_CONTRATOS_TERMINADOS, COLUNAS_CONTRATOS);
        configurarTabela(tabelaFuncionarios, DADOS_FUNCIONARIOS, COLUNAS_FUNCIONARIOS);

        lblStats.setText(
                ControladorClientes.getNumeroClientes() +
                        ControladorContratos.getNumeroContratos(true) +
                        ControladorContratos.getNumeroContratos(false) +
                        ControladorImoveis.getNumeroImoveis() +
                        ControladorFuncionarios.getNumeroFuncionarios()
        );
    }

    private void inicializarTabela(JTable tabela, String key) {

        MouseAdapter mouseAdapter = null;

        switch (key) {
            case DADOS_CLIENTES -> mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int row = tabela.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        ControladorUI.exibirDialogoAlterarCliente(Integer.parseInt(tabela.getValueAt(row, 0).toString()));
                    }
                }
            };
            case DADOS_IMOVEIS -> mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int row = tabela.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        ControladorUI.exibirDialogoAlterarImoveis(Integer.parseInt(tabela.getValueAt(row, 0).toString()));
                    }
                }
            };
            case DADOS_CONTRATOS_ATIVOS -> mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int row = tabela.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        ControladorUI.exibirDialogoAlterarContratos(Integer.parseInt(tabela.getValueAt(row, 0).toString()));
                    }
                }
            };
            case DADOS_CONTRATOS_TERMINADOS -> mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int row = tabela.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        ControladorUI.exibirDialogoAlterarContratos(Integer.parseInt(tabela.getValueAt(row, 0).toString()));
                    }
                }
            };
            case DADOS_FUNCIONARIOS -> mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int row = tabela.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        ControladorUI.exibirDialogoAlterarFuncionarios(Integer.parseInt(tabela.getValueAt(row, 0).toString()));
                    }
                }
            };
        }

        if (mouseAdapter != null)
            tabela.addMouseListener(mouseAdapter);
    }

    private void configurarBusca() {
        buscarClientes.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                configurarTabela(tabelaClientes, DADOS_CLIENTES, COLUNAS_CLIENTES);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        buscarImoveis.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                configurarTabela(tabelaImoveis, DADOS_IMOVEIS, COLUNAS_IMOVEIS);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        buscarContratosAtivos.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                configurarTabela(tabelaContratosAtivos, DADOS_CONTRATOS_ATIVOS, COLUNAS_CONTRATOS);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        buscarContratosTerminados.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                configurarTabela(tabelaContratosTerminados, DADOS_CONTRATOS_TERMINADOS, COLUNAS_CONTRATOS);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        buscarFuncionarios.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                configurarTabela(tabelaFuncionarios, DADOS_FUNCIONARIOS, COLUNAS_FUNCIONARIOS);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    private void configurarTabela(JTable tabela, String key, String[] cabecalho) {
        DefaultTableModel model = new DefaultTableModel();

        for (Object name : cabecalho)
            model.addColumn(name);

        switch (key) {
            case DADOS_CLIENTES -> {
                for (Cliente cliente : ControladorClientes.getListaClientes(buscarFuncionarios.getText()))
                    model.addRow(cliente.toObject());
            }
            case DADOS_CONTRATOS_ATIVOS -> {
                for (Contrato contrato : ControladorContratos.getListaContratosAtivos(buscarFuncionarios.getText()))
                    model.addRow(contrato.toObject());
            }
            case DADOS_CONTRATOS_TERMINADOS -> {
                for (Contrato contrato : ControladorContratos.getListaContratosTerminados(buscarFuncionarios.getText()))
                    model.addRow(contrato.toObject());
            }
            case DADOS_IMOVEIS -> {
                for (Imovel imovel : ControladorImoveis.getListaImoveis(buscarFuncionarios.getText()))
                    model.addRow(imovel.toObject());
            }
            case DADOS_FUNCIONARIOS -> {
                for (Funcionario funcionario : ControladorFuncionarios.getListaFuncionarios(buscarFuncionarios.getText()))
                    model.addRow(funcionario.toObject());
            }
        }

        tabela.setModel(model);

        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);
    }

    public void setMenuPosition(int selectedIndex) {
        tabbedMenus.setTabPlacement(selectedIndex);
    }

    @Override
    public void configurarCores() {
        panelBarraMenu.setBackground(PAINEIS);
        panelBarraMenu.setBorder(BORDA);


        panelStatus.setBackground(PAINEIS);
        panelBenvindo.setBackground(PAINEIS);
        panelMenus.setBackground(PAINEIS);

        tabbedMenus.setBackground(PAINEIS);
        tabbedMenus.setForeground(TEXTO);
        tabbedMenus.setBorder(BORDA);
        panel1.setBackground(PAINEIS);
        panel1.setBorder(BORDA);

        panelBgCliente.setBackground(BACKGROUND);
        panelBgCliente.setBorder(null);
        panelBgImovel.setBackground(BACKGROUND);
        panelBgImovel.setBorder(null);
        panelBgContrato.setBackground(BACKGROUND);
        panelBgContrato.setBorder(null);
        panelBgFuncionario.setBackground(BACKGROUND);
        panelBgFuncionario.setBorder(null);

        panelHolderCliente.setBackground(PAINEIS);
        panelHolderCliente.setBorder(BORDA);

        panelHolderImovel.setBackground(PAINEIS);
        panelHolderImovel.setBorder(BORDA);

        panelHolderContrato.setBackground(PAINEIS);
        panelHolderContrato.setBorder(BORDA);

        panelHolderFuncionario.setBackground(PAINEIS);
        panelHolderFuncionario.setBorder(BORDA);

        panelTopClientes.setBackground(PAINEIS);
        panelBotClientes.setBackground(PAINEIS);

        panelTopImovel.setBackground(PAINEIS);
        panelBotImovel.setBackground(PAINEIS);

        panelTopContratoAtivo.setBackground(PAINEIS);
        panelTopContratoTerminado.setBackground(PAINEIS);
        panelBotContrato.setBackground(PAINEIS);

        panelTopFuncionario.setBackground(PAINEIS);
        panelBotFuncionario.setBackground(PAINEIS);

        panelBuscaCliente.setBackground(PAINEIS);
        panelBuscaImovel.setBackground(PAINEIS);
        panelBuscaContratoAtivo.setBackground(PAINEIS);
        panelBuscaContratoTerminado.setBackground(PAINEIS);
        panelBuscaFuncionario.setBackground(PAINEIS);


        tabelaClientes.setBackground(PAINEIS);
        tabelaClientes.setBorder(null);
        tabelaClientes.setForeground(TEXTO);
        tabelaImoveis.setBackground(PAINEIS);
        tabelaImoveis.setBorder(null);
        tabelaImoveis.setForeground(TEXTO);
        tabelaContratosAtivos.setBackground(PAINEIS);
        tabelaContratosAtivos.setBorder(null);
        tabelaContratosAtivos.setForeground(TEXTO);
        tabelaContratosTerminados.setBackground(PAINEIS);
        tabelaContratosTerminados.setBorder(null);
        tabelaContratosTerminados.setForeground(TEXTO);
        tabelaFuncionarios.setBackground(PAINEIS);
        tabelaFuncionarios.setBorder(null);
        tabelaFuncionarios.setForeground(TEXTO);

        scrollClientes.setBackground(PAINEIS);
        scrollClientes.setBorder(BORDA);

        scrollImovel.setBackground(PAINEIS);
        scrollImovel.setBorder(BORDA);

        scrollContratoAtivo.setBackground(PAINEIS);
        scrollContratoAtivo.setBorder(BORDA);

        scrollContratoTerminado.setBackground(PAINEIS);
        scrollContratoTerminado.setBorder(BORDA);

        scrollFuncionario.setBackground(PAINEIS);
        scrollFuncionario.setBorder(BORDA);

        scrollClientes.setBackground(PAINEIS);
        scrollClientes.setBorder(BORDA);

        buscarClientes.setBackground(PAINEIS);
        buscarClientes.setForeground(TEXTO);
        buscarClientes.setBorder(BORDA);

        buscarImoveis.setBackground(PAINEIS);
        buscarImoveis.setForeground(TEXTO);
        buscarImoveis.setBorder(BORDA);

        buscarContratosTerminados.setBackground(PAINEIS);
        buscarContratosTerminados.setForeground(TEXTO);
        buscarContratosTerminados.setBorder(BORDA);

        buscarContratosAtivos.setBackground(PAINEIS);
        buscarContratosAtivos.setForeground(TEXTO);
        buscarContratosAtivos.setBorder(BORDA);

        buscarFuncionarios.setBackground(PAINEIS);
        buscarFuncionarios.setForeground(TEXTO);
        buscarFuncionarios.setBorder(BORDA);


        lblAcesso.setForeground(TEXTO);
        lblTipoFuncionario.setForeground(TEXTO);
        lbl_ola.setForeground(TEXTO);
        lblStats.setForeground(TEXTO);

        lblClientesCadastrados.setForeground(TEXTO);
        lblImoveisCadastrados.setForeground(TEXTO);
        lblContratosVigencia.setForeground(TEXTO);
        lblContratosTerminados.setForeground(TEXTO);
        lblFuncionariosCadastrados.setForeground(TEXTO);
    }

    @Override
    public void show() {
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle(DESCRICAO + " - v" + VERSAO);
        frame.setSize(Dimensoes.DASHBOARD);
        frame.setLocation(Dimensoes.getCentroTela(frame.getWidth(), frame.getHeight()));

        try {
            frame.setIconImage(ImageIO.read(new File(ICONE_64)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

}

