package it.finanze.entrate.coopint.dboard.dpi.com.command.service;

import org.springframework.messaging.MessageHeaders;

public interface DpiCommunicationService {


    void processReceivedCommunication(String payload, MessageHeaders messageHeaders);

//    void updateNotes(String payload, MessageHeaders messageHeaders);

//    StatusEntity checkIfItsPossibileToChangeStatus(UpdateBean pwb);

    void updateStatus(String payload, MessageHeaders messageHeaders);

//    void deleteCommunication(String payload, MessageHeaders messageHeaders);

//    void checkIfItsPossibileToDeleteCommunications(UpdateBean pwb);

    void finalizeAllCommunicationsOfYear(long year);

    void finalizeCommunication(String comUuid);

    void finalizeCommunication(String payload, MessageHeaders messageHeaders);

//    void updateEffectiveDestByComUuid(String payload, MessageHeaders messageHeaders);

    void addContributeToEffectiveDest( String commId );

    // NON PREVISTO IN DAC7
//    void prepareAddEffectiveDes(String payload, MessageHeaders messageHeaders);
//    void addEffectiveDes(String payload, MessageHeaders messageHeaders);
//    void removeEffectiveDes(String payload, MessageHeaders messageHeaders) ;

}
