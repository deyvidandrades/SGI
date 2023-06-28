package controllers;

import controllers.entidades.Funcionario;
import controllers.interfaces.Strings;
import model.SGIBD;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class ControladorFuncionarios extends ControladorAutenticacao implements Strings {

    private static final SGIBD BD = SGIBD.getInstance();
    private static final ArrayList<Funcionario> arrayFuncionarios = carregarFuncionarios();

    private static boolean funcionarioExiste(String cpf) {
        for (Funcionario funcionario : arrayFuncionarios)
            if (funcionario.getCpf().equals(cpf))
                return true;
        return false;
    }

    private static ArrayList<Funcionario> carregarFuncionarios() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        for (Object object : BD.getRegistros(DADOS_FUNCIONARIOS)) {
            JSONObject item = (JSONObject) object;

            Funcionario funcionario = new Funcionario(
                    item.getInt("fuid"),
                    item.getString("nome"),
                    item.getString("cpf"),
                    item.getString("email"),
                    item.getString("senha"),
                    item.getDouble("salario"),
                    item.getBoolean("gerente")
            );

            funcionarios.add(funcionario);
        }
        return funcionarios;
    }

    private static void salvarFuncionarios() {
        JSONArray jsonArray = new JSONArray();
        for (Funcionario funcionario : arrayFuncionarios) {
            jsonArray.put(new JSONObject(funcionario.toString()));
        }
        BD.salvarRegistros(DADOS_FUNCIONARIOS, jsonArray);
    }

    private static int getProximoIdFuncionario() {
        int id = 0;
        for (Funcionario f : arrayFuncionarios)
            if (f.getFuid() > id)
                id = f.getFuid();

        return id + 1;
    }

    public static Funcionario getFuncionarioById(int id) {
        for (Funcionario funcionario : arrayFuncionarios) {
            if (funcionario.getFuid() == id)
                return funcionario;
        }
        return null;

    }

    public static void adicionarFuncionario(String nome, String email, String cpf, String senha, String salario, boolean gerente) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || salario.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_DADOS_INVALIDOS);
            return;
        }

        if (funcionarioExiste(cpf)) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_FUNCIONARIO_EXISTENTE);
            return;
        }

        arrayFuncionarios.add(new Funcionario(
                getProximoIdFuncionario(),
                nome,
                cpf,
                email,
                converterParaB64(senha),
                Double.parseDouble(salario.replace(",", ".")),
                gerente
        ));

        salvarFuncionarios();
    }

    public static void atualizarFuncionario(int fuid, String nome, String email, String cpf, String senha, String salario, boolean gerente) {
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || salario.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_DADOS_INVALIDOS);
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
        return (arrayFuncionarios.size() > 0 ? arrayFuncionarios.size() + " " + FUNCIONARIOS : NENHUM_FUNCIONARIO);
    }
}
