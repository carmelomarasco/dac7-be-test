package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "EFFECTIVE_DEST", schema = "DBOARD_DPI_NATIONAL")
@IdClass(EffectiveDestEntityPK.class)
public class EffectiveDestEntity {

	@Id
	@Column(name = "COM_UUID")
	private String comUuid;
	@Id
	@Column(name = "DATA")
	private Date data;
	@Id
	@Column(name = "COUNTRY_ID")
	private long countryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "COM_UUID", referencedColumnName = "COM_UUID", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "DATA", referencedColumnName = "DATA", nullable = false, insertable = false, updatable = false) })
	@JsonIgnore
	private Communication communication;

	@ManyToOne
	@JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID", nullable = false, insertable = false, updatable = false)
	private Country countryByCountryId;

	@Basic
	@Column(name = "REPORTABLE_SELLER_COUNT")
	private Long reportableSellerCount;
    @Basic
    @Column(name = "IMMOVABLE_PROPERTY_COUNT")
    private Long immovablePropertyCount;
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CONTRIBUTE_FOR_BUILD")
	private byte[] contributeForBuild;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EffectiveDestEntity that = (EffectiveDestEntity) o;
		return Objects.equal(comUuid, that.comUuid) && Objects.equal(data, that.data) && Objects.equal(countryId, that.countryId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(comUuid, data, countryId);
	}
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		EffectiveDestEntity that = (EffectiveDestEntity) o;
//		return countryId == that.countryId;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(countryId);
//	}
//
}
