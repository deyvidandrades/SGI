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

public class ControladorContratos {

    public static ArrayList<Contrato> listarContratos() {
        return SGIBD.getContratos();
    }

    public static void adicionarContrato(Cliente cliente, Imovel imovel, String dataCriacao, String dataFim, boolean tipo) {
        if (dataCriacao.isEmpty() || dataFim.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dataC = simpleDateFormat.parse(dataCriacao);
                Date dataF = simpleDateFormat.parse(dataFim);

                boolean adicionado = SGIBD.adicionarContrato(new Contrato(
                        cliente,
                        imovel,
                        dataC.getTime() * 1000,
                        dataF.getTime() * 1000,
                        tipo
                ));

                if (!adicionado)
                    ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CONTRATO_EXISTENTE);
            } catch (ParseException e) {
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO);
            }
        }
    }


    public static void atualizarContrato(int coid, Cliente cliente, Imovel imovel, String dataCriacao, String dataFim, boolean tipo) {
        if (dataCriacao.isEmpty() || dataFim.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dataC = simpleDateFormat.parse(dataCriacao);
                Date dataF = simpleDateFormat.parse(dataFim);

                boolean adicionado = SGIBD.atualizarContrato(new Contrato(
                        coid,
                        cliente,
                        imovel,
                        dataC.getTime(),
                        dataF.getTime(),
                        tipo
                ));

                if (!adicionado)
                    ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CONTRATO_EXISTENTE);

            } catch (ParseException e) {
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO);
            }
        }
    }

    public static void removerContrato(int coid) {
        boolean removido = SGIBD.removerContrato(coid);

        if (!removido)
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO_REMOCAO);
    }
}
