package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.StreamUtils;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "COMMUNICATION_DATA", schema = "DBOARD_DPI_NATIONAL")
public class CommunicationData {
    @Id
    @Size (max = 57)
    @Column (name = "COM_UUID", nullable = false, length = 57)
    private String comUuid;

    @Column (name = "COMMUNICATION_BLOB")
    private byte[] communicationBlob;

    @Column (name = "EXCEL_BLOB")
    private byte[] excelBlob;

    @Size (max = 100)
    @NotNull
    @Column (name = "CREATED_BY", nullable = false, length = 100)
    private String createdBy;

    @NotNull
    @Column (name = "CREATED_ON", nullable = false)
    private Instant createdOn;

    @Size (max = 100)
    @Column (name = "MODIFIED_BY", length = 100)
    private String modifiedBy;

    @Column (name = "MODIFIED_ON")
    private Instant modifiedOn;

    public static CommunicationData buildEntity(String comUuid, String xmlGzippedBase64FilePath) throws Exception{

        InputStream is = StreamUtils.getGzipXmlInputStreamFromXmlGzipBase64( new ByteArrayInputStream(xmlGzippedBase64FilePath.getBytes(StandardCharsets.UTF_8)));
        byte[] comArray = StreamUtils.readAllBytes(is);
        is.close();

        return CommunicationData.builder()
                .comUuid(comUuid)
                .communicationBlob(comArray)
                .build();
    }
}