package it.finanze.entrate.coopint.dboard.dpi.common.xml;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import it.finanze.entrate.coopint.xml.AbstractJaxbHelper;
import it.sogei.dac7.ex.Dac7Exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

public class DpiJaxbHelper<T> extends AbstractJaxbHelper<T> {

	public static String context_DPIOECD_V1 = "oecd.ties.dpi.v1:oecd.ties.isodpitypes.v1:oecd.ties.dpistf.v1:oecd.ties.dsm.v1:it.gov.agenziaentrate.specifichetecniche.dpitel:it.gov.agenziaentrate.specifichetecniche.telent.v1";
    
    String schemaLocation_DPIOECD_V1 = "urn:oecd:ties:dpi:v1 ../xsd/v1/DPIXML_v1.09.xsd";

    NamespacePrefixMapper dpiNamespacePrefixMapper = new DpiNamespaceMapper();

    public DpiJaxbHelper(Class<T> classToBind) {
        this.classToBind = classToBind;

        namespacePrefixMapper = dpiNamespacePrefixMapper;
        schemaLocation = schemaLocation_DPIOECD_V1;
        context = context_DPIOECD_V1;

        try {

            jaxbContext = getJaxbContext(context);
            this.sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            this.schema = sf.newSchema(new Source[]{
                    loadStreamSource("resources/xsd/DAC7/isodpitypes_v1.0.xsd"),
                    loadStreamSource("resources/xsd/DAC7/oecddpitypes_v1.0.xsd"),
                    loadStreamSource("resources/xsd/DAC7/DPIXML_v1.09.xsd"),
                    loadStreamSource("resources/xsd/telematico/telematico_v1.xsd"),
                    loadStreamSource("resources/xsd/telematico/fornituraDPIT_v1.0.0.xsd"),
            });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
    private StreamSource loadStreamSource(String filePath) throws FileNotFoundException {
        File configFile = new File(filePath);
        InputStream stream = new FileInputStream(configFile);
        if (stream == null) {
            throw new Dac7Exception("Impossibile trovare " + filePath);
        }
        return new StreamSource(stream);
    }
}
