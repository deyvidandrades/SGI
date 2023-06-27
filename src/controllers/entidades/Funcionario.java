package controllers.entidades;

import controllers.interfaces.Serializavel;

/*
 * Entidade que representa dados do funcionário
 *
 */
public class Funcionario implements Serializavel {

    private final int fuid;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private double salario;
    private boolean gerente;


    public Funcionario(int fuid, String nome, String cpf, String email, String senha, double salario, boolean gerente) {
        this.fuid = fuid;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.salario = salario;
        this.gerente = gerente;
    }

    public int getFuid() {
        return fuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isGerente() {
        return gerente;
    }

    public void setGerente(boolean gerente) {
        this.gerente = gerente;
    }

    /*Método criado para serializar dos dados para serem enviados ao model para armazenamento na base de dados*/
    @Override
    public Object[] toObject() {
        return new Object[]{
                getFuid(),
                getNome(),
                getCpf(),
                getEmail(),
                isGerente() ? "Gerente" : "Funcionário"
        };
    }

    @Override
    public String toString() {
        return "{" +
                "\"fuid\":" + fuid +
                ", \"nome\": \"" + nome + "\"" +
                ", \"cpf\": \"" + cpf + "\"" +
                ", \"email\": \"" + email + "\"" +
                ", \"senha\": \"" + senha + "\"" +
                ", \"salario\":" + salario +
                ", \"gerente\":" + gerente +
                "}";
    }

}
