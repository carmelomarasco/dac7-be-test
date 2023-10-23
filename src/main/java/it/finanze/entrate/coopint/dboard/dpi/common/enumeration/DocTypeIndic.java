package it.finanze.entrate.coopint.dboard.dpi.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DocTypeIndic {

    OECD_0("OECD0"),
    OECD_1("OECD1"),
    OECD_2("OECD2"),
    OECD_3("OECD3"),
    OECD_10("OECD10"),
    OECD_11("OECD11"),
    OECD_12("OECD12"),
    OECD_13("OECD13");

    private final String value;

    public String getValue() {
        return value;
    }

    public static DocTypeIndic fromValue(String v) {

        if (v == null)
            return null;

        DocTypeIndic[] var4;
        int var3 = (var4 = values()).length;
        for (int var2 = 0; var2 < var3; ++var2) {

            DocTypeIndic c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
