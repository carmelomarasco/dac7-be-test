package it.finanze.entrate.coopint.dboard.dpi.com.command.utility;

import oecd.ties.dpi.v1.*;
import oecd.ties.isodpitypes.v1.CountryCodeType;

import java.util.List;
import java.util.function.Predicate;

public class DPIUtils {
    public static Predicate<CorrectableOtherRPOType> buildPredicateOtherRPOinCountryList(List<String> countryList) {
        return otherRpo ->
                otherRpo.getResCountryCode().stream()
                        .map(CountryCodeType::value)
                        .anyMatch(countryList::contains);
    }

    public static Predicate<CorrectableReportableSellerType> buildPredicateReportableSellerInCountryList(List<String> countryList) {
        return seller -> {
            ReportableSellerType.Identity identity = seller.getIdentity();
            ReportableSellerType.Identity.IndividualSeller individualSeller = identity.getIndividualSeller();
            ReportableSellerType.Identity.EntitySeller entitySeller = identity.getEntitySeller();
            if (individualSeller != null) {
                ReportableSellerType.Identity.IndividualSeller.Standard standard = individualSeller.getStandard();
                if (standard != null) {
                    if (standard.getIndSellerID().getResCountryCode().stream()
                            .map(CountryCodeType::value)
                            .anyMatch(countryList::contains)) {
                        return true;
                    }
                }
            }
            if (entitySeller != null) {
                ReportableSellerType.Identity.EntitySeller.Standard standard = entitySeller.getStandard();
                if (standard != null) {
                    if (standard.getEntSellerID().getResCountryCode().stream()
                            .map(CountryCodeType::value)
                            .anyMatch(countryList::contains)) {
                        return true;
                    }
                }
            }
            return false;
        };
    }

    public static Predicate<CorrectableReportableSellerType> buildPredicateImmovablePropertyInCountryList(List<String> countryList) {
        return seller -> {
            return seller.getRelevantActivities().getImmovableProperty().getPropertyListing().stream()
                    .map(PropertyListingType::getAddress)
                    .map(AddressType::getCountryCode)
                    .map(CountryCodeType::value)
                    .anyMatch(countryList::contains);
        };
    }
}
