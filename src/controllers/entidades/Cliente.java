package controllers.entidades;

import controllers.interfaces.Serializavel;

/*
 * Entidade que representa dados do cliente
 *
 */
public class Cliente implements Serializavel {

    private final int clid;
    private String nome;
    private String email;
    private String cpf;
    private double renda;

    public Cliente(int clid, String nome, String cpf, String email, double renda) {
        this.clid = clid;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.renda = renda;
    }

    public int getClid() {
        return clid;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public double getRenda() {
        return renda;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRenda(double renda) {
        this.renda = renda;
    }

    /*Método criado para serializar dos dados para serem enviados ao model para armazenamento na base de dados*/
    public Object[] toObject() {
        return new Object[]{
                getClid(),
                getNome(),
                getCpf(),
                getEmail(),
                getRenda()
        };
    }

    @Override
    public String toString() {
        return "{" + "\"clid\":" + clid + ", \"nome\": \"" + nome + "\", \"cpf\": \"" + cpf + "\", \"email\": \"" + email + "\", \"renda\":" + renda + "}";
    }
}
