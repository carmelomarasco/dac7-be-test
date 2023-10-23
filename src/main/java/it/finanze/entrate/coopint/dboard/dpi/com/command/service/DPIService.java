package it.finanze.entrate.coopint.dboard.dpi.com.command.service;

import oecd.ties.dpi.v1.DPIOECD;

//@Service
public interface DPIService {

    long saveMessage(DPIOECD xmlMessage, String createdBy );

    void deleteMessage(Long id);
}
