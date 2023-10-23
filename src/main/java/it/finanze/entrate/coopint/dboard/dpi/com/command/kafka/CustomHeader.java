package it.finanze.entrate.coopint.dboard.dpi.com.command.kafka;

public interface CustomHeader {

    String EVENT_NAME = "event_name";
    String RECEIVER_NAME = "receiver_name";
    String PRODUCER_NAME = "producer_name";
    String PROTOCOL = "protocol";
    String STACKTRACE = "stacktrace";
    String PARENT_HEADER = "parent_header";
    String EMPTY_PAYLOAD = "empty_payload";
    String COM_UUID = "com_uuid";
    String ENDED_PROCESS = "ended_process";
    String MESSAGE_TYPE_INDIC = "message_type_indic";
    String REPORTING_PERIOD = "reporting_period";
    String PAYLOAD_II = "second_payload";
    String PREVIOUS_STATUS = "previous_status";
    String DATE_RECEIVED_TIMESTAMP = "date_received_timestamp";
    String SENDING_FISCAL_CODE = "sending_fiscal_code";
    String URL_CALLBACK = "url_callback";
    String CONTEXT = "context";
    String LATE_COM = "late_com";
    String INVALIDATE_BY_SYSTEM = "invalidate_by_system";
    String SID = "sid";
    String LOG_REQUEST = "log_request";
}
