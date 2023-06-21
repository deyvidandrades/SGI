import com.formdev.flatlaf.FlatIntelliJLaf;
import controllers.ControladorUI;

public class Main {
    public static void main(String[] args) {
        /* inicialização da biblioteca de tema da interface gráfica. */
        FlatIntelliJLaf.setup();

        /*Ponto de inicio da aplicação*/
        ControladorUI.exibirTelaLogin();
    }
}