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

public final class SGIBD {

    private static final File file = new File(Strings.DADOS);
    private static SGIBD INSTANCIA;

    private SGIBD() {
    }

    public static synchronized SGIBD getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new SGIBD();
        }

        return INSTANCIA;
    }

    /*ACESSO AOS DADOS*/
    private JSONArray carregarDados(String key) {
        String content = "";

        try {
            content = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject(content).getJSONObject(Strings.DADOS_RAIZ).getJSONArray(key);
    }

    private void salvarDados(String key, JSONArray array) {
        JSONArray arrayFuncionarios = carregarDados(Strings.DADOS_FUNCIONARIOS);
        JSONArray arrayClientes = carregarDados(Strings.DADOS_CLIENTES);
        JSONArray arrayImoveis = carregarDados(Strings.DADOS_IMOVEIS);
        JSONArray arrayContratos = carregarDados(Strings.DADOS_CONTRATOS);
        JSONArray arrayConfiguracoes = carregarDados(Strings.DADOS_CONFIGURACOES);

        switch (key) {
            case Strings.DADOS_FUNCIONARIOS -> arrayFuncionarios = array;
            case Strings.DADOS_CLIENTES -> arrayClientes = array;
            case Strings.DADOS_IMOVEIS -> arrayImoveis = array;
            case Strings.DADOS_CONTRATOS -> arrayContratos = array;
            case Strings.DADOS_CONFIGURACOES -> arrayConfiguracoes = array;
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
        } catch (IOException ignored) {
        }
    }

    /*FLUXO DE FUNCIONARIOS*/
    public void salvarFuncionarios(ArrayList<Funcionario> funcionarios) {
        JSONArray jsonArray = new JSONArray();
        for (Funcionario funcionario : funcionarios) {
            jsonArray.put(new JSONObject(funcionario.toString()));
        }

        salvarDados(Strings.DADOS_FUNCIONARIOS, jsonArray);
    }

    public ArrayList<Funcionario> getFuncionarios() {
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

    public Funcionario getFuncionarioByID(int funcionarioID) {
        for (Funcionario funcionario : getFuncionarios()) {
            if (funcionario.getFuid() == funcionarioID)
                return funcionario;
        }

        return null;
    }

    public int getProximoIdFuncionario() {
        int id = 0;
        for (Funcionario f : getFuncionarios())
            if (f.getFuid() > id)
                id = f.getFuid();

        return id + 1;
    }


    /*FLUXO DE CLIENTES*/
    public void salvarClientes(ArrayList<Cliente> clientes) {
        JSONArray jsonArray = new JSONArray();
        for (Cliente cliente : clientes) {
            jsonArray.put(new JSONObject(cliente.toString()));
        }

        salvarDados(Strings.DADOS_CLIENTES, jsonArray);
    }

    public ArrayList<Cliente> getClientes() {
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

    public int getProximoIdCliente() {
        int id = 0;
        for (Cliente c : getClientes())
            if (c.getClid() > id)
                id = c.getClid();

        return id + 1;
    }

    public Cliente getClienteByID(int id) {
        for (Cliente cliente : getClientes()) {
            if (cliente.getClid() == id)
                return cliente;
        }

        return null;
    }

    /*FLUXO DE IMOVEIS*/
    public void salvarImoveis(ArrayList<Imovel> imoveis) {
        JSONArray jsonArray = new JSONArray();
        for (Imovel imovel : imoveis) {
            jsonArray.put(new JSONObject(imovel.toString()));
        }

        salvarDados(Strings.DADOS_IMOVEIS, jsonArray);
    }

    public ArrayList<Imovel> getImoveis() {
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

    public Imovel getImovelByID(int id) {
        for (Imovel imovel : getImoveis()) {
            if (imovel.getImid() == id)
                return imovel;
        }

        return null;
    }

    public int getProximoIdImovel() {
        int id = 0;
        for (Imovel i : getImoveis())
            if (i.getImid() > id)
                id = i.getImid();

        return id + 1;
    }


    /*FLUXO DE CONTRATOS*/
    public void salvarContratos(ArrayList<Contrato> contratos) {
        JSONArray jsonArray = new JSONArray();
        for (Contrato contrato : contratos) {
            jsonArray.put(new JSONObject(contrato.toString()));
        }

        salvarDados(Strings.DADOS_CONTRATOS, jsonArray);
    }

    public ArrayList<Contrato> getContratos() {
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

    public Contrato getContratoByID(int id) {
        for (Contrato contrato : getContratos()) {
            if (contrato.getCoid() == id)
                return contrato;
        }

        return null;
    }

    public int getProximoIdContrato() {
        int id = 0;
        for (Contrato c : getContratos())
            if (c.getCoid() > id)
                id = c.getCoid();

        return id + 1;
    }


    /*FLUXO CONFIGURACOES*/

    public Map<String, Object> carregarConfiguracoes() {
        JSONArray jsonArray = carregarDados(Strings.DADOS_CONFIGURACOES);
        return ((JSONObject) jsonArray.get(0)).toMap();
    }

    public void salvarConfiguracao(int posicaoMenus, boolean temaEscuro) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("posicaoMenus", posicaoMenus);
        jsonObject.put("temaEscuro", temaEscuro);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        salvarDados(Strings.DADOS_CONFIGURACOES, jsonArray);
    }
}
