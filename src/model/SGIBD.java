package model;

import controllers.interfaces.Strings;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class SGIBD implements Strings {

    private static final File file = new File(DADOS);
    private static SGIBD INSTANCIA;

    private SGIBD() {
    }

    /*Instancia singleton*/
    public static synchronized SGIBD getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new SGIBD();
        }

        return INSTANCIA;
    }

    /*ACESSO AO ARQUIVO*/
    private JSONArray carregarDados(String key) {
        String content = "";

        try {
            content = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject(content).getJSONObject(DADOS_RAIZ).getJSONArray(key);
    }

    private void salvarDados(String key, JSONArray array) {
        JSONArray arrayFuncionarios = carregarDados(DADOS_FUNCIONARIOS);
        JSONArray arrayClientes = carregarDados(DADOS_CLIENTES);
        JSONArray arrayImoveis = carregarDados(DADOS_IMOVEIS);
        JSONArray arrayContratos = carregarDados(DADOS_CONTRATOS);
        JSONArray arrayConfiguracoes = carregarDados(DADOS_CONFIGURACOES);

        switch (key) {
            case DADOS_FUNCIONARIOS -> arrayFuncionarios = array;
            case DADOS_CLIENTES -> arrayClientes = array;
            case DADOS_IMOVEIS -> arrayImoveis = array;
            case DADOS_CONTRATOS -> arrayContratos = array;
            case DADOS_CONFIGURACOES -> arrayConfiguracoes = array;
        }

        HashMap<String, JSONArray> mapData = new HashMap<>();
        mapData.put(DADOS_FUNCIONARIOS, arrayFuncionarios);
        mapData.put(DADOS_CLIENTES, arrayClientes);
        mapData.put(DADOS_IMOVEIS, arrayImoveis);
        mapData.put(DADOS_CONTRATOS, arrayContratos);
        mapData.put(DADOS_CONFIGURACOES, arrayConfiguracoes);

        HashMap<String, HashMap<String, JSONArray>> map = new HashMap<>();
        map.put("dados", mapData);

        JSONObject data = new JSONObject(map);

        try {
            FileUtils.write(file, data + "\n", StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
    }

    /*FLUXO DADOS*/
    public void salvarRegistros(String key, JSONArray array) {
        salvarDados(key, array);
    }

    public JSONArray getRegistros(String key) {
        return carregarDados(key);
    }

    /*FLUXO CONFIGURACOES*/
    public Map<String, Object> carregarConfiguracoes() {
        JSONArray jsonArray = carregarDados(DADOS_CONFIGURACOES);
        return ((JSONObject) jsonArray.get(0)).toMap();
    }

    public void salvarConfiguracao(Map<String, Object> configuracoes) {
        JSONObject jsonObject = new JSONObject(configuracoes);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        salvarDados(DADOS_CONFIGURACOES, jsonArray);
    }
}
