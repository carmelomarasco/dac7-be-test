package it.finanze.entrate.coopint.dboard.dpi.com.command.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import it.finanze.entrate.coopint.dboard.dpi.common.xml.DpiJaxbHelper;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class JaxbUtils<T> {

	Class<T> clazz;
	DpiJaxbHelper<T> jaxbHelper = new DpiJaxbHelper<>(clazz);
	XMLInputFactory factory = XMLInputFactory.newInstance();

	public T unmarshall(byte[] xml) throws JAXBException {
		return jaxbHelper.unmarshal(new ByteArrayInputStream(xml));
	}

	public byte[] marshall(T o) throws JAXBException {
		ByteArrayOutputStream baos = (ByteArrayOutputStream) jaxbHelper.marshal(o);
		return baos.toByteArray();
	}

	public byte[] partialMarshall(T u, Class<T> clazz, String localPart/*, SchemaVersionEnum schemaVersion*/)
			throws JAXBException {

		String namespaceUri = "urn:oecd:ties:dpi:v1";

		String result = jaxbHelper.partialMarshal(u, new QName(namespaceUri, localPart));
		return result.getBytes();

	}

	public T partialUnmarshall(byte[] xmlFragment) throws JAXBException, XMLStreamException {
	
		XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(new ByteArrayInputStream(xmlFragment));
		return jaxbHelper.partialUnmarshal(xmlStreamReader);

	}

	public T partialUnmarshall(XMLStreamReader xmlStreamReader) throws JAXBException {
		return jaxbHelper.partialUnmarshal(xmlStreamReader);

	}

	public String extractTagValue(InputStream xmlContent, String tagToExtract) throws Exception {
		String value = "";
		XMLInputFactory xif = XMLInputFactory.newInstance();
		xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
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

		return value;
	}

}
