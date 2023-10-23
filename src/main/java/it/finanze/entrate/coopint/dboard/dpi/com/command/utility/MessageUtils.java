package it.finanze.entrate.coopint.dboard.dpi.com.command.utility;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import com.google.common.collect.Sets;
import oecd.ties.dpi.v1.*;
import oecd.ties.isodpitypes.v1.CountryCodeType;

import it.finanze.entrate.coopint.dboard.dpi.common.enumeration.MessageType;
import it.finanze.entrate.coopint.dboard.dpi.common.enumeration.MessageTypeDpi;
import it.finanze.entrate.coopint.dboard.dpi.common.xml.DpiXmlProcessor;
import lombok.extern.apachecommons.CommonsLog;
import oecd.ties.dpistf.v1.DocSpecType;

@CommonsLog
public class MessageUtils {

	private static final String DPI_OECD_ROOT_ELEMENT = "DPI_OECD";
	private static final String DPI_BODY = "DPIBody";

	private static final String MESSAGE_SPEC_ELEMENT = "MessageSpec";
	private static final String IN_ELEMENT = "IN";
	private static final String DOC_SPEC_TYPE_INDIC_ELEMENT = "DocTypeIndic";
	private static final String DOC_REF_ID = "DocRefId";
	private static final String CORR_DOC_REF_ID = "CorrDocRefId";
	private static final String MESSAGE_REF_ID_ELEMENT = "MessageRefId";
	private static final String SENDING_ENTITY_IN = "SendingEntityIN";
	private static final String TRANSMITTING_COUNTRY_ELEMENT = "TransmittingCountry";
	private static final String RECEIVING_COUNTRY_ELEMENT = "ReceivingCountry";
	private static final String MESSAGE_TYPE_ELEMENT = "MessageType";
	private static final String MESSAGE_TYPE_INDIC_ELEMENT = "MessageTypeIndic";
	private static final String REPORTING_PERIOD_ELEMENT = "ReportingPeriod";
	private static final String TIMESTAMP_ELEMENT = "Timestamp";
	private static final String CONTACT = "Contact";
	private static final String WARNING = "Warning";

	private static final String PLATFORM_OPERATOR = "CorrectablePlatformOperator_Type";
	private static final String OTHER_PLATFORM_OPERATORS = "OtherPlatformOperators_Type";
	private static final String REPORTABLE_SELLER = "CorrectableReportableSeller_Type";
	
	private static final String RES_COUNTRY_CODE = "ResCountryCode";
	//private static final String ORGANISATION_PARTY = "OrganisationParty_Type";
	//private static final String PERSON_PARTY = "PersonParty_Type";
	
	public static final String OECD0 = "OECD0";
	private static final String OECD1 = "OECD1";
	private static final String OECD2 = "OECD2";
	public static final String OECD3 = "OECD3";
	private static final String OECD10 = "OECD10";
	private static final String OECD11 = "OECD11";
	private static final String OECD12 = "OECD12";
	private static final String OECD13 = "OECD13";

	private static final String DPI_NAMESPACE_URI_V1 = "urn:oecd:ties:dpi:v1";

	private DpiXmlProcessor<DPIOECD> dpiV1XmlProcessor = new DpiXmlProcessor<>(
			DPIOECD.class);

	/**
	 * Metodo per capire se il messaggio è un NUOVI DATI (DPI401) oppure una
	 * CORRETTIVA (DPI402) Il controllo viene effettuato dal DOCTYPEINDIC del
	 * PlatformOperator, OtherPlatformOperators e ReportableSeller
	 *
	 * @param dpi
	 * @return
	 */
	public static MessageType getTypeIndicFromMessage(DPIOECD dpi) {

        //if (dpi.getMessageSpec().getMessageTypeIndic() != null) {
            return MessageType.fromValue(dpi.getMessageSpec().getMessageTypeIndic().value());
        //}

        // verificare se occorre o meno
		/*boolean isCorrective = 
				dpi.getDPIBody().stream().anyMatch(rs -> (rs.getPlatformOperator() != null
				// PlatformOperator
				&& (rs.getPlatformOperator().getDocSpec().getDocTypeIndic().value().equals(OECD3)
						|| rs.getPlatformOperator().getDocSpec().getDocTypeIndic().value().equals(OECD2)))
				// OtherPlatformOperators
				|| (rs.getOtherPlatformOperators().getAssumingPlatformOperator() != null &&
					((rs.getOtherPlatformOperators().getAssumingPlatformOperator().getDocSpec().getDocTypeIndic().equals(OECD3)
							|| rs.getOtherPlatformOperators().getAssumingPlatformOperator().getDocSpec().getDocTypeIndic().equals(OECD2))
					||
					(rs.getOtherPlatformOperators().getAssumedPlatformOperator() != null
					&& (rs.getOtherPlatformOperators().getAssumedPlatformOperator().stream().anyMatch(ap -> 
					ap.getDocSpec().getDocTypeIndic().equals(OECD3)
					|| ap.getDocSpec().getDocTypeIndic().equals(OECD2))
					)))
				)
				// ReportableSeller
				|| (rs.getReportableSeller() != null 
					&& rs.getReportableSeller().stream().allMatch(rp -> rp.getDocSpec().getDocTypeIndic().equals(OECD3) || rp.getDocSpec().getDocTypeIndic().equals(OECD2)))
				);

		return isCorrective ? MessageType.DPI402 : MessageType.DPI401;*/
	}

	/**
	 * Metodo che restituisce il message ref
	 *
	 * @param message
	 * @return
	 */
	public static String getMessageRef(DPIOECD message) {
		Objects.requireNonNull(message, "The message cannot be null!");
		return message.getMessageSpec().getMessageRefId();
	}

	/**
	 * extract the messagerefid from the xml input stream
	 * 
	 * @param xmlStream
	 * @return
	 */
	public String getMessageRefFromXmlStream(InputStream xmlStream) {
		String messageRefId = "NO-MESSAGE-REF-RECOGNIZED";
		try {
			messageRefId = DpiXmlProcessor.extractTagValue(xmlStream, "MessageRefId");
		} catch (Exception e) {
			// get any case, even if it is in v1 version
			messageRefId = dpiV1XmlProcessor.getJaxbInterface().extractTagValue(xmlStream, "MessageRefId");
		}
		return messageRefId;
	}

	/**
	 * Metodo che restituisce i doc ref id dei OtherPlatformOperators
	 *
	 * @param message
	 * @return
	 */
	public static List<String> getDocRefIdFromOtherPlatformOperators(DPIOECD message) {
		return message.getDPIBody().stream()
		        .flatMap(body -> Stream.concat(
		                Stream.of(body.getOtherPlatformOperators().getAssumingPlatformOperator()),
		                body.getOtherPlatformOperators().getAssumedPlatformOperator().stream()
		        ))
		        .map(CorrectableOtherRPOType::getDocSpec)
		        .map(DocSpecType::getDocRefId)
		        .collect(Collectors.toList());
	}

	/**
	 * Metodo che restituisce il doc ref id del PlatformOperator
	 *
	 * @param message
	 * @return
	 */
	public static List<String> getDocRefIdFromPlatformOperator(DPIOECD message) {
		return message.getDPIBody().stream()
				.flatMap(body -> Stream.of(body.getPlatformOperator()))
				.map(CorrectablePlatformOperatorType::getDocSpec)
				.map(DocSpecType::getDocRefId)
		        .collect(Collectors.toList());
	}
	
	/**
	 * Metodo che restituisce il doc ref id del ReportableSeller
	 *
	 * @param message
	 * @return
	 */
	public static List<String> getDocRefIdFromReportableSeller(DPIOECD message) {
		return message.getDPIBody().stream()
				.flatMap(body -> body.getReportableSeller().stream())
				.map(CorrectableReportableSellerType::getDocSpec)
				.map(DocSpecType::getDocRefId)
		        .collect(Collectors.toList());
	}

	/**
	 * Metodo che restituisce i cor doc ref id dei OtherPlatformOperators
	 *
	 * @param message
	 * @return
	 */
	public static List<String> getCorrDocRefIdFromOtherPlatformOperators(DPIOECD message) {
		return message.getDPIBody().stream()
		        .flatMap(opo -> Stream.concat(
		                Stream.of(opo.getOtherPlatformOperators().getAssumingPlatformOperator()),
		                opo.getOtherPlatformOperators().getAssumedPlatformOperator().stream()
		        ))
		        .map(CorrectableOtherRPOType::getDocSpec)
		        .map(DocSpecType::getCorrDocRefId)
		        .collect(Collectors.toList());
	}

	/**
	 * Metodo che restituisce il corr doc ref id del PlatformOperator
	 *
	 * @param message
	 * @return
	 */
	public static List<String> getCorrDocRefIdFromPlatformOperator(DPIOECD message) {
		return message.getDPIBody().stream()
				.flatMap(body -> Stream.of(body.getPlatformOperator()))
				.map(CorrectablePlatformOperatorType::getDocSpec)
				.map(DocSpecType::getCorrDocRefId)
		        .collect(Collectors.toList());
	}

	/**
	 * Metodo che restituisce i doc ref id del ReportableSeller
	 *
	 * @param message
	 * @return
	 */
	public static List<String> getCorrDocRefIdFromReportableSeller(DPIOECD message) {
		return message.getDPIBody().stream()
				.flatMap(body -> body.getReportableSeller().stream())
				.map(CorrectableReportableSellerType::getDocSpec)
				.map(DocSpecType::getDocRefId)
		        .collect(Collectors.toList());
	}
	
	/**
	 * Metodo che restituisce tutti i Corr Doc Ref id del messaggio
	 *
	 * @param message
	 * @return
	 */

	public static List<String> getAllCorrDocRefId(DPIOECD message) {

		List<String> corrDocOtherPlatformOperator = getCorrDocRefIdFromOtherPlatformOperators(message);
		List<String> corrDocPlatformOperator = getCorrDocRefIdFromPlatformOperator(message);
		List<String> corrDocReportableSeller = getCorrDocRefIdFromReportableSeller(message);

		List<String> result = new ArrayList<>();

		if (!corrDocOtherPlatformOperator.isEmpty()) {
			result.addAll(corrDocOtherPlatformOperator);
		}
		if (!corrDocPlatformOperator.isEmpty()) {
			result.addAll(corrDocPlatformOperator);
		}
		if (!corrDocReportableSeller.isEmpty()) {
			result.addAll(corrDocReportableSeller);
		}

		return result;
	}

	public static Long reportableSellerCount(DPIOECD message) {
		if (message == null)
			return 0L;
		AtomicLong totalRepSeller = new AtomicLong();
		message.getDPIBody().forEach(b -> {
			totalRepSeller.addAndGet(b.getReportableSeller().size());
		});
		return totalRepSeller.get();
	}

	/**
	 * return the count of all PlatformOperator in message
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the count
	 * @throws Exception
	 */
	public static Long platformOperatorCount(DPIOECD message) {
		if (message == null)
			return 0L;
		
		return message.getDPIBody().stream()
				.filter(Objects::nonNull)
				.flatMap(body -> Stream.of(body.getPlatformOperator()))
				.count();
	}

	public static String getValueString(String input) {
		return input == null || "null".equals(input) || "".equals(input) ? "--" : input;
	}

	/**
	 * return the count of all reportable seller in message
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the count
	 * @throws Exception
	 */
	public static long getReportableSellerFromMessageStream(InputStream inputXmlStream) throws Exception {
		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
		long count = 0;
		while (xmlStreamReader.hasNext()) {
			int sc = xmlStreamReader.getEventType();
			if (sc == XMLStreamConstants.START_ELEMENT
					&& xmlStreamReader.getName().getLocalPart().equals(REPORTABLE_SELLER)) {
				count++;
			}
			xmlStreamReader.next();
		}
		return count;
	}

	/**
	 * return the docrefid list for all PlatformOperator in the message
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the list of docrefids of all PlatformOperator
	 * @throws Exception
	 */
	public static List<String> getPlatformOperatorDocRefIdsFromMessageStream(InputStream inputXmlStream) throws Exception {
		List<String> accountDocRefIds = new ArrayList<>();
		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
		long count = 0;
		while (xmlStreamReader.hasNext()) {
			int sc = xmlStreamReader.getEventType();
			if (sc == XMLStreamConstants.START_ELEMENT
					&& xmlStreamReader.getName().getLocalPart().equals(PLATFORM_OPERATOR)) {
				xmlStreamReader.next();
				sc = xmlStreamReader.getEventType();
				while (!(sc == XMLStreamConstants.END_ELEMENT
						&& xmlStreamReader.getName().getLocalPart().equals(PLATFORM_OPERATOR))) {
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(DOC_REF_ID)) {
						accountDocRefIds.add(xmlStreamReader.getElementText());
					}
					xmlStreamReader.next();
					sc = xmlStreamReader.getEventType();
				}
			}
			xmlStreamReader.next();
		}
		return accountDocRefIds;
	}

	/**
	 * return the INs list for all rfi in the message
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the list of INs of all rfi
	 * @throws Exception
	 */
//	public static List<String> getReportingFiInsFromMessageStream(InputStream inputXmlStream) throws Exception {
//		List<String> ins = new ArrayList<>();
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT)) {
//				xmlStreamReader.next();
//				sc = xmlStreamReader.getEventType();
//				while (!(sc == XMLStreamConstants.END_ELEMENT
//						&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT))) {
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals(IN_ELEMENT)) {
//						ins.add(xmlStreamReader.getElementText());
//					}
//					xmlStreamReader.next();
//					sc = xmlStreamReader.getEventType();
//				}
//			}
//			xmlStreamReader.next();
//		}
//		return ins;
//	}

	/**
	 * Returns the first RFI jaxb object read from file
	 * 
	 * @param inputXmlStream the stream to read
	 * @return the RFI object if found, null otherwise
	 * @throws Exception
	 */
//	public static OrganisationPartyType getReportingFI(InputStream inputXmlStream) throws Exception {
//		DpiJaxbHelper<OrganisationPartyType> jaxbReportingFI = new DpiJaxbHelper<>(
//				OrganisationPartyType.class);
//		OrganisationPartyType rfi = null;
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT)) {
//				rfi = jaxbReportingFI.partialUnmarshal(xmlStreamReader);	
//			}
//			xmlStreamReader.next();
//			if (rfi != null) {
//				break;
//			}
//		}
//		return rfi;
//	}

	/**
	 * return the docrefid list for all rfi in the message with oecd0
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the list of docrefids of all rfi
	 * @throws Exception
	 */
//	public static List<String> getReportingFIDocRefIdsFromMessageStream_OECD0(InputStream inputXmlStream)
//			throws Exception {
//		List<String> accountDocRefIds = new ArrayList<>();
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
//		String docrefid = "";
//		String doctypeindic = "";
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT)) {
//
//				xmlStreamReader.next();
//				sc = xmlStreamReader.getEventType();
//				while (!(sc == XMLStreamConstants.END_ELEMENT
//						&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT))) {
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals(DOC_SPEC_TYPE_INDIC_ELEMENT)) {
//						doctypeindic = xmlStreamReader.getElementText();
//
//					}
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals(DOC_REF_ID)) {
//						docrefid = xmlStreamReader.getElementText();
//
//						if (OECD0.equalsIgnoreCase(doctypeindic)) {
//
//							accountDocRefIds.add(docrefid);
//						}
//					}
//
//					xmlStreamReader.next();
//					sc = xmlStreamReader.getEventType();
//				}
//			}
//			xmlStreamReader.next();
//		}
//		return accountDocRefIds;
//	}

	/**
	 * return the docrefid list for all rfi in the message with oecd0
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the list of docrefids of all rfi
	 * @throws Exception
	 */
//	public static List<String> getReportingFIDocRefIdsFromMessageStream_OECD1(InputStream inputXmlStream)
//			throws Exception {
//		List<String> accountDocRefIds = new ArrayList<>();
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
//		String docrefid = "";
//		String doctypeindic = "";
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT)) {
//
//				xmlStreamReader.next();
//				sc = xmlStreamReader.getEventType();
//				while (!(sc == XMLStreamConstants.END_ELEMENT
//						&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT))) {
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals(DOC_SPEC_TYPE_INDIC_ELEMENT)) {
//						doctypeindic = xmlStreamReader.getElementText();
//
//					}
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals(DOC_REF_ID)) {
//						docrefid = xmlStreamReader.getElementText();
//
//						if (OECD1.equalsIgnoreCase(doctypeindic)) {
//
//							accountDocRefIds.add(docrefid);
//						}
//					}
//
//					xmlStreamReader.next();
//					sc = xmlStreamReader.getEventType();
//				}
//			}
//			xmlStreamReader.next();
//		}
//		return accountDocRefIds;
//	}

	/**
	 * return the docrefid list for all rfi in the message with oecd2 or oecd3
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the list of docrefids of all rfi
	 * @throws Exception
	 */
//	public static List<String> getReportingFIDocRefIdsFromMessageStream_OECD_2_OR_3(InputStream inputXmlStream)
//			throws Exception {
//		List<String> accountDocRefIds = new ArrayList<>();
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
//		String docrefid = "";
//		String doctypeindic = "";
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT)) {
//
//				xmlStreamReader.next();
//				sc = xmlStreamReader.getEventType();
//				while (!(sc == XMLStreamConstants.END_ELEMENT
//						&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT))) {
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals(DOC_SPEC_TYPE_INDIC_ELEMENT)) {
//						doctypeindic = xmlStreamReader.getElementText();
//
//					}
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals(DOC_REF_ID)) {
//						docrefid = xmlStreamReader.getElementText();
//
//						if (OECD2.equalsIgnoreCase(doctypeindic) || OECD3.equalsIgnoreCase(doctypeindic)) {
//
//							accountDocRefIds.add(docrefid);
//						}
//					}
//
//					xmlStreamReader.next();
//					sc = xmlStreamReader.getEventType();
//				}
//			}
//			xmlStreamReader.next();
//		}
//		return accountDocRefIds;
//	}

	/**
	 * return the docrefid list for all account in message
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the list of docrefids of all accounts
	 * @throws Exception
	 */
//	public static List<String> getAccountsDocRefIdsFromMessageStream(InputStream inputXmlStream) throws Exception {
//		List<String> accountDocRefIds = new ArrayList<>();
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
//		long count = 0;
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals(ACCOUNT_REPORT_ELEMENT)) {
//				xmlStreamReader.next();
//				sc = xmlStreamReader.getEventType();
//				while (!(sc == XMLStreamConstants.END_ELEMENT
//						&& xmlStreamReader.getName().getLocalPart().equals(ACCOUNT_REPORT_ELEMENT))) {
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals(DOC_REF_ID)) {
//						accountDocRefIds.add(xmlStreamReader.getElementText());
//					}
//					xmlStreamReader.next();
//					sc = xmlStreamReader.getEventType();
//				}
//			}
//			xmlStreamReader.next();
//		}
//		return accountDocRefIds;
//	}

	/**
	 * return the count of all account in message
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the list of docrefids of all accounts
	 * @throws Exception
	 */
//	public static List<String> getAllCorrDocRefIdsFromMessageStream(InputStream inputXmlStream) throws Exception {
//		List<String> allCorrDocRefIds = new ArrayList<>();
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
//		long count = 0;
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals(CORR_DOC_REF_ID)) {
//				allCorrDocRefIds.add(xmlStreamReader.getElementText());
//			}
//			xmlStreamReader.next();
//		}
//		return allCorrDocRefIds;
//	}

	/**
	 * return the count of all rfi in message
	 * 
	 * @param inputXmlStream the input xml stream
	 * @return the count
	 * @throws Exception
	 */
//	public static long getReportinFINumberFromMessageStream(InputStream inputXmlStream) throws Exception {
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);
//		long count = 0;
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_FI_ELEMENT)) {
//				count++;
//			}
//			xmlStreamReader.next();
//		}
//		return count;
//	}

	/**
	 * Metodo per capire se il messaggio è un NUOVI DATI oppure una CORRETTIVA Il
	 * controllo viene effettuato dal DOCTYPEINDIC della Reporting Entity, Reports e
	 * Additional Info perche' nel Message Spec non è detto che venga specificato
	 *
	 * @param inputXmlStream the xml stream to read
	 * @return the type of message
	 */
	public static MessageTypeDpi getTypeIndicFromMessageStream(InputStream inputXmlStream) throws Exception {
		Set<String> docTypeIndicSet = new HashSet<>();

		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);

		while (xmlStreamReader.hasNext()) {
			int sc = xmlStreamReader.getEventType();
			if (sc == XMLStreamConstants.START_ELEMENT
					&& xmlStreamReader.getName().getLocalPart().equals(DOC_SPEC_TYPE_INDIC_ELEMENT)) {
				docTypeIndicSet.add(xmlStreamReader.getElementText());
			}
			xmlStreamReader.next();
		}

		return docTypeIndicSet.contains("OECD2") || docTypeIndicSet.contains("OECD3") ? MessageTypeDpi.DPI402
				: MessageTypeDpi.DPI401;
	}

	public static DPIOECDValueObject getDPIOECDFromXmlStream(InputStream inputXmlStream) throws Exception {
		DPIOECDValueObject valueObject = new DPIOECDValueObject();
		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputXmlStream);

		while (xmlStreamReader.hasNext()) {
			int sc = xmlStreamReader.getEventType();

			if (sc == XMLStreamConstants.START_ELEMENT
					&& (xmlStreamReader.getName().getLocalPart().equals(DPI_OECD_ROOT_ELEMENT))) {

				for (int i = 0, len = xmlStreamReader.getAttributeCount(); i < len; i++) {
					if (xmlStreamReader.getAttributeLocalName(i).equalsIgnoreCase("version")) {
						valueObject.setVersion(xmlStreamReader.getAttributeValue(i));
						// verify if its a number
						try {
							Double.valueOf(valueObject.getVersion());
						} catch (Exception e) {
							valueObject.setVersion(null);
						}
					}
				}

				// if we haven't found the version tag, try to search from namespaces
				if (valueObject.getVersion() == null || valueObject.getVersion().isEmpty()) {
					if (xmlStreamReader.getNamespaceCount() > 0) {
						for (int nsIndex = 0; nsIndex < xmlStreamReader.getNamespaceCount(); nsIndex++) {
							String nsPrefix = xmlStreamReader.getNamespacePrefix(nsIndex);
							String nsId = xmlStreamReader.getNamespaceURI(nsIndex);
							log.info(nsPrefix + ":" + nsId);
							if (DPI_NAMESPACE_URI_V1.equalsIgnoreCase(nsId)) {
								valueObject.setVersion("1.0");
								log.info("set version to 1.0");
								break;
							}
						}
					}
				}
			}

			if (sc == XMLStreamConstants.START_ELEMENT
					&& xmlStreamReader.getName().getLocalPart().equals(MESSAGE_SPEC_ELEMENT)) {

				xmlStreamReader.next();
				sc = xmlStreamReader.getEventType();

				while (!(sc == XMLStreamConstants.END_ELEMENT
						&& xmlStreamReader.getName().getLocalPart().equals(MESSAGE_SPEC_ELEMENT))) {

					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(SENDING_ENTITY_IN)) {

						valueObject.setSendingEntityIN(xmlStreamReader.getElementText());
					}
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(TRANSMITTING_COUNTRY_ELEMENT)) {

						valueObject.setTransmittingCountry(xmlStreamReader.getElementText());
					}
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(RECEIVING_COUNTRY_ELEMENT)) {

						valueObject.setReceivingCountry(xmlStreamReader.getElementText());
					}
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(MESSAGE_TYPE_ELEMENT)) {

						valueObject.setMessageType(xmlStreamReader.getElementText());
					}
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(MESSAGE_REF_ID_ELEMENT)) {

						valueObject.setMessageRefId(xmlStreamReader.getElementText());
					}
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(MESSAGE_TYPE_INDIC_ELEMENT)) {

						valueObject.setMessageTypeIndic(xmlStreamReader.getElementText());
					}
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(REPORTING_PERIOD_ELEMENT)) {

						valueObject.setReportingPeriod(xmlStreamReader.getElementText());
					}
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(TIMESTAMP_ELEMENT)) {

						valueObject.setTimestamp(xmlStreamReader.getElementText());
					}
					
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(WARNING)) {

						valueObject.setWarning(xmlStreamReader.getElementText());
					}
					
					if (sc == XMLStreamConstants.START_ELEMENT
							&& xmlStreamReader.getName().getLocalPart().equals(CONTACT)) {

						valueObject.setContact(xmlStreamReader.getElementText());
					}
					xmlStreamReader.next();
					sc = xmlStreamReader.getEventType();
				}
				return valueObject;
			}
			xmlStreamReader.next();
		}
		return new DPIOECDValueObject();
	}

	public static class DPIOECDValueObject {
		public DPIOECDValueObject() {
		}

		/**
		<dpi:SendingEntityIN>a</dpi:SendingEntityIN>
		<dpi:TransmittingCountry>YE</dpi:TransmittingCountry>
		<dpi:ReceivingCountry>NO</dpi:ReceivingCountry>
		<dpi:MessageType>DPI</dpi:MessageType>
		<dpi:Warning>a</dpi:Warning>
		<dpi:Contact>a</dpi:Contact>
		<dpi:MessageRefId>a</dpi:MessageRefId>
		<dpi:MessageTypeIndic>DPI403</dpi:MessageTypeIndic>
		<dpi:ReportingPeriod>1957-08-13</dpi:ReportingPeriod>
		<dpi:Timestamp>2001-12-17T09:30:47Z</dpi:Timestamp>
		 */

		private String version;
		private String sendingEntityIN;
		private String transmittingCountry;
		private String receivingCountry;
		private String messageType;
		private String contact;
		private String warning;
		private String messageRefId;
		private String messageTypeIndic;
		private String reportingPeriod;
		private String timestamp;

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getTransmittingCountry() {
			return transmittingCountry;
		}

		public void setTransmittingCountry(String transmittingCountry) {
			this.transmittingCountry = transmittingCountry;
		}

		public String getReceivingCountry() {
			return receivingCountry;
		}

		public void setReceivingCountry(String receivingCountry) {
			this.receivingCountry = receivingCountry;
		}

		public String getMessageType() {
			return messageType;
		}

		public void setMessageType(String messageType) {
			this.messageType = messageType;
		}

		public String getMessageRefId() {
			return messageRefId;
		}

		public void setMessageRefId(String messageRefId) {
			this.messageRefId = messageRefId;
		}

		public String getMessageTypeIndic() {
			return messageTypeIndic;
		}

		public void setMessageTypeIndic(String messageTypeIndic) {
			this.messageTypeIndic = messageTypeIndic;
		}

		public String getReportingPeriod() {
			return reportingPeriod;
		}

		public void setReportingPeriod(String reportingPeriod) {
			this.reportingPeriod = reportingPeriod;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public String getSendingEntityIN() {
			return sendingEntityIN;
		}

		public void setSendingEntityIN(String sendingEntityIN) {
			this.sendingEntityIN = sendingEntityIN;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String getWarning() {
			return warning;
		}

		public void setWarning(String warning) {
			this.warning = warning;
		}

		@Override
		public String toString() {
			return "DPIOECDValueObject [version=" + version + ", sendingEntityIN=" + sendingEntityIN
					+ ", transmittingCountry=" + transmittingCountry
					+ ", receivingCountry=" + receivingCountry + ", messageType=" + messageType + ", messageRefId="
					+ messageRefId + ", messageTypeIndic=" + messageTypeIndic + ", reportingPeriod=" + reportingPeriod
					+ ", contact=" + contact + ", warning=" + warning
					+ ", timestamp=" + timestamp + "]";
		}

	}
	public static Map<String, List<String>> findAllDestinationsOfCommunication(DPIOECD dpioecd)
			throws Exception {
		Map<String, List<String>> cc = new HashMap<>();
//		cc.put("platformOperator", dpioecd.getDPIBody().stream()
//				.map(dpiBodyType -> dpiBodyType.getPlatformOperator().getResCountryCode())
//				.flatMap(Collection::stream)
//				.map(CountryCodeType::value)
//				.collect(Collectors.toList())
//		);
//		// TODO otherPlatformOperators estrazione country, solo da assumed?
//		cc.put("otherPlatformOperators", dpioecd.getDPIBody().stream()
//				.map(DPIBodyType::getOtherPlatformOperators)
//				.map(OtherPlatformOperatorsType::getAssumedPlatformOperator)
//				.flatMap(Collection::stream)
//				.map(CorrectableOtherRPOType::getResCountryCode)
//				.flatMap(Collection::stream)
//				.map(CountryCodeType::value)
//				.collect(Collectors.toList())
//
//		);

		List<String> reportableSellersIndividual = dpioecd.getDPIBody().stream()
				.map(DPIBodyType::getReportableSeller)
				.flatMap(Collection::stream)
				.map(ReportableSellerType::getIdentity)
				.map(ReportableSellerType.Identity::getIndividualSeller)
				.filter(Objects::nonNull)
				.map(ReportableSellerType.Identity.IndividualSeller::getStandard)
				.map(ReportableSellerType.Identity.IndividualSeller.Standard::getIndSellerID)
				.map(PersonPartyType::getResCountryCode)
				.flatMap(Collection::stream)
				.map(CountryCodeType::value)
				.collect(Collectors.toList());

		List<String> reportableSellersEntity = dpioecd.getDPIBody().stream()
				.map(DPIBodyType::getReportableSeller)
				.flatMap(Collection::stream)
				.map(ReportableSellerType::getIdentity)
				.map(ReportableSellerType.Identity::getEntitySeller)
				.filter(Objects::nonNull)
				.map(ReportableSellerType.Identity.EntitySeller::getStandard)
				.map(ReportableSellerType.Identity.EntitySeller.Standard::getEntSellerID)
				.map(OrganisationPartyType::getResCountryCode)
				.flatMap(Collection::stream)
				.map(CountryCodeType::value)
				.collect(Collectors.toList());

		List<String> immovableProperty = dpioecd.getDPIBody().stream()
				.map(DPIBodyType::getReportableSeller)
				.flatMap(Collection::stream)
				.map(ReportableSellerType::getRelevantActivities)
				.map(ReportableSellerType.RelevantActivities::getImmovableProperty)
				.map(ReportableSellerType.RelevantActivities.ImmovableProperty::getPropertyListing)
				.flatMap(Collection::stream)
				.map(PropertyListingType::getAddress)
				.map(AddressType::getCountryCode)
				.map(CountryCodeType::value)
				.collect(Collectors.toList());

		Set<String> uniqCountry = Sets.newHashSet();
		uniqCountry.addAll(reportableSellersIndividual);
		uniqCountry.addAll(reportableSellersEntity);
		uniqCountry.addAll(immovableProperty);
		// TODO reportableSellers estrazione country solo da Standard?
		cc.put("reportableSellers", new ArrayList<>(uniqCountry));
		return cc;
	}

}
