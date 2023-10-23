package it.finanze.entrate.coopint.dboard.dpi.utils;


import lombok.extern.apachecommons.CommonsLog;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

@CommonsLog
public class JaxbUtils {




    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }

    public static <T> T unmarshall(byte[] xml, Class<T> clazz) throws JAXBException {
        try {

            log.info("Start unmarshall");
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //noinspection unchecked
            return (T) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml));

        } catch (JAXBException ex) {
            throw ex;
        }
    }

    public static <T> byte[] marshall(Object o, Class<T> clazz) throws JAXBException {

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(o, baos);
            return baos.toByteArray();

        } catch (JAXBException ex) {
            throw ex;
        }
    }
/*
    public static <T> byte[] partialMarshall(T u, Class<T> clazz, String localPart, SchemaVersionEnum schemaVersion) throws JAXBException {
        try {
            String namespaceUri = SchemaVersionEnum.V1.equals(schemaVersion) ? "urn:oecd:ties:crs:v1" : "urn:oecd:ties:crs:v2";
            CrsJaxbHelper<T> jaxbHelper = new CrsJaxbHelper<>(clazz);
            String result = jaxbHelper.partialMarshal(u, new QName(namespaceUri, localPart));
            return result.getBytes();

        } catch (JAXBException ex) {
            throw ex;
        }
    }

    public static <T> T partialUnmarshall(byte[] xmlFragment, Class<T> clazz) throws JAXBException, XMLStreamException {
        try {

            CrsJaxbHelper<T> jaxbHelper = new CrsJaxbHelper<>(clazz);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(new ByteArrayInputStream(xmlFragment));
            return jaxbHelper.partialUnmarshal(xmlStreamReader);

        } catch (JAXBException | XMLStreamException ex) {
            throw ex;
        }
    }

    public static <T> T partialUnmarshall(XMLStreamReader xmlStreamReader, Class<T> clazz) throws JAXBException {
        CrsJaxbHelper<T> jaxbHelper = new CrsJaxbHelper<>(clazz);
        return jaxbHelper.partialUnmarshal(xmlStreamReader);


    }
*/
}
