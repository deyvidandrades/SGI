package views;

import controllers.*;
import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.entidades.Funcionario;
import controllers.entidades.Imovel;
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
    private JTextField buscarContratos;
    private JTable tabelaContratos;
    private JButton btnCadastrarContrato;
    private JButton btnSettings;

    private JLabel lblTipoFuncionario;
    private JLabel lblStats;
    private JLabel imgClientesBuscar;
    private JLabel imgContratosBuscar;
    private JLabel imgImoveisBuscar;
    private JButton btnAtualizar;
    private JTextField buscarFuncionarios;
    private JTable tabelaFuncionarios;
    private JButton btnCadastrarFuncionario;
    private JLabel imgFuncionariosBuscar;
    private JTabbedPane tabbedMenus;

    public TelaDashboard(Funcionario funcionarioLogado, Map<String, Object> configuracoes) {
        super();
        img.setIcon(icone_32);

        btnSair.setIcon(icone_sair);
        btnSettings.setIcon(icone_configuracoes);
        btnAtualizar.setIcon(icone_atualizar);

        btnCadastrarCliente.setIcon(icone_add);
        btnCadastrarContrato.setIcon(icone_add);
        btnCadastrarImovel.setIcon(icone_add);
        btnCadastrarFuncionario.setIcon(icone_add);

        imgClientesBuscar.setIcon(icone_buscar);
        imgContratosBuscar.setIcon(icone_buscar);
        imgImoveisBuscar.setIcon(icone_buscar);
        imgFuncionariosBuscar.setIcon(icone_buscar);

        tabbedMenus.setTabPlacement((int) configuracoes.get("posicaoMenus"));


        lbl_ola.setText(Strings.OLA + funcionarioLogado.getNome().split(" ")[0] + "," + Strings.BEM_VINDO);

        lblTipoFuncionario.setText(funcionarioLogado.isGerente() ? Strings.GERENTE : Strings.FUNCIONARIO);
        lblStats.setText(
                ControladorClientes.listarClientes().size() + Strings.CLIENTES + " | " +
                        ControladorContratos.listarContratos().size() + Strings.CONTRATOS + " | " +
                        ControladorImoveis.listarImoveis().size() + Strings.IMOVEIS
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

    public void atualizarDadosTabelas() {
        configurarTabelaClientes(false);
        configurarTabelaImoveis(false);
        configurarTabelaContratos(false);
        configurarTabelaFuncionarios(false);
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


        tabelaContratos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabelaContratos.rowAtPoint(evt.getPoint());
                int col = tabelaContratos.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    ControladorUI.exibirDialogoAlterarContratos(Integer.parseInt(tabelaContratos.getValueAt(row, 0).toString()));
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
                configurarTabelaClientes(true);
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
                configurarTabelaImoveis(true);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        buscarContratos.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                configurarTabelaContratos(true);
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
                configurarTabelaFuncionarios(true);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    private void configurarTabelaClientes(boolean busca) {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };

        for (Object name : Strings.COLUNAS_CLIENTES) {
            model.addColumn(name);
        }

        for (Cliente cliente : ControladorClientes.listarClientes()) {
            if (busca && !buscarClientes.getText().isEmpty()) {
                if (cliente.getNome().toUpperCase().contains(buscarClientes.getText().toUpperCase())) {
                    model.addRow(cliente.toObject());
                }
            } else {
                model.addRow(cliente.toObject());
            }
        }

        tabelaClientes.setModel(model);


        tabelaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaClientes.getColumnModel().getColumn(0).setWidth(0);
    }

    private void configurarTabelaImoveis(boolean busca) {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };

        for (Object name : Strings.COLUNAS_IMOVEIS) {
            model.addColumn(name);
        }

        for (Imovel imovel : ControladorImoveis.listarImoveis()) {
            if (busca && !buscarImoveis.getText().isEmpty()) {
                if (imovel.getEndereco().toUpperCase().contains(buscarImoveis.getText().toUpperCase())) {
                    model.addRow(imovel.toObject());
                }
            } else {
                model.addRow(imovel.toObject());
            }
        }

        tabelaImoveis.setModel(model);

        tabelaImoveis.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaImoveis.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaImoveis.getColumnModel().getColumn(0).setWidth(0);
    }

    private void configurarTabelaContratos(boolean busca) {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };

        for (Object name : Strings.COLUNAS_CONTRATOS) {
            model.addColumn(name);
        }

        for (Contrato contrato : ControladorContratos.listarContratos()) {
            if (busca && !buscarContratos.getText().isEmpty()) {
                if (contrato.getImovel().getEndereco().toUpperCase().contains(buscarContratos.getText().toUpperCase())) {
                    model.addRow(contrato.toObject());
                }
            } else {
                model.addRow(contrato.toObject());
            }
        }

        tabelaContratos.setModel(model);

        tabelaContratos.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaContratos.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaContratos.getColumnModel().getColumn(0).setWidth(0);
    }

    private void configurarTabelaFuncionarios(boolean busca) {
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

        for (Funcionario funcionario : ControladorFuncionarios.listarFuncionarios()) {
            if (busca && !buscarFuncionarios.getText().isEmpty()) {
                if (funcionario.getNome().toUpperCase().contains(buscarFuncionarios.getText().toUpperCase())) {
                    model.addRow(funcionario.toObject());
                }
            } else {
                model.addRow(funcionario.toObject());
            }
        }

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

