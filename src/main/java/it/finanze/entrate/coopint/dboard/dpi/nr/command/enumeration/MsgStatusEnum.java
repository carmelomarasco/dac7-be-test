package it.finanze.entrate.coopint.dboard.dpi.nr.command.enumeration;

public enum MsgStatusEnum {

    IN_ATTESA_DI_VALIDAZIONE("IN ATTESA DI VALIDAZIONE"),
    PRONTO_PER_INVIO("PRONTO PER L INVIO"),
    PRENOTATO_PER_INVIO("PRENOTATO PER L INVIO"),
    INIBITO("INIBITO"),
    NON_VALIDATO("NON VALIDATO"),
    TRANSMITTED("TRANSMITTED"),
    DELIVERED("DELIVERED"),
    DOWNLOADED("DOWNLOADED"),
    EXPIRED("EXPIRED"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    SUSPENDED("SUSPENDED"),
    SUSPENDED_WAITING_FOR_PREVIOUS_MESSAGES_ACCEPTED("SUSPENDED_WAITING_FOR_PREVIOUS_MESSAGES_ACCEPTED");

    private final String value;

    MsgStatusEnum(String msgStatus) {
        this.value = msgStatus;
    }

    public String getValue() {
        return value;
    }

    public static MsgStatusEnum fromValue(String v) {

        if (v == null)
            return null;

        MsgStatusEnum[] var4;
        int var3 = (var4 = values()).length;
        for (int var2 = 0; var2 < var3; ++var2) {

            MsgStatusEnum c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
