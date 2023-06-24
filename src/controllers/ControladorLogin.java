package controllers;

import controllers.entidades.Funcionario;
import model.SGIBD;

public class ControladorLogin {
    private static final SGIBD BD = SGIBD.getInstance();

    public static Boolean autenticarFuncionario(String email, String senha) {
        for (Funcionario funcionario : BD.getFuncionarios()) {
            if (funcionario.getEmail().equals(email) && funcionario.getSenha().equals(senha)) {
                ControladorUI.funcionarioLogado = funcionario;
                return true;
            }
        }
        return false;
    }
}
