package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.Communication;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.CommunicationData;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.EffectiveDestEntity;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.PotentialDestEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationDto implements Serializable {

	private static final long serialVersionUID = 141333323454L;

	private String comUuid;
	private String data;
	private String messageDate;
	private String messageType;
	private String protocol;
	private CommunicationStatusDto status;
	private String senderCf;
	private String sentDate;
	private String sendingEntityIn;
	private String receipt;
	private CountryDto transmittingCountry;
	private String reportingPeriod;
	private String platformOperatorName;
	private Long reportableSellersCount;
	private CountryDto receivingCountry;
	private String messageRefId;
	private MessageTypeIndicDto messageTypeIndic;
	private List<CountryDto> effectiveDestsCountries;
	private List<CountryDto> potentialDestsCountries;
	private Boolean hasXmlBlob;
	private Boolean hasXlsxBlob;
	private ComClassDto comClass;
	private String fiscalYear;
	private List<CommunicationCodesDto> codesDto = new ArrayList<>();
	
	private CommunicationDto(Communication entity, CommunicationData cdata) {
		this();
		if (entity != null) {
			if (entity.getId() != null) {
				this.setComUuid(entity.getId().getComUuid());
				try {
					this.setData(CommunicationSearchDto.dateTimeItalianFormatter.format(entity.getId().getData()));
				} catch (Exception e) {
					this.setData(StringUtils.EMPTY);
				}
			} else {
				this.setComUuid(StringUtils.EMPTY);
				this.setData(StringUtils.EMPTY);
			}
			try {
				this.setMessageDate(CommunicationSearchDto.dateTimeItalianFormatter.format(entity.getMessageDate()));
			} catch (Exception e) {
				this.setMessageDate(StringUtils.EMPTY);
			}
			this.setProtocol(StringUtils.trimToEmpty(entity.getProtocol()));
			this.setSenderCf(StringUtils.trimToEmpty(entity.getSenderCf()));
			try {
				this.setSentDate(CommunicationSearchDto.dateTimeItalianFormatter.format(entity.getSentDate()));
			} catch (Exception e) {
				this.setSentDate(StringUtils.EMPTY);
			}
			this.setReceipt(StringUtils.trimToEmpty(entity.getReceipt()));
			this.setStatus(CommunicationStatusDto.getDto(entity.getStatus()));
			this.setSendingEntityIn(StringUtils.trimToEmpty(entity.getSendingEntityIn()));
			this.setTransmittingCountry(CountryDto.getDto(entity.getTransmittingCountry()));
			this.setReceivingCountry(CountryDto.getDto(entity.getReceivingCountry()));
			this.setMessageType(StringUtils.trimToEmpty(entity.getMessageType()));
			this.setMessageRefId(StringUtils.trimToEmpty(entity.getMessageRefId()));
			this.setMessageTypeIndic(MessageTypeIndicDto.getDto(entity.getMessageTypeIndic()));
			this.setComClass(ComClassDto.getDto(entity.getComClass()));
			try {
				this.setReportingPeriod(
						CommunicationSearchDto.dateTimeItalianFormatter.format(entity.getReportingPeriod()));
			} catch (Exception e) {
				this.setReportingPeriod(StringUtils.EMPTY);
			}
			if (entity.getReportingPeriod() != null) {
				this.setFiscalYear(String.valueOf(entity.getReportingPeriod().getYear()));
			} else {
				this.setFiscalYear(StringUtils.EMPTY);
			}
			try {
				this.setMessageDate(CommunicationSearchDto.dateTimeItalianFormatter.format(entity.getMessageDate()));
			} catch (Exception e) {
				this.setMessageDate(StringUtils.EMPTY);
			}
			this.setPlatformOperatorName(StringUtils.trimToEmpty(entity.getPlatformOperatorName()));
			if (entity.getReportableSellersCount() != null && entity.getReportableSellersCount() >= 0) {
				this.setReportableSellersCount(entity.getReportableSellersCount());
			} else {
				this.setReportableSellersCount(0L);
			}
			this.effectiveDestsCountries = new LinkedList<>();
			if (!CollectionUtils.isEmpty(entity.getEffectiveDests())) {
				for (EffectiveDestEntity ede : entity.getEffectiveDests()) {
					this.effectiveDestsCountries.add(CountryDto.getDto(ede.getCountryByCountryId()));
				}
			}
			this.potentialDestsCountries = new LinkedList<>();
			if (!CollectionUtils.isEmpty(entity.getPotentialDests())) {
				for (PotentialDestEntity ede : entity.getPotentialDests()) {
					this.potentialDestsCountries.add(CountryDto.getDto(ede.getCountryByCountryId()));
				}
			}
			if (cdata != null) {
                this.hasXmlBlob = cdata.getCommunicationBlob() != null;
                this.hasXlsxBlob = cdata.getExcelBlob() != null;
			}
			this.setSenderCf(StringUtils.trimToEmpty(entity.getSenderCf()));
		}
	}

	public static CommunicationDto getDto(Communication entity, CommunicationData cdata) {
		if (entity != null) {
			return new CommunicationDto(entity, cdata);
		} else {
			return null;
		}
	}

//	public static List<CommunicationDto> getDtos(List<Communication> ents) {
//		List<CommunicationDto> ret = new LinkedList<>();
//		if (!CollectionUtils.isEmpty(ents)) {
//			for (Communication fy : ents) {
//				ret.add(CommunicationDto.getDto(fy));
//			}
//		}
//		return ret;
//	}

}