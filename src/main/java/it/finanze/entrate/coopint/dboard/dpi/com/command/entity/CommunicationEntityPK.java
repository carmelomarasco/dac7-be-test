package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationEntityPK implements Serializable {

    @Column(name = "COM_UUID")
    @Id
    private String comUuid;
    @Column(name = "DATA")
    @Id
    private Date data;

}
