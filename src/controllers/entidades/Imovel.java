package controllers.entidades;

import controllers.interfaces.Serializavel;

/*
 * Entidade que representa dados do imóvel
 *
 */
public class Imovel implements Serializavel {

    private final int imid;
    private String nomeProprietario;
    private double valorLocacao;
    private double valorVenda;
    private String endereco;
    private int numQuartos;
    private int numBanheiros;

    private boolean venda;
    private boolean disponivel;


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

    public int getImid() {
        return imid;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public double getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(double valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumQuartos() {
        return numQuartos;
    }

    public void setNumQuartos(int numQuartos) {
        this.numQuartos = numQuartos;
    }

    public int getNumBanheiros() {
        return numBanheiros;
    }

    public void setNumBanheiros(int numBanheiros) {
        this.numBanheiros = numBanheiros;
    }

    public boolean isVenda() {
        return venda;
    }

    public void setVenda(boolean venda) {
        this.venda = venda;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    /*Método criado para serializar dos dados para serem enviados ao model para armazenamento na base de dados*/
    @Override
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
