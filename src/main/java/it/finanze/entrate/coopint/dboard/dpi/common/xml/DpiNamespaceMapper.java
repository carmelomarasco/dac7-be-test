package it.finanze.entrate.coopint.dboard.dpi.common.xml;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class DpiNamespaceMapper extends NamespacePrefixMapper {

    private static final String DPI_PREFIX = "dpi";
    private static final String DPI_URI = "urn:oecd:ties:dpi:v1";

    private static final String STF_PREFIX = "stf";
    private static final String STF_URI = "urn:oecd:ties:dpistf:v1";

    private static final String ISO_CTS_PREFIX = "iso";
    private static final String ISO_CTS_URI = "urn:oecd:ties:isodpitypes:v1";


    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {

        switch (namespaceUri) {
            case DPI_URI:
                return DPI_PREFIX;
            case STF_URI:
                return STF_PREFIX;
            case ISO_CTS_URI:
                return ISO_CTS_PREFIX;
        }

        return suggestion;
    }
}
