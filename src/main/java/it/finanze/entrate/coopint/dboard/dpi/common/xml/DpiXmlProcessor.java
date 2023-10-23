package it.finanze.entrate.coopint.dboard.dpi.common.xml;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import it.finanze.entrate.coopint.xml.AbstractXmlProcessor;

/**
 * this class exposes methods to convert objects from and to JAXB starting from
 * a stream
 *
 */
public class DpiXmlProcessor<T> extends AbstractXmlProcessor<T> {

	private static XMLInputFactory xif = createXmlInputFactory();

	public DpiXmlProcessor(Class<T> classToBind) {
		super(classToBind);
		jaxbInterface = new DpiJaxbHelper<>(classToBind);
	}

	public static String extractTagValue(InputStream xmlContent, String tagToExtract) {
		String value = "";
		try {
			StreamSource xml = new StreamSource(xmlContent);

			XMLStreamReader xsr = xif.createXMLStreamReader(xml);

			// Advance to the "Tag" element.
			while (xsr.hasNext()) {

				if (xsr.isStartElement()) {
					String localName = xsr.getLocalName();

					if (tagToExtract.equalsIgnoreCase(localName)) {
						value = xsr.getElementText();
						break;
					}
				}
				xsr.next();
			}
			xsr.close();

		} catch (XMLStreamException e) {
			logger.error(e.getMessage());
		}
		return value;

	}
	

	private static XMLInputFactory createXmlInputFactory() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
		return factory;
	}
}
