package it.finanze.entrate.coopint.dboard.dpi.nr.command.utils;


import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.MessageSearchBean;

public class MessageNRUtils {
    private static final String OECD0 = "OECD0";
    private static final String OECD1 = "OECD1";
    private static final String OECD2 = "OECD2";
    public static final String OECD3 = "OECD3";
    //used for subjects and correctable
    public static final int LIMIT_TRANSACTION = 1000;
    public static final int END_XML = -999;

/*
    public static <T> Collection<List<T>> partitionBasedOnSize(List<T> inputList, int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return inputList.stream()
                .collect(Collectors.groupingBy(s -> counter.getAndIncrement() / size))
                .values();
    }

    public static boolean isOrganisation(CorrectableAccountReportWrapper account) {
        return account.getAccountHolder().getOrganisation() != null;
    }

    public static boolean isPerson(CorrectableAccountReportWrapper account) {
        return account.getAccountHolder().getIndividual() != null;
    }

    public static boolean isControllingPerson(CorrectableAccountReportWrapper account) {
        return account.getControllingPerson() != null && !account.getControllingPerson().isEmpty();
    }

    public static String[] getTinOrg(CorrectableAccountReportWrapper account) {
        String[] result = new String[2];
        result[0] = "NO-TIN";
        result[1] = "--";

        if (!ParamUtils.isNullOrEmpty(account.getAccountHolder().getOrganisation().getIn())) {
            for (OrganisationINWrapper in : account.getAccountHolder().getOrganisation().getIn()) {
                if (in.getIssuedBy() != null) {
                    result[0] = in.getValue();
                    result[1] = in.getIssuedBy().value();
                    if (result[1].equals("IT")) {
                        return result;
                    }
                }
            }
        }
        return result;
    }
*/
    public static boolean checkFromSinottico(MessageSearchBean searchFormBean){
      return  searchFormBean.getTypeSearchParam().contains("TARDIVI")||searchFormBean.getTypeSearchParam().contains("ERRATI")||
                searchFormBean.getTypeSearchParam().contains("SCARTATI")||searchFormBean.getTypeSearchParam().contains("PRONTI_PER_INVIO")
                ||searchFormBean.getTypeSearchParam().contains("EXCHANGE_NOT_CLOSED");
    }
/*
    public static String[] getTinInd(CorrectableAccountReportWrapper account) {
        String[] result = new String[2];
        result[0] = "NO-TIN";
        result[1] = "--";

        if (!ParamUtils.isNullOrEmpty(account.getAccountHolder().getIndividual().getTin())) {
            for (TINWrapper tin : account.getAccountHolder().getIndividual().getTin()) {
                if (tin.getIssuedBy() != null) {
                    result[0] = tin.getValue();
                    result[1] = tin.getIssuedBy().value();
                    if (result[1].equals("IT")) {
                        return result;
                    }
                }
            }
        }
        return result;
    }


    public static String[] getTinCP(ControllingPersonWrapper controlling) {
        String[] result = new String[2];
        result[0] = "NO-TIN";
        result[1] = "--";

        if (!ParamUtils.isNullOrEmpty(controlling.getIndividual().getTin())) {
            for (TINWrapper tin : controlling.getIndividual().getTin()) {
                if (tin.getIssuedBy() != null) {
                    result[0] = tin.getValue();
                    result[1] = tin.getIssuedBy().value();
                    if (result[1].equals("IT")) {
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public static MessageType getTypeIndicFromMessage(CRSOECDWrapper crs) {

//        if (cbcoecd.getMessageSpec().getMessageTypeIndic() != null) {
//            return MessageType.fromValue(cbcoecd.getMessageSpec().getMessageTypeIndic().value());
//        }


        boolean isCorrective = crs.getCrsBody()
                .stream()
                .anyMatch(rs ->
                        (rs.getReportingFI() != null && (rs.getReportingFI().getDocSpec().getDocTypeIndic().value().equals(OECD3) || rs.getReportingFI().getDocSpec().getDocTypeIndic().value().equals(OECD2))) ||
                                rs.getReportingGroup().stream().allMatch(reportingGroup -> reportingGroup.getAccountReport().stream().allMatch(reportType -> reportType.getDocSpec().getDocTypeIndic().value().equals(OECD2))) ||
                                rs.getReportingGroup().stream().allMatch(reportingGroup -> reportingGroup.getAccountReport().stream().allMatch(reportType -> reportType.getDocSpec().getDocTypeIndic().value().equals(OECD3))));

        return isCorrective ? MessageType.CRS702 : MessageType.CRS701;
    }

    public static void addReportingFiToJaxb(CRSOECDWrapper crsJaxb, CorrectableOrganisationPartyWrapper rfi) {
        crsJaxb.getCrsBody().clear();
        CrsBodyWrapper crsBody = new CrsBodyWrapper();
        crsBody.setReportingFI(rfi);
        crsBody.setSchemaVersion(crsJaxb.getSchemaVersion());
        crsJaxb.getCrsBody().add(crsBody);
    }

    public static void addAccountToJaxb(CRSOECDWrapper crsJaxb, CorrectableAccountReportWrapper account) {
        CrsBodyWrapper.ReportingGroupWrapper reportingGroup = new CrsBodyWrapper.ReportingGroupWrapper();
        reportingGroup.setAccountReport(new ArrayList<>());
        reportingGroup.setPoolReport(new ArrayList<>());
        reportingGroup.setSchemaVersion(crsJaxb.getSchemaVersion());
        reportingGroup.getAccountReport().add(account);
        crsJaxb.getCrsBody().get(0).setReportingGroup(new ArrayList<>());
        crsJaxb.getCrsBody().get(0).getReportingGroup().add(reportingGroup);
    }
*/

}
