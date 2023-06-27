package controllers;

import controllers.entidades.Funcionario;
import controllers.interfaces.Strings;
import model.SGIBD;

import java.util.ArrayList;

public abstract class ControladorFuncionarios extends ControladorAutenticacao {

    private static final SGIBD BD = SGIBD.getInstance();
    private static final ArrayList<Funcionario> arrayFuncionarios = BD.getFuncionarios();

    private static boolean funcionarioExiste(String cpf) {
        for (Funcionario funcionario : arrayFuncionarios)
            if (funcionario.getCpf().equals(cpf))
                return true;
        return false;
    }

    private static void salvarFuncionarios() {
        BD.salvarFuncionarios(arrayFuncionarios);
    }

    public static void adicionarFuncionario(String nome, String email, String cpf, String senha, String salario, boolean gerente) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || salario.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
            return;
        }

        if (funcionarioExiste(cpf)) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_FUNCIONARIO_EXISTENTE);
            return;
        }

        arrayFuncionarios.add(new Funcionario(
                BD.getProximoIdFuncionario(),
                nome,
                cpf,
                email,
                senha,
                Double.parseDouble(salario.replace(",", ".")),
                gerente
        ));

        salvarFuncionarios();
    }

    public static void atualizarFuncionario(int fuid, String nome, String email, String cpf, String senha, String salario, boolean gerente) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || salario.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
            return;
        }

        for (Funcionario funcionario : arrayFuncionarios) {
            if (funcionario.getFuid() == fuid) {
                funcionario.setNome(nome);
                funcionario.setCpf(cpf);
                funcionario.setEmail(email);
                funcionario.setSenha(converterParaB64(senha));
                funcionario.setSalario(Double.parseDouble(salario.replace(",", ".")));
                funcionario.setGerente(gerente);
            }
        }

        salvarFuncionarios();
    }

    public static void removerFuncionario(int fuid) {

        int indice = 0;
        for (Funcionario funcionario : arrayFuncionarios) {
            if (funcionario.getFuid() == fuid)
                break;
            indice++;
        }

        arrayFuncionarios.remove(indice);
        salvarFuncionarios();
    }

    public static ArrayList<Funcionario> getListaFuncionarios(String busca) {
        ArrayList<Funcionario> array = new ArrayList<>();

        for (Funcionario funcionario : arrayFuncionarios) {
            if (busca.isEmpty())
                array.add(funcionario);
            else {
                if (funcionario.getNome().toUpperCase().contains(busca.toUpperCase()))
                    array.add(funcionario);
            }
        }
        return array;
    }

    public static String getNumeroFuncionarios() {
        return (arrayFuncionarios.size() > 0 ? arrayFuncionarios.size() + " " + Strings.FUNCIONARIOS : Strings.NENHUM_FUNCIONARIO);
    }
}
