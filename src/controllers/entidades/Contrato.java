package controllers.entidades;

import controllers.interfaces.Serializavel;
import controllers.interfaces.Strings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Entidade que representa dados do contrato
 *
 */
public class Contrato implements Serializavel {

    private final int coid;
    private Cliente cliente;
    private Imovel imovel;
    private long dataInicio;
    private long dataFim;

    public Contrato(int coid, Cliente cliente, Imovel imovel, long dataInicio, long dataFim) {
        this.coid = coid;
        this.cliente = cliente;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getCoid() {
        return coid;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Date getDataInicio() {
        return new Date(dataInicio);
    }

    public void setDataInicio(long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return new Date(dataFim);
    }

    public void setDataFim(long dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isTerminado() {
        return getDataInicio().getTime() > getDataFim().getTime();
    }

    /*Método criado para serializar dos dados para serem enviados ao model para armazenamento na base de dados*/
    public Object[] toObject() {
        DateFormat simple = new SimpleDateFormat("dd/MM/yyyy");

        return new Object[]{
                getCoid(),
                getImovel().getNomeProprietario(),
                getImovel().getEndereco(),
                getImovel().isVenda() ? getImovel().getValorVenda() : getImovel().getValorLocacao(),
                getCliente().getNome(),
                getCliente().getCpf(),
                simple.format(getDataInicio()),
                simple.format(getDataFim()),
                getImovel().isVenda()? Strings.VENDA : Strings.LOCACAO
        };
    }

    @Override
    public String toString() {
        return "{\"coid\":" + coid +
                ", \"clid\":" + cliente.getClid() +
                ",\"imid\":" + imovel.getImid() +
                ", \"dataInicio\":" + dataInicio +
                ",\"dataFim\": " + dataFim +
                " }";
    }
}
