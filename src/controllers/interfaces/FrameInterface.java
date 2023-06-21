package controllers.interfaces;

import javax.swing.*;
import java.awt.*;

public interface FrameInterface {

    JFrame frame = new JFrame();
    Dimension tamanhoTela = Dimensoes.TELA;
    ImageIcon icone_64 = new ImageIcon(Strings.ICONE_64);
    ImageIcon icone_32 = new ImageIcon(Strings.ICONE_32);
    ImageIcon icone_add = new ImageIcon(Strings.ICONE_ADD);
    ImageIcon icone_buscar = new ImageIcon(Strings.ICONE_BUSCAR);
    ImageIcon icone_sair = new ImageIcon(Strings.ICONE_SAIR);
    ImageIcon icone_configuracoes = new ImageIcon(Strings.ICONE_CONFIGURACOES);
    ImageIcon icone_atualizar = new ImageIcon(Strings.ICONE_ATUALIZAR);

    void show();
}
