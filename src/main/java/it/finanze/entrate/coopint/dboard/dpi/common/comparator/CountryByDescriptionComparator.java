package it.finanze.entrate.coopint.dboard.dpi.common.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;

public class CountryByDescriptionComparator implements Comparator<Country> {

	public CountryByDescriptionComparator() {
	}

	@Override
	public int compare(Country s1, Country s2) {
		if (s1 == null && s2 == null) {
			return 0;
		} else if (s1 == null && s2 != null) {
			return -1;
		} else if (s1 != null && s2 == null) {
			return 1;
		} else {
			return StringUtils.trimToEmpty(s1.getDescription()).compareTo(StringUtils.trimToEmpty(s2.getDescription()));
		}
	}

}