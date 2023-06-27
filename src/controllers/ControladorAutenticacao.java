package controllers;

import controllers.interfaces.Strings;

import java.util.Base64;

public abstract class ControladorAutenticacao {
    private static final String chave = Strings.SISTEMA_SGI;

    public static String converterParaB64(String senha) {
        return Base64.getEncoder().encodeToString((senha + chave).getBytes());
    }

    public static String converterDeB64(String senha) {
        byte[] decodedBytes = Base64.getDecoder().decode((senha));
        return new String(decodedBytes).replace(chave, "");
    }
}
