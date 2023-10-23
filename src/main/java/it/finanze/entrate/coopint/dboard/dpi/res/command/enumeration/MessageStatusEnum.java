package it.finanze.entrate.coopint.dboard.dpi.res.command.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageStatusEnum {

    RECEIVED("RECEIVED"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    XML_NOT_VALIDATED("XML_NOT_VALIDATED"),
    FAILED_DECRYPTION("FAILED_DECRYPTION");

    private final String value;

    public static MessageStatusEnum fromValue(String v) {
        if (v == null) return null;

        MessageStatusEnum[] var4;
        int var3 = (var4 = values()).length;

        for (int var2 = 0; var2 < var3; ++var2) {
            MessageStatusEnum c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }

}
