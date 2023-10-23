package it.finanze.entrate.coopint.dboard.dpi.common.exception.message;

public class MessageError {

    public static String deleteSubsequent(String protocol){
        return "Impossibile eliminare la comunicazione perche' ne esiste una successiva inviata dalla stessa RFI con protocollo [" + protocol + "]";
    }
}
