package it.finanze.entrate.coopint.dboard.dpi.res.command.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusMessageStatusEnum {

    CREATED("CREATED"),
    SUSPENDED("SUSPENDED"),
    TRANSMITTED("TRANSMITTED"),
    DELIVERED("DELIVERED"),
    DOWNLOADED("DOWNLOADED"),
    INHIBITED("INHIBITED"),
    EXPIRED("EXPIRED");

    private final String value;

    public static StatusMessageStatusEnum fromValue(String v) {
        if (v == null) return null;

        StatusMessageStatusEnum[] var4;
        int var3 = (var4 = values()).length;

        for (int var2 = 0; var2 < var3; ++var2) {
            StatusMessageStatusEnum c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }

}
