package it.finanze.entrate.coopint.dboard.dpi.common.service;

import org.apache.kafka.common.protocol.types.Field.Str;

public interface Dac7VMService {
    String getStatusMessage(String xmlPath);
    boolean getValidationStatus(String xmlPath);
}
