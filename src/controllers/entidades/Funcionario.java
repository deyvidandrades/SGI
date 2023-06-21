package controllers.entidades;

import controllers.interfaces.Serializavel;
import model.SGIBD;

/*
 * Entidade que representa dados do funcionário
 *
 */
public class Funcionario implements Serializavel {

    private final int fuid;
    private final String nome;
    private final String cpf;
    private final String email;
    private final String senha;
    private final double salario;
    private final boolean gerente;


    public Funcionario(int fuid, String nome, String cpf, String email, String senha, double salario, boolean gerente) {
        this.fuid = fuid;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.salario = salario;
        this.gerente = gerente;
    }

    /*Funcionarios recém criados tem seu id gerado no construtor*/
    public Funcionario(String nome, String cpf, String email, String senha, double salario, boolean gerente) {
        this.fuid = SGIBD.getProximoIdFuncionario();
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

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public double getSalario() {
        return salario;
    }

    public boolean isGerente() {
        return gerente;
    }

    /*Método criado para serializar dos dados para serem enviados ao model para armazenamento na base de dados*/
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
