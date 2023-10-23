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
@Table(name = "STATUS_CHANGE_ROUTES", schema = "DBOARD_DPI_NATIONAL", catalog = "")
public class StatusChangeRoutes {
    @Id
    @Column(name = "ROUTE_ID")
    private long routeId;
    @Basic
    @Column(name = "ROUTE_FROM")
    private long routeFrom;
    @Basic
    @Column(name = "ROUTE_TO")
    private long routeTo;
}
