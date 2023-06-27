package controllers.interfaces;

import java.awt.*;

/*
 * Classe responsável pela definição dos tamanhos da UI
 */
public interface Dimensoes {

    //INTERFACES GRAFICAS//
    Dimension TELA = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension LOGIN = new Dimension(350, 650);
    Dimension DASHBOARD = new Dimension(1200, 600);

    Dimension DIALOGO_MENSAGEM = new Dimension(500, 115);
    Dimension DIALOGO_CONFIGURACOES = new Dimension(450, 380);
    Dimension DIALOGO_ALTERAR_CLIENTE = new Dimension(400, 400);
    Dimension DIALOGO_ALTERAR_IMOVEL = new Dimension(400, 420);
    Dimension DIALOGO_ALTERAR_CONTRATO = new Dimension(550, 350);
    Dimension DIALOGO_ALTERAR_FUNCIONARIO = new Dimension(520, 280);

    static Point getCentroTela(int x, int y) {
        return new Point((TELA.width - x) / 2, (TELA.height - y) / 2);
    }
}
