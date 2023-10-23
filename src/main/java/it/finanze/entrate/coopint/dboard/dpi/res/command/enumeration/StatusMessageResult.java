package it.finanze.entrate.coopint.dboard.dpi.res.command.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusMessageResult {

    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    private final String value;

    public static StatusMessageResult fromValue(String v) {


        if (v == null) return null;

        StatusMessageResult[] var4;
        int var3 = (var4 = values()).length;

        for (int var2 = 0; var2 < var3; ++var2) {
            StatusMessageResult c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
