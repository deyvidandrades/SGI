package controllers;

import controllers.entidades.Imovel;
import controllers.interfaces.Strings;
import model.SGIBD;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class ControladorImoveis implements Strings {

    private static final SGIBD BD = SGIBD.getInstance();
    private static final ArrayList<Imovel> arrayImoveis = carregarImoveis();

    private static boolean imovelExiste(String endereco) {
        for (Imovel imovel : arrayImoveis)
            if (imovel.getEndereco().equals(endereco))
                return true;
        return false;
    }

    private static ArrayList<Imovel> carregarImoveis() {
        ArrayList<Imovel> imoveis = new ArrayList<>();

        for (Object object : BD.getRegistros(DADOS_IMOVEIS)) {
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

    private static void salvarImoveis() {
        JSONArray jsonArray = new JSONArray();
        for (Imovel imovel : arrayImoveis) {
            jsonArray.put(new JSONObject(imovel.toString()));
        }

        BD.salvarRegistros(DADOS_IMOVEIS, jsonArray);
    }

    private static int getProximoIdImovel() {
        int id = 0;
        for (Imovel i : arrayImoveis)
            if (i.getImid() > id)
                id = i.getImid();

        return id + 1;
    }

    public static Imovel getImovelById(int id) {
        for (Imovel imovel : arrayImoveis) {
            if (imovel.getImid() == id)
                return imovel;
        }
        return null;

    }

    public static void adicionarImovel(String proprietario, String endereco, String valorLocacao, String valorVenda, String numeroQuartos, String numeroBanheiros, boolean locacao) {
        if (proprietario.isEmpty() || endereco.isEmpty() || valorLocacao.isEmpty() || valorVenda.isEmpty() || numeroQuartos.isEmpty() || numeroBanheiros.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_DADOS_INVALIDOS);
            return;
        }

        if (imovelExiste(endereco)) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_IMOVEL_EXISTENTE);
            return;
        }

        arrayImoveis.add(new Imovel(
                getProximoIdImovel(),
                proprietario,
                Double.parseDouble(valorLocacao.replace(",", ".")),
                Double.parseDouble(valorVenda.replace(",", ".")),
                endereco,
                Integer.parseInt(numeroQuartos),
                Integer.parseInt(numeroBanheiros),
                locacao,
                true
        ));

        salvarImoveis();
    }

    public static void atualizarImovel(int imid, String proprietario, String endereco, String valorLocacao, String valorVenda, String numeroQuartos, String numeroBanheiros, boolean locacao) {
        if (proprietario.isEmpty() || endereco.isEmpty() || valorLocacao.isEmpty() || valorVenda.isEmpty() || numeroQuartos.isEmpty() || numeroBanheiros.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_DADOS_INVALIDOS);
            return;
        }

        for (Imovel imovel : arrayImoveis) {
            if (imovel.getImid() == imid) {
                imovel.setNomeProprietario(proprietario);
                imovel.setEndereco(endereco);
                imovel.setValorLocacao(Double.parseDouble(valorLocacao.replace(",", ".")));
                imovel.setValorVenda(Double.parseDouble(valorVenda.replace(",", ".")));
                imovel.setNumQuartos(Integer.parseInt(numeroQuartos));
                imovel.setNumBanheiros(Integer.parseInt(numeroBanheiros));
                imovel.setVenda(locacao);
            }
        }

        salvarImoveis();
    }

    public static void removerImovel(int imid) {

        if (ControladorContratos.verificarExisteRegistroNoContrato(DADOS_IMOVEIS, imid)) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_CONTRATO_EM_VIGENCIA_IMOVEL);
            return;
        }

        int indice = 0;
        for (Imovel imovel : arrayImoveis) {
            if (imovel.getImid() == imid)
                break;
            indice++;
        }

        arrayImoveis.remove(indice);
        salvarImoveis();
    }

    public static ArrayList<Imovel> getListaImoveis(String busca) {
        ArrayList<Imovel> array = new ArrayList<>();

        for (Imovel imovel : arrayImoveis) {
            if (busca.isEmpty())
                array.add(imovel);
            else {
                if (imovel.getEndereco().toUpperCase().contains(busca.toUpperCase()))
                    array.add(imovel);
            }
        }
        return array;
    }

    public static ArrayList<Imovel> getListaImoveisDisponiveis() {
        ArrayList<Imovel> array = new ArrayList<>();

        for (Imovel imovel : arrayImoveis)
            if (imovel.isDisponivel())
                array.add(imovel);

        return array;
    }

    public static void alterarDisponibilidade(int imid, boolean disponivel) {
        for (Imovel imovel : arrayImoveis)
            if (imovel.getImid() == imid)
                imovel.setDisponivel(disponivel);

        salvarImoveis();
    }

    public static boolean imoveisDisponiveis() {
        for (Imovel imovel : arrayImoveis) {
            if (imovel.isDisponivel())
                return true;
        }
        return false;
    }

    public static String getNumeroImoveis() {
        return (arrayImoveis.size() > 0 ? arrayImoveis.size() + " " + IMOVEIS : NENHUM_IMOVEL) + " | ";
    }

}
