package controllers;

import controllers.entidades.Imovel;
import controllers.interfaces.Strings;
import model.SGIBD;

import java.util.ArrayList;

public class ControladorImoveis {


    public static ArrayList<Imovel> listarImoveis() {
        return SGIBD.getImoveis();
    }

    public static void adicionarImovel(String proprietario, String endereco, String valorLocacao, String valorVenda, String numeroQuartos, String numeroBanheiros, boolean locacao) {

        if (proprietario.isEmpty() || endereco.isEmpty() || valorLocacao.isEmpty() || valorVenda.isEmpty() || numeroQuartos.isEmpty() || numeroBanheiros.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            boolean adicionado = SGIBD.adicionarImovel(new Imovel(
                    proprietario,
                    Double.parseDouble(valorLocacao),
                    Double.parseDouble(valorVenda),
                    endereco,
                    Integer.parseInt(numeroQuartos),
                    Integer.parseInt(numeroBanheiros),
                    locacao,
                    true
            ));

            if (!adicionado)
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_IMOVEL_EXISTENTE);
        }
    }


    public static void atualizarImovel(int imid, String proprietario, String endereco, String valorLocacao, String valorVenda, String numeroQuartos, String numeroBanheiros, boolean locacao) {
        if (proprietario.isEmpty() || endereco.isEmpty() || valorLocacao.isEmpty() || valorVenda.isEmpty() || numeroQuartos.isEmpty() || numeroBanheiros.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            boolean adicionado = SGIBD.atualizarImovel(new Imovel(
                    imid,
                    proprietario,
                    Double.parseDouble(valorLocacao),
                    Double.parseDouble(valorVenda),
                    endereco,
                    Integer.parseInt(numeroQuartos),
                    Integer.parseInt(numeroBanheiros),
                    locacao,
                    true
            ));

            if (!adicionado)
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_IMOVEL_EXISTENTE);
        }
    }

    public static void removerImovel(int imid) {
        boolean removido = SGIBD.removerImovel(imid);

        if (!removido)
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO_REMOCAO);
    }
}