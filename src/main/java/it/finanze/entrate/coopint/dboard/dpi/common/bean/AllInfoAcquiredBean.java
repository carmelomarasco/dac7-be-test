package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AllInfoAcquiredBean {

    CommunicationBean communicationBean;
    DpiNationalNotifyBean dpiNationalNotifyBean;
    HistoryBean historyBean;
    Date reportingPeriod;
    Boolean fyForCurrentComIsExpired;
    String messageTypeIndic;
}
