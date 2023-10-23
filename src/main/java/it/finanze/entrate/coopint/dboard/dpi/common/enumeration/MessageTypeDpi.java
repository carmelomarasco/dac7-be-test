package it.finanze.entrate.coopint.dboard.dpi.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageTypeDpi {

    DPI401("DPI401"),
    DPI402("DPI402");

    private final String value;

    public String getValue() {
        return value;
    }

    public static MessageTypeDpi fromValue(String v) {

        if (v == null)
            return null;

        MessageTypeDpi[] var4;
        int var3 = (var4 = values()).length;
        for (int var2 = 0; var2 < var3; ++var2) {

            MessageTypeDpi c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
