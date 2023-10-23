package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.CommunicationStatus;

public class CommunicationStatusComparator implements Comparator<CommunicationStatus> {

	public CommunicationStatusComparator() {
	}

	@Override
	public int compare(CommunicationStatus s1, CommunicationStatus s2) {
		if (s1 == null && s2 == null) {
			return 0;
		} else if (s1 == null && s2 != null) {
			return -1;
		} else if (s1 != null && s2 == null) {
			return 1;
		} else {
			return StringUtils.trimToEmpty(s1.getStatus()).compareTo(StringUtils.trimToEmpty(s2.getStatus()));
		}
	}

}