package controllers;

import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.entidades.Funcionario;
import controllers.entidades.Imovel;
import controllers.interfaces.Dimensoes;
import controllers.interfaces.Strings;
import model.SGIBD;
import views.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/*
 * Classe responsável por coordenar a exibição das interfaces gráficas
 */
public abstract class ControladorUI extends ControladorAutenticacao implements Dimensoes {
    private static final SGIBD BD = SGIBD.getInstance();

    public static Funcionario funcionarioLogado;

    public static TelaDashboard instanciaTelaDashboard;

    public static Dialog instanciaDialogoTela;

    public static void exibirTelaLogin() {
        TelaLogin login = new TelaLogin();
        login.show();
    }

    public static void exibirTelaDashboard() {
        instanciaTelaDashboard = new TelaDashboard(funcionarioLogado, BD.carregarConfiguracoes());
        instanciaTelaDashboard.show();
    }

    public static void exibirDialogoConfiguracoes() {
        instanciaDialogoTela = new DialogoConfiguracoes();
        instanciaDialogoTela.setIconImage(new ImageIcon(Strings.ICONE_32).getImage());
        instanciaDialogoTela.setSize(DIALOGO_CONFIGURACOES);
        instanciaDialogoTela.setLocation(Dimensoes.getCentroTela(instanciaDialogoTela.getWidth(), instanciaDialogoTela.getHeight()));
        instanciaDialogoTela.setVisible(true);
    }

    public static void exibirDialogoAlterarCliente(int clienteID) {
        Cliente cliente = BD.getClienteByID(clienteID);

        instanciaDialogoTela = new DialogoAlterarCliente(cliente);
        instanciaDialogoTela.setIconImage(new ImageIcon(Strings.ICONE_32).getImage());
        instanciaDialogoTela.setSize(Dimensoes.DIALOGO_ALTERAR_CLIENTE);
        instanciaDialogoTela.setLocation(Dimensoes.getCentroTela(instanciaDialogoTela.getWidth(), instanciaDialogoTela.getHeight()));
        instanciaDialogoTela.setVisible(true);
    }

    public static void exibirDialogoAlterarImoveis(int imovelID) {
        Imovel imovel = BD.getImovelByID(imovelID);
        instanciaDialogoTela = new DialogoAlterarImoveis(imovel);
        instanciaDialogoTela.setIconImage(new ImageIcon(Strings.ICONE_32).getImage());
        instanciaDialogoTela.setSize(Dimensoes.DIALOGO_ALTERAR_IMOVEL);
        instanciaDialogoTela.setLocation(Dimensoes.getCentroTela(instanciaDialogoTela.getWidth(), instanciaDialogoTela.getHeight()));
        instanciaDialogoTela.setVisible(true);
    }

    public static void exibirDialogoAlterarContratos(int contratoID) {
        Contrato contrato = BD.getContratoByID(contratoID);
        ArrayList<Imovel> arrayImoveis = contrato != null ? ControladorImoveis.getListaImoveis("") : ControladorImoveis.getListaImoveisDisponiveis();
        ArrayList<Cliente> arrayClientes = ControladorClientes.getListaClientes("");

        int posImovel = 0;
        int posCliente = 0;

        if (contrato != null) {
            for (int i = 0; i < arrayImoveis.size(); i++) {
                if (arrayImoveis.get(i).getImid() == contrato.getImovel().getImid())
                    posImovel = i;
            }
            for (int i = 0; i < arrayClientes.size(); i++) {
                if (arrayClientes.get(i).getClid() == contrato.getCliente().getClid())
                    posCliente = i;
            }
        }


        if (ControladorImoveis.imoveisDisponiveis() || contrato != null) {
            instanciaDialogoTela = new DialogoAlterarContrato(contrato, arrayImoveis, arrayClientes, posImovel, posCliente);
            instanciaDialogoTela.setIconImage(new ImageIcon(Strings.ICONE_32).getImage());
            instanciaDialogoTela.setSize(Dimensoes.DIALOGO_ALTERAR_CONTRATO);
            instanciaDialogoTela.setLocation(Dimensoes.getCentroTela(instanciaDialogoTela.getWidth(), instanciaDialogoTela.getHeight()));
            instanciaDialogoTela.setVisible(true);
        } else
            exibirDialogoMensagens(Strings.MENSAGEM_IMOVEL_INDISPONIVEIS);
    }

    public static void exibirDialogoAlterarFuncionarios(int funcionarioID) {
        Funcionario funcionario = BD.getFuncionarioByID(funcionarioID);

        instanciaDialogoTela = new DialogoAlterarFuncionario(funcionario, funcionario != null ? converterDeB64(funcionario.getSenha()) : "");
        instanciaDialogoTela.setIconImage(new ImageIcon(Strings.ICONE_32).getImage());
        instanciaDialogoTela.setSize(Dimensoes.DIALOGO_ALTERAR_FUNCIONARIO);
        instanciaDialogoTela.setLocation(Dimensoes.getCentroTela(instanciaDialogoTela.getWidth(), instanciaDialogoTela.getHeight()));
        instanciaDialogoTela.setVisible(true);
    }

    public static void exibirDialogoMensagens(String mensagemDadosInvalidos) {
        DialogoMensagem dialogoMensagem = new DialogoMensagem(mensagemDadosInvalidos);
        dialogoMensagem.setIconImage(new ImageIcon(Strings.ICONE_32).getImage());
        dialogoMensagem.setSize(Dimensoes.DIALOGO_MENSAGEM);
        dialogoMensagem.setLocation(Dimensoes.getCentroTela(dialogoMensagem.getWidth(), dialogoMensagem.getHeight()));
        dialogoMensagem.setVisible(true);
    }

    public static void mudarPosicaoMenus(int pos) {
        instanciaTelaDashboard.setMenuPosition(pos + 1);
        BD.salvarConfiguracao(pos + 1, (boolean) BD.carregarConfiguracoes().get("temaEscuro"));
    }

    public static void mudarTema(boolean temaEscuro) {
        BD.salvarConfiguracao((int) BD.carregarConfiguracoes().get("posicaoMenus"), temaEscuro);
    }

    public static Map<String, Object> getConfiguracoes() {
        return BD.carregarConfiguracoes();
    }
}
