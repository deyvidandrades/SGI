package controllers;

import controllers.entidades.Contrato;
import controllers.entidades.Imovel;
import controllers.interfaces.Strings;
import model.SGIBD;

import java.util.ArrayList;

public class ControladorImoveis {


    private static final SGIBD BD = SGIBD.getInstance();
    public static ArrayList<Imovel> listarImoveis() {
        return BD.getImoveis();
    }

    public static void adicionarImovel(String proprietario, String endereco, String valorLocacao, String valorVenda, String numeroQuartos, String numeroBanheiros, boolean locacao) {

        if (proprietario.isEmpty() || endereco.isEmpty() || valorLocacao.isEmpty() || valorVenda.isEmpty() || numeroQuartos.isEmpty() || numeroBanheiros.isEmpty())
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
        else {
            boolean adicionado = BD.adicionarImovel(new Imovel(
                    BD.getProximoIdImovel(),
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
            boolean adicionado = BD.atualizarImovel(new Imovel(
                    imid,
                    proprietario,
                    Double.parseDouble(valorLocacao.replace(",",".")),
                    Double.parseDouble(valorVenda.replace(",",".")),
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
        boolean temContrato = false;

        for (Contrato contrato : BD.getContratos()) {
            if (contrato.getImovel().getImid() == imid) {
                temContrato = true;
                break;
            }
        }

        if (!temContrato) {

            boolean removido = BD.removerImovel(imid);

            if (!removido)
                ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_ERRO_REMOCAO);
        }else
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CONTRATO_EM_VIGENCIA_IMOVEL);
    }
}
