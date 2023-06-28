package controllers;

import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.entidades.Imovel;
import controllers.interfaces.Strings;
import model.SGIBD;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class ControladorContratos implements Strings {
    private static final SGIBD BD = SGIBD.getInstance();
    private static final ArrayList<Contrato> arrayContratos = carregarContratos();

    private static void salvarContratos() {
        JSONArray jsonArray = new JSONArray();
        for (Contrato contrato : arrayContratos) {
            jsonArray.put(new JSONObject(contrato.toString()));
        }
        BD.salvarRegistros(DADOS_CONTRATOS, jsonArray);
    }

    private static int getProximoIdContrato() {
        int id = 0;
        for (Contrato c : arrayContratos)
            if (c.getCoid() > id)
                id = c.getCoid();

        return id + 1;
    }

    private static ArrayList<Contrato> carregarContratos() {
        ArrayList<Contrato> contratos = new ArrayList<>();

        for (Object object : BD.getRegistros(DADOS_CONTRATOS)) {
            JSONObject item = (JSONObject) object;


            Contrato contrato = new Contrato(
                    item.getInt("coid"),
                    ControladorClientes.getClienteById(item.getInt("clid")),
                    ControladorImoveis.getImovelById(item.getInt("imid")),
                    item.getLong("dataInicio"),
                    item.getLong("dataFim")
            );
            contratos.add(contrato);
        }
        return contratos;
    }

    public static Contrato getContratoById(int id) {
        for (Contrato contrato : arrayContratos) {
            if (contrato.getCoid() == id)
                return contrato;
        }
        return null;

    }

    public static boolean verificarExisteRegistroNoContrato(String key, int id) {
        for (Contrato contrato : arrayContratos) {
            switch (key) {
                case DADOS_CLIENTES -> {
                    if (contrato.getCliente().getClid() == id)
                        return true;
                }
                case DADOS_IMOVEIS -> {
                    if (contrato.getImovel().getImid() == id)
                        return true;
                }
            }
        }
        return false;
    }

    public static void adicionarContrato(Cliente cliente, Imovel imovel, String dataCriacao, String dataFim) {
        if (dataCriacao.isEmpty() || dataFim.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_DADOS_INVALIDOS);
            return;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataC = simpleDateFormat.parse(dataCriacao);
            Date dataF = simpleDateFormat.parse(dataFim);

            ControladorImoveis.alterarDisponibilidade(imovel.getImid(), false);

            arrayContratos.add(new Contrato(
                    getProximoIdContrato(),
                    cliente,
                    imovel,
                    dataC.getTime(),
                    dataF.getTime()
            ));

            salvarContratos();
        } catch (ParseException e) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_ERRO);
        }
    }

    public static void atualizarContrato(int coid, Cliente cliente, Imovel imovel, String dataCriacao, String dataFim) {
        if (dataCriacao.isEmpty() || dataFim.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_DADOS_INVALIDOS);
            return;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataC = simpleDateFormat.parse(dataCriacao);
            Date dataF = simpleDateFormat.parse(dataFim);

            for (Contrato contrato : arrayContratos) {
                if (contrato.getCoid() == coid) {
                    contrato.setCliente(cliente);
                    contrato.setImovel(imovel);
                    contrato.setDataInicio(dataC.getTime());
                    contrato.setDataFim(dataF.getTime());
                }
            }

            salvarContratos();
        } catch (ParseException e) {
            ControladorUI.exibirDialogoMensagens(MENSAGEM_ERRO);
        }
    }

    public static void removerContrato(int coid) {

        int indice = 0;
        for (Contrato contrato : arrayContratos) {
            if (contrato.getCoid() == coid) {
                ControladorImoveis.alterarDisponibilidade(contrato.getImovel().getImid(), true);
                break;
            }
            indice++;
        }

        arrayContratos.remove(indice);
        salvarContratos();
    }


    public static ArrayList<Contrato> getListaContratosAtivos(String busca) {
        ArrayList<Contrato> array = new ArrayList<>();

        for (Contrato contrato : arrayContratos) {
            if (!contrato.isTerminado()) {
                if (busca.isEmpty())
                    array.add(contrato);
                else {
                    if (contrato.getImovel().getEndereco().toUpperCase().contains(busca.toUpperCase()))
                        array.add(contrato);
                }
            }
        }
        return array;
    }

    public static ArrayList<Contrato> getListaContratosTerminados(String busca) {
        ArrayList<Contrato> array = new ArrayList<>();

        for (Contrato contrato : arrayContratos) {
            if (contrato.isTerminado()) {
                if (busca.isEmpty())
                    array.add(contrato);
                else {
                    if (contrato.getImovel().getEndereco().toUpperCase().contains(busca.toUpperCase()))
                        array.add(contrato);
                }
            }
        }
        return array;
    }


    public static String getNumeroContratos(boolean ativo) {
        int countAtivos = 0;
        int countTerminados = 0;

        for (Contrato contrato : arrayContratos)
            if (!contrato.isTerminado())
                countAtivos++;
            else
                countTerminados++;

        return ativo ? (countAtivos > 0 ? countAtivos + " " + CONTRATOS_ATIVOS : NENHUM_CONTRATO_ATIVO) + " | " :
                (countTerminados > 0 ? countTerminados + " " + CONTRATOS_TERMINADOS : NENHUM_CONTRATO_TERMINADO) + " | ";
    }
}

