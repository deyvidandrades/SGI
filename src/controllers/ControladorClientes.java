package controllers;

import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.interfaces.Strings;
import model.SGIBD;

import java.util.ArrayList;

/*
 * Controlador responsável pelo processamento de dados do cliente
 */
public abstract class ControladorClientes {

    private static final SGIBD BD = SGIBD.getInstance();
    private static final ArrayList<Cliente> arrayClientes = BD.getClientes();

    private static boolean clienteExiste(String cpf) {
        for (Cliente cliente : arrayClientes)
            if (cliente.getCpf().equals(cpf)) {
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CLIENTE_EXISTENTE);
                return true;
            }
        return false;
    }

    private static boolean verificarExisteContrato(int id) {
        for (Contrato contrato : BD.getContratos()) {
            if (contrato.getCliente().getClid() == id) {
                return true;
            }
        }
        return false;
    }

    private static void salvarClientes() {
        BD.salvarClientes(arrayClientes);
    }

    public static void adicionarCliente(String nome, String email, String cpf, String renda) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || renda.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
            return;
        }

        if (clienteExiste(cpf)) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CLIENTE_EXISTENTE);
            return;
        }

        arrayClientes.add(new Cliente(
                BD.getProximoIdCliente(),
                nome,
                cpf,
                email,
                Double.parseDouble(renda.replace(",", "."))
        ));

        salvarClientes();
    }

    public static void atualizarCliente(String nome, String email, String cpf, String renda) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || renda.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
            return;
        }

        for (Cliente cliente : arrayClientes) {
            if (cliente.getCpf().equals(cpf)) {
                cliente.setNome(nome);
                cliente.setEmail(email);
                cliente.setCpf(cpf);
                cliente.setRenda(Double.parseDouble(renda.replace(",", ".")));
            }
        }

        salvarClientes();
    }

    public static void removerCliente(int clid) {

        if (verificarExisteContrato(clid)) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CONTRATO_EM_VIGENCIA_CLIENTE);
            return;
        }

        int indice = 0;
        for (Cliente cliente : arrayClientes) {
            if (cliente.getClid() == clid)
                break;
            indice++;
        }

        arrayClientes.remove(indice);
        salvarClientes();
    }

    public static ArrayList<Cliente> getListaClientes(String busca) {
        ArrayList<Cliente> array = new ArrayList<>();

        for (Cliente cliente : arrayClientes) {
            if (busca.isEmpty())
                array.add(cliente);
            else {
                if (cliente.getNome().toUpperCase().contains(busca.toUpperCase()))
                    array.add(cliente);
            }
        }
        return array;
    }
}
