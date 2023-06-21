package controllers;

import controllers.entidades.Funcionario;
import controllers.interfaces.Strings;
import model.SGIBD;

import java.util.ArrayList;

public class ControladorFuncionarios {


    public static ArrayList<Funcionario> listarFuncionarios() {
        return SGIBD.getFuncionarios();
    }

    public static void adicionarFuncionario(String nome, String email, String cpf, String senha, String salario, boolean gerente) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || salario.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            boolean adicionado = SGIBD.adicionarFuncionario(new Funcionario(
                    nome,
                    cpf,
                    email,
                    senha,
                    Double.parseDouble(salario.replace(",",".")),
                    gerente
            ));

            if (!adicionado)
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_FUNCIONARIO_EXISTENTE);
        }
    }
    public static void atualizarFuncionario(int fuid, String nome, String email, String cpf, String senha, String salario, boolean gerente) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || salario.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            boolean adicionado = SGIBD.atualizarFuncionario(new Funcionario(
                    fuid,
                    nome,
                    cpf,
                    email,
                    senha,
                    Double.parseDouble(salario),
                    gerente
            ));

            if (!adicionado)
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_FUNCIONARIO_NAO_ATUALIZDO);
        }
    }
    public static void removerFuncionario(int fuid) {
        if (fuid == 0)
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO_REMOCAO_FUNCIONARIO_BASE);
        else {
            boolean removido = SGIBD.removerFuncionario(fuid);

            if (!removido)
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO_REMOCAO);
        }
    }

}
