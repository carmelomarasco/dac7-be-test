package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "FISCAL_YEAR", schema = "DBOARD_DPI_NATIONAL", catalog = "")
public class FiscalYear {
	
	@Id
	@Column(name = "YEAR")
	private Long year;
	@Basic
	@Column(name = "EXPIRATION_DATE")
	private Date expirationDate;
	@Basic
	@Column(name = "EXTENDED_EXP_DATE")
	private Date extendedExpDate;
	@Basic
	@Column(name = "EXPIRED")
	private boolean expired;

	@Basic
	@Column(name = "FLAG_NR_START_MESSAGE")
	private long flagNrStartMessage;

	// @OneToMany(mappedBy = "fiscalYearByFiscalYear")
	// private Collection<Communication> communicationsByYear;

	@Override
	public String toString() {
		return "FiscalYearEntity [year=" + year + ", expirationDate=" + expirationDate + ", extendedExpDate="
				+ extendedExpDate + ", expired=" + expired + "]";
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		FiscalYear that = (FiscalYear) o;
		return getYear() != null && Objects.equals(getYear(), that.getYear());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
