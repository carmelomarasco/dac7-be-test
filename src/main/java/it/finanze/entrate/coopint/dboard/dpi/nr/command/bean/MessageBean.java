package it.finanze.entrate.coopint.dboard.dpi.nr.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.StatusMessageIn;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy.MessageOutLazy;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.StatusMessageInRepository;
import it.finanze.entrate.coopint.dboard.dpi.utils.DateUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageBean {
    private String uuid;
    private String msgRef;
    private String country;
    private String year;
    private String type;
    private String date;
    private String listPO;
    private Long poCount;
    private Long sellerCount;
    private String status;
    private String notes;
    private boolean checked;
    private String messageRefId; //TODO ????
    private String startDate;
    private String endDate;
    private String validationResult;

    public static MessageBean getFromLazyEntity(MessageOutLazy input, String validationResult) {
        return MessageBean.builder()
                .uuid(input.getId().getMsgUuid())
                .msgRef(input.getMessageRef())
                .country(input.getCountry().getValue())
                .year(input.getFiscalYear().getYear().toString())
                .type(input.getMessageTypeIndic().getId().toString())
                .date(input.getSendDate() == null ? "" : DateUtils.convertDateToString(Date.from(input.getSendDate())))
                // TODO Verificare
                .listPO(input.getListPO())
                .poCount((long)input.getPlatformOperatorsCount())
                .sellerCount((long)input.getReportableSellersCount())
                .status(input.getStatus().getId().toString())
                .messageRefId(input.getMessageRef())
                .startDate(input.getBuildStartDate() == null ? "" : DateUtils.convertDateToString(Date.from(input.getBuildStartDate())))
                .endDate(input.getBuildEndDate() == null ? "" : DateUtils.convertDateToString(Date.from(input.getBuildEndDate())))
//                .notes(input.getNotes())
                        // TODO Risolvere
                .validationResult(validationResult )
                .build();
    }

    public static List<MessageBean> getListFromLazyEntity(List<MessageOutLazy> listInput, StatusMessageInRepository statusMessageInRepository) {
        List<MessageBean> listResult = new ArrayList<>();

        for(MessageOutLazy msg:listInput){
        	Optional<StatusMessageIn> o=statusMessageInRepository.findFirstStatusMessageInByMsgUuidOrderByDataInsDesc(msg.getId().getMsgUuid());
        	if(o.isPresent()){
        		String s=o.get().getValidationResult();
        	}
        }
        
        listInput.forEach(data -> listResult.add(MessageBean.getFromLazyEntity(data,
                
        		statusMessageInRepository.findFirstStatusMessageInByMsgUuidOrderByDataInsDesc(data.getId().getMsgUuid())
                        .map(StatusMessageIn::getValidationResult)
                        .orElse("--")
                        )
                )

        );
        
        listResult.sort(new Comparator<MessageBean>(){

			@Override
			public int compare(MessageBean a, MessageBean b) {
				
				return (a.year + a.country).compareTo(b.year + b.country);
			}
        	
        });
        
        return listResult;
    }

}
