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
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TelaDashboard implements FrameInterface {

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


        lbl_ola.setText(Strings.OLA + funcionarioLogado.getNome().split(" ")[0] + "," + Strings.BEM_VINDO);

        lblTipoFuncionario.setText(funcionarioLogado.isGerente() ? Strings.GERENTE : Strings.FUNCIONARIO);


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


        btnSettings.addActionListener(e -> ControladorUI.exibirDialogoConfiguracoes());
        btnSair.addActionListener(e -> ControladorUI.exibirTelaLogin());
        btnAtualizar.addActionListener(e -> atualizarDadosTabelas());

        inicializarTabelas();
        configurarBusca();
        atualizarDadosTabelas();

        btnCadastrarCliente.addActionListener(e -> ControladorUI.exibirDialogoAlterarCliente(-1));
        btnCadastrarImovel.addActionListener(e -> ControladorUI.exibirDialogoAlterarImoveis(-1));
        btnCadastrarContrato.addActionListener(e -> ControladorUI.exibirDialogoAlterarContratos(-1));
        btnCadastrarFuncionario.addActionListener(e -> ControladorUI.exibirDialogoAlterarFuncionarios(-1));
    }

    private void configurarCores() {
        panelBarraMenu.setBackground(Cores.PAINEIS);
        panelBarraMenu.setBorder(Cores.BORDA);


        panelStatus.setBackground(Cores.PAINEIS);
        panelBenvindo.setBackground(Cores.PAINEIS);
        panelMenus.setBackground(Cores.PAINEIS);

        tabbedMenus.setBackground(Cores.PAINEIS);
        tabbedMenus.setForeground(Cores.TEXTO);
        tabbedMenus.setBorder(Cores.BORDA);
        panel1.setBackground(Cores.PAINEIS);
        panel1.setBorder(Cores.BORDA);

        panelBgCliente.setBackground(Cores.BACKGROUND);
        panelBgCliente.setBorder(null);
        panelBgImovel.setBackground(Cores.BACKGROUND);
        panelBgImovel.setBorder(null);
        panelBgContrato.setBackground(Cores.BACKGROUND);
        panelBgContrato.setBorder(null);
        panelBgFuncionario.setBackground(Cores.BACKGROUND);
        panelBgFuncionario.setBorder(null);

        panelHolderCliente.setBackground(Cores.PAINEIS);
        panelHolderCliente.setBorder(Cores.BORDA);

        panelHolderImovel.setBackground(Cores.PAINEIS);
        panelHolderImovel.setBorder(Cores.BORDA);

        panelHolderContrato.setBackground(Cores.PAINEIS);
        panelHolderContrato.setBorder(Cores.BORDA);

        panelHolderFuncionario.setBackground(Cores.PAINEIS);
        panelHolderFuncionario.setBorder(Cores.BORDA);

        panelTopClientes.setBackground(Cores.PAINEIS);
        panelBotClientes.setBackground(Cores.PAINEIS);

        panelTopImovel.setBackground(Cores.PAINEIS);
        panelBotImovel.setBackground(Cores.PAINEIS);

        panelTopContratoAtivo.setBackground(Cores.PAINEIS);
        panelTopContratoTerminado.setBackground(Cores.PAINEIS);
        panelBotContrato.setBackground(Cores.PAINEIS);

        panelTopFuncionario.setBackground(Cores.PAINEIS);
        panelBotFuncionario.setBackground(Cores.PAINEIS);

        panelBuscaCliente.setBackground(Cores.PAINEIS);
        panelBuscaImovel.setBackground(Cores.PAINEIS);
        panelBuscaContratoAtivo.setBackground(Cores.PAINEIS);
        panelBuscaContratoTerminado.setBackground(Cores.PAINEIS);
        panelBuscaFuncionario.setBackground(Cores.PAINEIS);


        tabelaClientes.setBackground(Cores.PAINEIS);
        tabelaClientes.setBorder(null);
        tabelaClientes.setForeground(Cores.TEXTO);
        tabelaImoveis.setBackground(Cores.PAINEIS);
        tabelaImoveis.setBorder(null);
        tabelaImoveis.setForeground(Cores.TEXTO);
        tabelaContratosAtivos.setBackground(Cores.PAINEIS);
        tabelaContratosAtivos.setBorder(null);
        tabelaContratosAtivos.setForeground(Cores.TEXTO);
        tabelaContratosTerminados.setBackground(Cores.PAINEIS);
        tabelaContratosTerminados.setBorder(null);
        tabelaContratosTerminados.setForeground(Cores.TEXTO);
        tabelaFuncionarios.setBackground(Cores.PAINEIS);
        tabelaFuncionarios.setBorder(null);
        tabelaFuncionarios.setForeground(Cores.TEXTO);

        scrollClientes.setBackground(Cores.PAINEIS);
        scrollClientes.setBorder(Cores.BORDA);

        scrollImovel.setBackground(Cores.PAINEIS);
        scrollImovel.setBorder(Cores.BORDA);

        scrollContratoAtivo.setBackground(Cores.PAINEIS);
        scrollContratoAtivo.setBorder(Cores.BORDA);

        scrollContratoTerminado.setBackground(Cores.PAINEIS);
        scrollContratoTerminado.setBorder(Cores.BORDA);

        scrollFuncionario.setBackground(Cores.PAINEIS);
        scrollFuncionario.setBorder(Cores.BORDA);

        scrollClientes.setBackground(Cores.PAINEIS);
        scrollClientes.setBorder(Cores.BORDA);

        buscarClientes.setBackground(Cores.PAINEIS);
        buscarClientes.setForeground(Cores.TEXTO);
        buscarClientes.setBorder(Cores.BORDA);

        buscarImoveis.setBackground(Cores.PAINEIS);
        buscarImoveis.setForeground(Cores.TEXTO);
        buscarImoveis.setBorder(Cores.BORDA);

        buscarContratosTerminados.setBackground(Cores.PAINEIS);
        buscarContratosTerminados.setForeground(Cores.TEXTO);
        buscarContratosTerminados.setBorder(Cores.BORDA);

        buscarContratosAtivos.setBackground(Cores.PAINEIS);
        buscarContratosAtivos.setForeground(Cores.TEXTO);
        buscarContratosAtivos.setBorder(Cores.BORDA);

        buscarFuncionarios.setBackground(Cores.PAINEIS);
        buscarFuncionarios.setForeground(Cores.TEXTO);
        buscarFuncionarios.setBorder(Cores.BORDA);



        lblAcesso.setForeground(Cores.TEXTO);
        lblTipoFuncionario.setForeground(Cores.TEXTO);
        lbl_ola.setForeground(Cores.TEXTO);
        lblStats.setForeground(Cores.TEXTO);

        lblClientesCadastrados.setForeground(Cores.TEXTO);
        lblImoveisCadastrados.setForeground(Cores.TEXTO);
        lblContratosVigencia.setForeground(Cores.TEXTO);
        lblContratosTerminados.setForeground(Cores.TEXTO);
        lblFuncionariosCadastrados.setForeground(Cores.TEXTO);
    }

    public void atualizarDadosTabelas() {
        configurarTabelaClientes();
        configurarTabelaImoveis();
        configurarTabelaContratosAtivos();
        configurarTabelaContratosTerminados();
        configurarTabelaFuncionarios();

        lblStats.setText(
                ControladorClientes.getNumeroClientes() +
                        ControladorContratos.getNumeroContratos(true) +
                        ControladorContratos.getNumeroContratos(false) +
                        ControladorImoveis.getNumeroImoveis() +
                        ControladorFuncionarios.getNumeroFuncionarios()
        );
    }

    private void inicializarTabelas() {

        tabelaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabelaClientes.rowAtPoint(evt.getPoint());
                int col = tabelaClientes.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    ControladorUI.exibirDialogoAlterarCliente(Integer.parseInt(tabelaClientes.getValueAt(row, 0).toString()));
                }
            }
        });


        tabelaImoveis.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabelaImoveis.rowAtPoint(evt.getPoint());
                int col = tabelaImoveis.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    ControladorUI.exibirDialogoAlterarImoveis(Integer.parseInt(tabelaImoveis.getValueAt(row, 0).toString()));
                }
            }
        });


        tabelaContratosAtivos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabelaContratosAtivos.rowAtPoint(evt.getPoint());
                int col = tabelaContratosAtivos.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    ControladorUI.exibirDialogoAlterarContratos(Integer.parseInt(tabelaContratosAtivos.getValueAt(row, 0).toString()));
                }
            }
        });

        tabelaContratosTerminados.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabelaContratosTerminados.rowAtPoint(evt.getPoint());
                int col = tabelaContratosTerminados.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    ControladorUI.exibirDialogoAlterarContratos(Integer.parseInt(tabelaContratosTerminados.getValueAt(row, 0).toString()));
                }
            }
        });

        tabelaFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabelaFuncionarios.rowAtPoint(evt.getPoint());
                int col = tabelaFuncionarios.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    ControladorUI.exibirDialogoAlterarFuncionarios(Integer.parseInt(tabelaFuncionarios.getValueAt(row, 0).toString()));
                }
            }
        });
    }

    private void configurarBusca() {
        buscarClientes.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                configurarTabelaClientes();
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
                configurarTabelaImoveis();
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
                configurarTabelaContratosAtivos();
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
                configurarTabelaContratosTerminados();
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
                configurarTabelaFuncionarios();
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    private void configurarTabelaClientes() {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };

        for (Object name : Strings.COLUNAS_CLIENTES) {
            model.addColumn(name);
        }

        for (Cliente cliente : ControladorClientes.getListaClientes(buscarClientes.getText()))
            model.addRow(cliente.toObject());

        tabelaClientes.setModel(model);


        tabelaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaClientes.getColumnModel().getColumn(0).setWidth(0);
    }

    private void configurarTabelaImoveis() {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };

        for (Object name : Strings.COLUNAS_IMOVEIS) {
            model.addColumn(name);
        }

        for (Imovel imovel : ControladorImoveis.getListaImoveis(buscarImoveis.getText()))
            model.addRow(imovel.toObject());

        tabelaImoveis.setModel(model);

        tabelaImoveis.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaImoveis.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaImoveis.getColumnModel().getColumn(0).setWidth(0);
    }

    private void configurarTabelaContratosAtivos() {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };

        for (Object name : Strings.COLUNAS_CONTRATOS) {
            model.addColumn(name);
        }


        for (Contrato contrato : ControladorContratos.getListaContratosAtivos(buscarContratosAtivos.getText()))
            if (!contrato.isTerminado())
                model.addRow(contrato.toObject());


        tabelaContratosAtivos.setModel(model);

        tabelaContratosAtivos.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaContratosAtivos.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaContratosAtivos.getColumnModel().getColumn(0).setWidth(0);
    }

    private void configurarTabelaContratosTerminados() {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };

        for (Object name : Strings.COLUNAS_CONTRATOS) {
            model.addColumn(name);
        }


        for (Contrato contrato : ControladorContratos.getListaContratosTerminados(buscarContratosTerminados.getText()))
            if (contrato.isTerminado())
                model.addRow(contrato.toObject());


        tabelaContratosTerminados.setModel(model);

        tabelaContratosTerminados.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaContratosTerminados.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaContratosTerminados.getColumnModel().getColumn(0).setWidth(0);
    }

    private void configurarTabelaFuncionarios() {
        System.out.println(buscarFuncionarios.getText().toUpperCase());
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };

        for (Object name : Strings.COLUNAS_FUNCIONARIOS) {
            model.addColumn(name);
        }

        for (Funcionario funcionario : ControladorFuncionarios.getListaFuncionarios(buscarFuncionarios.getText()))
            model.addRow(funcionario.toObject());

        tabelaFuncionarios.setModel(model);

        tabelaFuncionarios.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaFuncionarios.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaFuncionarios.getColumnModel().getColumn(0).setWidth(0);
    }

    public void setMenuPosition(int selectedIndex) {
        tabbedMenus.setTabPlacement(selectedIndex);
    }

    @Override
    public void show() {
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle(Strings.DESCRICAO + " - v" + Strings.VERSAO);
        frame.setSize(Dimensoes.DASHBOARD);
        frame.setLocation(Dimensoes.getCentroTela(frame.getWidth(), frame.getHeight()));

        try {
            frame.setIconImage(ImageIO.read(new File(Strings.ICONE_64)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

}

