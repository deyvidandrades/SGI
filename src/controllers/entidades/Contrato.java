package controllers.entidades;

import controllers.interfaces.Serializavel;
import model.SGIBD;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Entidade que representa dados do contrato
 *
 */
public class Contrato implements Serializavel {

    private final int coid;
    private final Cliente cliente;
    private final Imovel imovel;
    private final long dataInicio;
    private final long dataFim;
    private final boolean tipo;

    public Contrato(int coid, Cliente cliente, Imovel imovel, long dataInicio, long dataFim, boolean tipo) {
        this.coid = coid;
        this.cliente = cliente;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo = tipo;
    }

    /*Contratos recém criados tem seu id gerado no construtor*/
    public Contrato(Cliente cliente, Imovel imovel, long dataInicio, long dataFim, boolean tipo) {
        this.coid = SGIBD.getProximoIdContrato();
        this.cliente = cliente;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo = tipo;
    }

    public int getCoid() {
        return coid;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public Date getDataInicio() {
        return new Date(dataInicio);
    }

    public Date getDataFim() {
        return new Date(dataFim);
    }

    public boolean isTipo() {
        return tipo;
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
                isTipo() ? "Venda" : "Locação"
        };
    }

    @Override
    public String toString() {
        return "{\"coid\":" + coid +
                ", \"clid\":" + cliente.getClid() +
                ",\"imid\":" + imovel.getImid() +
                ", \"dataInicio\":" + dataInicio +
                ",\"dataFim\": " + dataFim +
                ",\"tipo\": " + tipo +
                " }";
    }
}
