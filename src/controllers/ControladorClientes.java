package controllers;

import controllers.entidades.Cliente;
import controllers.interfaces.Strings;
import model.SGIBD;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Controlador responsável pelo processamento de dados do cliente
 */
public abstract class ControladorClientes implements Strings {

    private static final SGIBD BD = SGIBD.getInstance();
    private static final ArrayList<Cliente> arrayClientes = carregarClientes();

    private static boolean clienteExiste(String cpf) {
        for (Cliente cliente : arrayClientes)
            if (cliente.getCpf().equals(cpf)) {
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CLIENTE_EXISTENTE);
                return true;
            }
        return false;
    }

    private static void salvarClientes() {
        JSONArray jsonArray = new JSONArray();
        for (Cliente cliente : arrayClientes) {
            jsonArray.put(new JSONObject(cliente.toString()));
        }
        BD.salvarRegistros(DADOS_CLIENTES, jsonArray);
    }

    private static int getProximoIdCliente() {
        int id = 0;
        for (Cliente c : arrayClientes)
            if (c.getClid() > id)
                id = c.getClid();

        return id + 1;
    }

    private static ArrayList<Cliente> carregarClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();

        for (Object object : BD.getRegistros(DADOS_CLIENTES)) {
            JSONObject item = (JSONObject) object;

            Cliente cliente = new Cliente(
                    item.getInt("clid"),
                    item.getString("nome"),
                    item.getString("cpf"),
                    item.getString("email"),
                    item.getDouble("renda")
            );
            clientes.add(cliente);
        }
        return clientes;
    }

    public static Cliente getClienteById(int id) {
        for (Cliente cliente : arrayClientes) {
            if (cliente.getClid() == id)
                return cliente;
        }
        return null;
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
                getProximoIdCliente(),
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

        if (ControladorContratos.verificarExisteRegistroNoContrato(DADOS_CLIENTES, clid)) {
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

    public static String getNumeroClientes() {
        return (arrayClientes.size() > 0 ? arrayClientes.size() + " " + Strings.CLIENTES : Strings.NENHUM_CLIENTE) + " | ";
    }
}
