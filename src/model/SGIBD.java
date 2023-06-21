package model;

import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.entidades.Funcionario;
import controllers.entidades.Imovel;
import controllers.interfaces.Strings;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface SGIBD {
    File file = new File(Strings.DADOS);

    /*ACESSO AOS DADOS*/
    static JSONArray carregarDados(String key) {
        String content = "";

        try {
            content = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject(content).getJSONObject(Strings.DADOS_RAIZ).getJSONArray(key);
    }

    static boolean salvarDados(String key, JSONObject novoObjeto) {

        if (novoObjeto == null)
            return false;

        JSONArray arrayFuncionarios = carregarDados(Strings.DADOS_FUNCIONARIOS);
        JSONArray arrayClientes = carregarDados(Strings.DADOS_CLIENTES);
        JSONArray arrayImoveis = carregarDados(Strings.DADOS_IMOVEIS);
        JSONArray arrayContratos = carregarDados(Strings.DADOS_CONTRATOS);
        JSONArray arrayConfiguracoes = carregarDados(Strings.DADOS_CONFIGURACOES);

        switch (key) {
            case Strings.DADOS_FUNCIONARIOS -> arrayFuncionarios.put(novoObjeto);
            case Strings.DADOS_CLIENTES -> arrayClientes.put(novoObjeto);
            case Strings.DADOS_IMOVEIS -> arrayImoveis.put(novoObjeto);
            case Strings.DADOS_CONTRATOS -> arrayContratos.put(novoObjeto);
            case Strings.DADOS_CONFIGURACOES -> arrayConfiguracoes.put(novoObjeto);
        }

        HashMap<String, JSONArray> mapData = new HashMap<>();
        mapData.put(Strings.DADOS_FUNCIONARIOS, arrayFuncionarios);
        mapData.put(Strings.DADOS_CLIENTES, arrayClientes);
        mapData.put(Strings.DADOS_IMOVEIS, arrayImoveis);
        mapData.put(Strings.DADOS_CONTRATOS, arrayContratos);
        mapData.put(Strings.DADOS_CONFIGURACOES, arrayConfiguracoes);

        HashMap<String, HashMap<String, JSONArray>> map = new HashMap<>();
        map.put("dados", mapData);

        JSONObject data = new JSONObject(map);

        try {
            FileUtils.write(file, data + "\n", StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean removerDados(String key, int objectId) {

        JSONArray arrayFuncionarios = carregarDados(Strings.DADOS_FUNCIONARIOS);
        JSONArray arrayClientes = carregarDados(Strings.DADOS_CLIENTES);
        JSONArray arrayContratos = carregarDados(Strings.DADOS_CONTRATOS);
        JSONArray arrayImoveis = carregarDados(Strings.DADOS_IMOVEIS);
        JSONArray arrayConfiguracoes = carregarDados(Strings.DADOS_CONFIGURACOES);

        switch (key) {
            case Strings.DADOS_CLIENTES -> {
                if (getClienteByID(objectId) == null)
                    return false;

                arrayClientes = removerItemArray(carregarDados(Strings.DADOS_CLIENTES), "clid", objectId);
            }
            case Strings.DADOS_CONTRATOS -> {
                if (getContratoByID(objectId) == null)
                    return false;

                arrayContratos = removerItemArray(carregarDados(Strings.DADOS_CONTRATOS), "coid", objectId);
            }
            case Strings.DADOS_IMOVEIS -> {
                if (getImovelByID(objectId) == null)
                    return false;

                arrayImoveis = removerItemArray(carregarDados(Strings.DADOS_IMOVEIS), "imid", objectId);
            }
            case Strings.DADOS_FUNCIONARIOS -> {
                if (getFuncionarioByID(objectId) == null)
                    return false;

                arrayFuncionarios = removerItemArray(carregarDados(Strings.DADOS_FUNCIONARIOS), "fuid", objectId);
            }
            case Strings.DADOS_CONFIGURACOES -> arrayConfiguracoes.remove(0);
        }

        HashMap<String, JSONArray> mapData = new HashMap<>();
        mapData.put(Strings.DADOS_FUNCIONARIOS, arrayFuncionarios);
        mapData.put(Strings.DADOS_CLIENTES, arrayClientes);
        mapData.put(Strings.DADOS_IMOVEIS, arrayImoveis);
        mapData.put(Strings.DADOS_CONTRATOS, arrayContratos);
        mapData.put(Strings.DADOS_CONFIGURACOES, arrayConfiguracoes);

        HashMap<String, HashMap<String, JSONArray>> map = new HashMap<>();
        map.put("dados", mapData);

        JSONObject data = new JSONObject(map);

        try {
            FileUtils.write(file, data + "\n", StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static JSONArray removerItemArray(JSONArray array, String key, int objectId) {
        int i = 0;
        boolean encontrou = false;
        while (i < array.length() && !encontrou) {
            JSONObject funcionario = (JSONObject) array.get(i);

            if (String.valueOf(funcionario.getInt(key)).equals(String.valueOf(objectId))) {
                encontrou = true;
            } else {
                i++;
            }
        }

        array.remove(i);
        return array;
    }

    /*FLUXO DE FUNCIONARIOS*/
    static ArrayList<Funcionario> getFuncionarios() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        for (Object object : carregarDados(Strings.DADOS_FUNCIONARIOS)) {
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

    static Funcionario getFuncionarioByID(int funcionarioID) {
        for (Funcionario funcionario : getFuncionarios()) {
            if (funcionario.getFuid() == funcionarioID)
                return funcionario;
        }

        return null;
    }

    static boolean adicionarFuncionario(Funcionario novoFuncionario) {
        for (Funcionario funcionario : getFuncionarios()) {
            if (funcionario.getCpf().equals(novoFuncionario.getCpf()))
                return atualizarFuncionario(novoFuncionario);
        }

        return salvarDados(Strings.DADOS_FUNCIONARIOS, new JSONObject(novoFuncionario.toString()));
    }

    static boolean atualizarFuncionario(Funcionario novoFuncionario) {
        removerFuncionario(novoFuncionario.getFuid());
        return salvarDados(Strings.DADOS_FUNCIONARIOS, new JSONObject(novoFuncionario.toString()));
    }

    static int getProximoIdFuncionario() {
        return getFuncionarios().get(getFuncionarios().size() - 1).getFuid() + 1;
    }

    static boolean removerFuncionario(int fuid) {
        return removerDados(Strings.DADOS_FUNCIONARIOS, fuid);
    }


    /*FLUXO DE CLIENTES*/
    static ArrayList<Cliente> getClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();

        for (Object object : carregarDados(Strings.DADOS_CLIENTES)) {
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

    static int getProximoIdCliente() {
        return getClientes().get(getClientes().size() - 1).getClid() + 1;
    }

    static boolean adicionarCliente(Cliente novoCliente) {
        for (Cliente cliente : getClientes()) {
            if (cliente.getCpf().equals(novoCliente.getCpf()))
                return false;
        }

        return salvarDados(Strings.DADOS_CLIENTES, new JSONObject(novoCliente.toString()));
    }

    static boolean atualizarCliente(Cliente cliente) {
        removerCliente(cliente.getClid());
        return salvarDados(Strings.DADOS_CLIENTES, new JSONObject(cliente.toString()));
    }

    static boolean removerCliente(int clid) {
        return removerDados(Strings.DADOS_CLIENTES, clid);
    }

    static Cliente getClienteByID(int id) {
        for (Cliente cliente : getClientes()) {
            if (cliente.getClid() == id)
                return cliente;
        }

        return null;
    }

    /*FLUXO DE IMOVEIS*/
    static ArrayList<Imovel> getImoveis() {
        ArrayList<Imovel> imoveis = new ArrayList<>();

        for (Object object : carregarDados(Strings.DADOS_IMOVEIS)) {
            JSONObject item = (JSONObject) object;

            Imovel imovel = new Imovel(
                    item.getInt("imid"),
                    item.getString("nomeProprietario"),
                    item.getDouble("valorLocacao"),
                    item.getDouble("valorVenda"),
                    item.getString("endereco"),
                    item.getInt("numQuartos"),
                    item.getInt("numBanheiros"),
                    item.getBoolean("venda"),
                    item.getBoolean("disponivel")
            );

            imoveis.add(imovel);
        }

        return imoveis;

    }

    static Imovel getImovelByID(int id) {
        for (Imovel imovel : getImoveis()) {
            if (imovel.getImid() == id)
                return imovel;
        }

        return null;
    }

    static int getProximoIdImovel() {
        return getImoveis().get(getImoveis().size() - 1).getImid() + 1;
    }

    static boolean adicionarImovel(Imovel novoImovel) {
        for (Imovel imovel : getImoveis()) {
            if (imovel.getEndereco().equals(novoImovel.getEndereco()))
                return false;
        }

        return salvarDados(Strings.DADOS_IMOVEIS, new JSONObject(novoImovel.toString()));
    }

    static boolean atualizarImovel(Imovel imovel) {
        removerImovel(imovel.getImid());
        return salvarDados(Strings.DADOS_IMOVEIS, new JSONObject(imovel.toString()));
    }

    static boolean removerImovel(int imid) {
        return removerDados(Strings.DADOS_IMOVEIS, imid);
    }

    /*FLUXO DE CONTRATOS*/
    static ArrayList<Contrato> getContratos() {
        ArrayList<Contrato> contratos = new ArrayList<>();

        for (Object object : carregarDados(Strings.DADOS_CONTRATOS)) {
            JSONObject item = (JSONObject) object;

            Contrato contrato = new Contrato(
                    item.getInt("coid"),
                    getClienteByID(item.getInt("clid")),
                    getImovelByID(item.getInt("imid")),
                    item.getLong("dataInicio"),
                    item.getLong("dataFim"),
                    item.getBoolean("tipo")
            );

            contratos.add(contrato);
        }

        return contratos;
    }

    static Contrato getContratoByID(int id) {
        for (Contrato contrato : getContratos()) {
            if (contrato.getCoid() == id)
                return contrato;
        }

        return null;
    }

    static int getProximoIdContrato() {
        return getContratos().get(getContratos().size() - 1).getCoid() + 1;
    }

    static boolean adicionarContrato(Contrato novoContrato) {
        for (Contrato contrato : getContratos()) {
            if (contrato.equals(novoContrato))
                return false;
        }

        return salvarDados(Strings.DADOS_CONTRATOS, new JSONObject(novoContrato.toString()));
    }

    static boolean atualizarContrato(Contrato contrato) {
        removerContrato(contrato.getCoid());
        return salvarDados(Strings.DADOS_CONTRATOS, new JSONObject(contrato.toString()));
    }

    static boolean removerContrato(int coid) {
        return removerDados(Strings.DADOS_CONTRATOS, coid);
    }


    /*FLUXO CONFIGURACOES*/

    static Map<String, Object> carregarConfiguracoes() {
        JSONArray jsonArray = carregarDados(Strings.DADOS_CONFIGURACOES);
        return ((JSONObject) jsonArray.get(0)).toMap();
    }

    static void salvarConfiguracao(int posicaoMenus, boolean temaEscuro) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("posicaoMenus", posicaoMenus);
        jsonObject.put("temaEscuro", temaEscuro);

        removerDados(Strings.DADOS_CONFIGURACOES, -1);

        salvarDados(Strings.DADOS_CONFIGURACOES, jsonObject);
    }
}
