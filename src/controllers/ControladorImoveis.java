package controllers;

import controllers.entidades.Contrato;
import controllers.entidades.Imovel;
import controllers.interfaces.Strings;
import model.SGIBD;

import java.util.ArrayList;

public abstract class ControladorImoveis {

    private static final SGIBD BD = SGIBD.getInstance();
    private static final ArrayList<Imovel> arrayImoveis = BD.getImoveis();

    private static boolean imovelExiste(String endereco) {
        for (Imovel imovel : arrayImoveis)
            if (imovel.getEndereco().equals(endereco))
                return true;
        return false;
    }

    private static boolean verificarExisteContrato(int id) {
        for (Contrato contrato : BD.getContratos()) {
            if (contrato.getImovel().getImid() == id) {
                return true;
            }
        }
        return false;
    }

    private static void salvarImoveis() {
        //todo mensagem
        BD.salvarImoveis(arrayImoveis);
    }

    public static void adicionarImovel(String proprietario, String endereco, String valorLocacao, String valorVenda, String numeroQuartos, String numeroBanheiros, boolean locacao) {
        if (proprietario.isEmpty() || endereco.isEmpty() || valorLocacao.isEmpty() || valorVenda.isEmpty() || numeroQuartos.isEmpty() || numeroBanheiros.isEmpty()) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
            return;
        }

        if (imovelExiste(endereco)) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_IMOVEL_EXISTENTE);
            return;
        }

        arrayImoveis.add(new Imovel(
                BD.getProximoIdImovel(),
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
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_DADOS_INVALIDOS);
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

        if (verificarExisteContrato(imid)) {
            ControladorUI.exibirDialogoMensagens(Strings.MENSAGEM_CONTRATO_EM_VIGENCIA_IMOVEL);
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
}
