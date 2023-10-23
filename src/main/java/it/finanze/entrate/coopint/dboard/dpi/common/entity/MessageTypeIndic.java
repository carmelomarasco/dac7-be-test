package it.finanze.entrate.coopint.dboard.dpi.common.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MESSAGE_TYPE_INDIC")
public class MessageTypeIndic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_TYPE_INDIC_SEQ")
    @SequenceGenerator (name = "MESSAGE_TYPE_INDIC_SEQ", sequenceName = "MESSAGE_TYPE_INDIC_SEQ", allocationSize = 1)
    @Column (name = "MESSAGE_TYPE_INDIC_ID", nullable = false)
    private Long id;

    @Size (max = 4000)
    @NotNull
    @Column (name = "VALUE", nullable = false, length = 4000)
    private String value;

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
    
    public MessageTypeIndic(long id, String value, String createdBy, Instant createdOn, String modifiedBy, Instant modifiedOn) {
        this.id = id;
        this.value = value;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    public static MessageTypeIndicBuilder builder() {
        return new MessageTypeIndicBuilder();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MessageTypeIndic;
    }

    public static class MessageTypeIndicBuilder {
        private long id;
        private String value;
        private String createdBy;
        private Instant createdOn;
        private String modifiedBy;
        private Instant modifiedOn;

        MessageTypeIndicBuilder() {
        }

        public MessageTypeIndicBuilder messageTypeIndicId(long id) {
            this.id = id;
            return this;
        }

        public MessageTypeIndicBuilder value(String value) {
            this.value = value;
            return this;
        }

        public MessageTypeIndicBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public MessageTypeIndicBuilder createdOn(Instant createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public MessageTypeIndicBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public MessageTypeIndicBuilder modifiedOn(Instant modifiedOn) {
            this.modifiedOn = modifiedOn;
            return this;
        }

        public MessageTypeIndic build() {
            return new MessageTypeIndic(id, value, createdBy, createdOn, modifiedBy, modifiedOn);
        }

        public String toString() {
            return "MessageTypeIndicEntity.MessageTypeIndicEntityBuilder(messageTypeIndicId=" + this.id + ", value=" + this.value + ", createdBy=" + this.createdBy + ", createdOn=" + this.createdOn + ", modifiedBy=" + this.modifiedBy + ", modifiedOn=" + this.modifiedOn + ")";
        }
    }

}