package controllers.interfaces;

import model.SGIBD;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/*
 * Classe responsável pela definição dos tamanhos da UI
 */
public interface Cores {
    boolean temaEscuro = (boolean) SGIBD.getInstance().carregarConfiguracoes().get("temaEscuro");
    Color  PAINEIS= temaEscuro ? new Color(70, 70, 80) : new Color(250, 250, 250);
    Color BACKGROUND = temaEscuro ? new Color(50, 50, 60) : new Color(242, 242, 242);
    Color TEXTO = temaEscuro ? new Color(232, 232, 242) : new Color(20, 20, 30);

    Color DESTAQUE = new Color(64,131,201);
    Border BORDA = BorderFactory.createLineBorder(temaEscuro? new Color(100,100,110):new Color(209,209,209),1);
    public void configurarCores();
}
