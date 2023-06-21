package controllers.entidades;

import controllers.interfaces.Serializavel;
import model.SGIBD;

/*
 * Entidade que representa dados do imóvel
 *
 */
public class Imovel implements Serializavel {

    private final int imid;
    private final String nomeProprietario;
    private final double valorLocacao;
    private final double valorVenda;
    private final String endereco;
    private final int numQuartos;
    private final int numBanheiros;

    private final boolean venda;
    private final boolean disponivel;


    public Imovel(int imid, String nomeProprietario, double valorLocacao, double valorVenda, String endereco, int numQuartos, int numBanheiros, boolean venda, boolean disponivel) {
        this.imid = imid;
        this.nomeProprietario = nomeProprietario;
        this.valorLocacao = valorLocacao;
        this.valorVenda = valorVenda;
        this.endereco = endereco;
        this.numQuartos = numQuartos;
        this.numBanheiros = numBanheiros;
        this.venda = venda;
        this.disponivel = disponivel;
    }

    /*Imoveis recém criados tem seu id gerado no construtor*/
    public Imovel(String nomeProprietario, double valorLocacao, double valorVenda, String endereco, int numQuartos, int numBanheiros, boolean venda, boolean disponivel) {
        this.imid = SGIBD.getProximoIdImovel();
        this.nomeProprietario = nomeProprietario;
        this.valorLocacao = valorLocacao;
        this.valorVenda = valorVenda;
        this.endereco = endereco;
        this.numQuartos = numQuartos;
        this.numBanheiros = numBanheiros;
        this.venda = venda;
        this.disponivel = disponivel;
    }

    public int getImid() {
        return imid;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public double getValorLocacao() {
        return valorLocacao;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getNumQuartos() {
        return numQuartos;
    }

    public int getNumBanheiros() {
        return numBanheiros;
    }

    public boolean isVenda() {
        return venda;
    }

    public boolean isDisponivel() {
        return disponivel;
    }


    /*Método criado para serializar dos dados para serem enviados ao model para armazenamento na base de dados*/
    public Object[] toObject() {
        return new Object[]{
                getImid(),
                getEndereco(),
                getNomeProprietario(),
                getValorLocacao(),
                getValorVenda(),
                getNumQuartos(),
                getNumBanheiros(),
                isVenda() ? "Venda" : "Locação",
                isDisponivel() ? "Sim" : "Não"
        };
    }

    @Override
    public String toString() {
        return "{ \"imid\":" + imid +
                ",\"nomeProprietario\": \"" + nomeProprietario + "\"" +
                ",\"valorLocacao\": " + valorLocacao +
                ",\"valorVenda\": " + valorVenda +
                ",\"endereco\": \"" + endereco + "\"" +
                ",\"numQuartos\": " + numQuartos +
                ",\"numBanheiros\": " + numBanheiros +
                ",\"venda\": " + venda +
                ",\"disponivel\": " + disponivel +
                "}";
    }
}
