package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "COM_CLASS", schema = "DBOARD_DPI_NATIONAL", catalog = "")
public class ComClassEntity {
    @Id
    @Column(name = "COM_CLASS_ID")
    private Long comClassId;
    @Basic
    @Column(name = "COM_CLASS_DESCRIPTION")
    private String comClassDescription;

//    @OneToMany(mappedBy = "comClassByComClassId")
//    private Collection<Communication> communicationsByComClassId;
}
