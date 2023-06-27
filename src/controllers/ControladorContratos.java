package controllers;

import controllers.entidades.Cliente;
import controllers.entidades.Contrato;
import controllers.entidades.Imovel;
import controllers.interfaces.Strings;
import model.SGIBD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class ControladorContratos {
    private static final SGIBD BD = SGIBD.getInstance();
    private static final ArrayList<Contrato> arrayContratos = BD.getContratos();

    private static void salvarContratos() {
        //todo mensagem
        BD.salvarContratos(arrayContratos);
    }

    public static void adicionarContrato(Cliente cliente, Imovel imovel, String dataCriacao, String dataFim, boolean tipo) {
        if (dataCriacao.isEmpty() || dataFim.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
            return;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataC = simpleDateFormat.parse(dataCriacao);
            Date dataF = simpleDateFormat.parse(dataFim);

            ControladorImoveis.alterarDisponibilidade(imovel.getImid(), false);

            arrayContratos.add(new Contrato(
                    BD.getProximoIdContrato(),
                    cliente,
                    imovel,
                    dataC.getTime(),
                    dataF.getTime()
            ));

            salvarContratos();
        } catch (ParseException e) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO);
        }
    }

    public static void atualizarContrato(int coid, Cliente cliente, Imovel imovel, String dataCriacao, String dataFim, boolean tipo) {
        if (dataCriacao.isEmpty() || dataFim.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
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
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO);
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

        return ativo ? (countAtivos > 0 ? countAtivos + " " + Strings.CONTRATOS_ATIVOS : Strings.NENHUM_CONTRATO_ATIVO) + " | " :
                (countTerminados > 0 ? countTerminados + " " + Strings.CONTRATOS_TERMINADOS : Strings.NENHUM_CONTRATO_TERMINADO) + " | ";
    }
}

