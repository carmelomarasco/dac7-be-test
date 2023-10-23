package it.finanze.entrate.coopint.dboard.dpi.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ComClass {

    NEW_DATA_ON_TERMS("NUOVI DATI NEI TERMINI"),

    NEW_DATA_OUT_OF_TERMS("NUOVI DATI FUORI DAI TERMINI"),

    CORRECTIVE_DATA("CORRETTIVA"), // Solo dopo la scadenza del periodo fiscale

    INTEGRATIVE_DATA("INTEGRATIVA"),

    //SUBSTITUTIVE("SOSTITUTIVA"),

    INVALIDATE("ANNULLAMENTO"), // solo prima della scadenza

    NO_DATA("ASSENZA DI DATI DA COMUNICARE");

    private final String value;

    public String getValue() {
        return value;
    }

    public static ComClass fromValue(String v) {

        if (v == null)
            return null;

        ComClass[] var4;
        int var3 = (var4 = values()).length;
        for (int var2 = 0; var2 < var3; ++var2) {

            ComClass c = var4[var2];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
