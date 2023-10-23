package it.finanze.entrate.coopint.dboard.dpi.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageTypeIndic {

    DPI401("DPI401"),
    DPI402("DPI402"),
    DPI403("DPI403");

    private final String value;

    public String getValue() {
        return value;
    }

    public static MessageTypeIndic fromValue(String v) {

        if (v == null)
            return null;

        MessageTypeIndic[] var4;
        int var3 = (var4 = values()).length;
        for (int var2 = 0; var2 < var3; ++var2) {

            MessageTypeIndic c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
