package controllers;

import controllers.entidades.Cliente;
import controllers.interfaces.Strings;
import model.SGIBD;

import java.util.ArrayList;

/*
* Controlador responsável pelo processamento de dados do cliente
*/
public class ControladorClientes {

    public static ArrayList<Cliente> listarClientes() {
        return SGIBD.getClientes();
    }

    /*
    * Método recebe dados de cliente, verifica se são válidos e envia um novo Cliente para ser
    * adicionado a base de dados. Caso não seja adicionado exibe um dialogo de erro.
    * */
    public static void adicionarCliente(String nome, String email, String cpf, String renda) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || renda.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            boolean adicionado = SGIBD.adicionarCliente(new Cliente(
                    nome,
                    cpf,
                    email,
                    Double.parseDouble(renda.replace(",", "."))
            ));

            if (!adicionado)
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CLIENTE_EXISTENTE);
        }
    }

    /*
    * Metodo recebe os dados, Verifica se não são nulos, se forem exibe um dialogo de metodos invalidos.
    * Um Cliente é criado e enviado para ser atualizado pelo model. Se a operação falhar, exibe um
    * dialogo com uma mensagem de erro.
    * */
    public static void atualizarCliente(int clid, String nome, String email, String cpf, String renda) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || renda.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            boolean adicionado = SGIBD.atualizarCliente(new Cliente(
                    clid,
                    nome,
                    cpf,
                    email,
                    Double.parseDouble(renda.replace(",", "."))
            ));

            if (!adicionado)
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CLIENTE_EXISTENTE);
        }
    }

    public static void removerCliente(int clid) {
        boolean removido = SGIBD.removerCliente(clid);

        if (!removido)
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO_REMOCAO);
    }
}
