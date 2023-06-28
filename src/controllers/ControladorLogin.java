package controllers;

import controllers.entidades.Funcionario;

public abstract class ControladorLogin extends ControladorAutenticacao {

    public static Boolean autenticarFuncionario(String email, String senha) {
        for (Funcionario funcionario : ControladorFuncionarios.getListaFuncionarios("")) {
            if (funcionario.getEmail().equals(email) && funcionario.getSenha().equals(converterParaB64(senha))) {
                ControladorUI.funcionarioLogado = funcionario;
                return true;
            }
        }
        return false;
    }
}
