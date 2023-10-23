package it.finanze.entrate.coopint.dboard.dpi.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    CANCELED("CANCELED"),
    REPLACED("REPLACED"),
    ACCEPTED_NON_CONTRIBUTING("ACCEPTED_NON_CONTRIBUTING"),
    FINALIZED("FINALIZED"),
    SUSPENDED("SUSPENDED"),
    CAUTIONED("CAUTIONED");



    private final String value;

    public String getValue() {
        return value;
    }

    public static Status fromValue(String v) {

        if (v == null)
            return null;

        Status[] var4;
        int var3 = (var4 = values()).length;
        for (int var2 = 0; var2 < var3; ++var2) {

            Status c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
