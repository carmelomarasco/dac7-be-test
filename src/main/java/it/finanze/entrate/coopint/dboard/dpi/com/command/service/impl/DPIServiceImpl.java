package it.finanze.entrate.coopint.dboard.dpi.com.command.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Address;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Amount;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Consideration;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Currency;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.EntitySeller;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Fees;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.FinancialIdentifier;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.GenerationIdentifier;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Gvs;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Identifier;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.ImmovableProperty;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.ImmovablePropertyPropertyListing;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.IndividualSeller;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Message;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageBody;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageBodyReportableSeller;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageMessageBody;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MiddleName;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MsCountryCode;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.NameOrganisation;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePerson;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonGenerationIdentifier;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonMiddleName;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonSuffix;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.NamePersonTitle;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.NumberOfActivity;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Organisation;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationAddress;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationCountry;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationIn;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationNameOrganisation;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationOrganisationIn;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationPlatformBusinessName;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OrganisationTin;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherActivity;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherPlatformOperator;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherPlatformOperatorsOtherRpo;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpo;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoAddress;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoCountry;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoNameOrganisation;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherRpoTin;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Party;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PermanentEstablishment;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Person;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonAddress;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonNamePerson;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonNationality;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonResCountry;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PersonTin;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PlatformBusinessName;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PlatformOperator;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PropertyListing;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.RelevantActivity;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.ReportableSeller;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardEntitySeller;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardEntitySellerOidFinancialIdentifier;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardIndividualSeller;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.StandardIndividualSellerFinancialIdentifier;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Suffix;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Taxes;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Tin;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Title;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.ConsiderationRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CountryRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CurrencyRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.InRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.MessageRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.MessageTypeIndicRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.MsCountryCodeRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.NexusRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DPIService;
import oecd.ties.dpi.v1.AddressFixType;
import oecd.ties.dpi.v1.AddressType;
import oecd.ties.dpi.v1.BirthPlaceType;
import oecd.ties.dpi.v1.ConsiderationType;
import oecd.ties.dpi.v1.CorrectableOtherRPOType;
import oecd.ties.dpi.v1.CorrectablePlatformOperatorType;
import oecd.ties.dpi.v1.CorrectableReportableSellerType;
import oecd.ties.dpi.v1.DPIBodyType;
import oecd.ties.dpi.v1.DPIOECD;
import oecd.ties.dpi.v1.FeesType;
import oecd.ties.dpi.v1.FinancialIdentifierType;
import oecd.ties.dpi.v1.GVSType;
import oecd.ties.dpi.v1.IdentifierType;
import oecd.ties.dpi.v1.MessageSpecType;
import oecd.ties.dpi.v1.MonAmntType;
import oecd.ties.dpi.v1.NameOrganisationType;
import oecd.ties.dpi.v1.NamePersonType;
import oecd.ties.dpi.v1.NumberOfActivitiesType;
import oecd.ties.dpi.v1.OrganisationINType;
import oecd.ties.dpi.v1.OrganisationPartyType;
import oecd.ties.dpi.v1.OtherActivitiesType;
import oecd.ties.dpi.v1.OtherPlatformOperatorsType;
import oecd.ties.dpi.v1.OtherRPOType;
import oecd.ties.dpi.v1.PermanentEstablishmentsType;
import oecd.ties.dpi.v1.PersonPartyType;
import oecd.ties.dpi.v1.PropertyListingType;
import oecd.ties.dpi.v1.ReportableSellerType;
import oecd.ties.dpi.v1.TINType;
import oecd.ties.dpi.v1.TaxesType;
import oecd.ties.dpistf.v1.DocSpecType;
import oecd.ties.isodpitypes.v1.CountryCodeType;
import oecd.ties.isodpitypes.v1.CurrCodeType;
import oecd.ties.isodpitypes.v1.MSCountryCodeType;

@Service
public class DPIServiceImpl implements DPIService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private MessageTypeIndicRepository messageTypeIndicRepository;
    @Autowired
    private InRepository inRepository;
    @Autowired
    private NexusRepository nexusRepository;
    @Autowired
    private MsCountryCodeRepository msCountryCodeRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ConsiderationRepository considerationRepository;
    /*@Autowired
    private StandardEntitySellerRepository standardEntitySellerRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private PlatformBusinessNameRepository platformBusinessNameRepository;
    @Autowired
    private PartyRepository partyRepository;*/

    @Autowired
    @Qualifier(value = "nationalEntityManager")
    //@PersistenceContext
    private EntityManager em;

    private static class MapContext{
        Instant createdAt;
        String createdBy;
        List<Object> insertList;

        public MapContext(Instant createdAt, String createdBy) {
            this.createdAt = createdAt;
            this.createdBy = createdBy;
            this.insertList = new ArrayList<>();
        }

        void insert(Object o){
            if( insertList.contains(o)){
                throw new IllegalArgumentException("Oggetto già presente");
            }
            insertList.add(o);
        }
    }

    @Transactional
    @Override
    public long saveMessage(DPIOECD xmlMessage, String createdBy ) {
        MapContext c = new MapContext(Instant.now(), createdBy);
        xml2jpa(c,xmlMessage);

        if( c.insertList.isEmpty()){
            throw new IllegalStateException("Lista di oggetti jpa vuota");
        }
        // rendere persistenti tutte le entità
        for( Object o : c.insertList){
            em.persist(o);
        }
        return ((Message)c.insertList.get(0)).getId();
    }

    // xml2jpa effettua mappatura campo/campo, se necessario crea bean destinazione, gestisce associazioni, effettua operazioni repo
    
    private void xml2jpa( MapContext c, DPIOECD xml ) {
        Message jpa = new Message();
        xml2jpa(c, xml,jpa);
    }

    private void xml2jpa( MapContext c, DPIOECD xml, Message jpa ){
        jpa.setVersion(xml.getVersion());
        xml2jpa(c, xml.getMessageSpec(), jpa);
        c.insert(jpa);
        for(DPIBodyType jchild : xml.getDPIBody()) {
            MessageBody jmb = new MessageBody();
            xml2jpa(c,jchild,jmb);
            // associazione
            MessageMessageBody link = new MessageMessageBody();
            link.setMessage(jpa);
            link.setMessageBody(jmb);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
    }

    private void xml2jpa(MapContext c, MessageSpecType xml, Message jpa ) {
        jpa.setSendingEntityIn(xml.getSendingEntityIN());
        jpa.setTransmittingCountry(findCountry(xml.getTransmittingCountry()));
        jpa.setReceivingCountry(findCountry(xml.getReceivingCountry()));
        if (xml.getMessageType() != null) {
            jpa.setMessageType(xml.getMessageType().value());
        }
        jpa.setWarning(xml.getWarning());
        jpa.setContact(xml.getContact());
        jpa.setMessageRefId(xml.getMessageRefId());
        if (xml.getMessageTypeIndic() != null) {
            jpa.setMessageTypeIndic(messageTypeIndicRepository.findByValue(xml.getMessageTypeIndic().value()));
        }
        jpa.setReportingPeriod(toLocalDate(xml.getReportingPeriod()));
        jpa.setTimestamp(toLocalDate(xml.getTimestamp()));
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
   }

    private void xml2jpa( MapContext c, DPIBodyType xml, MessageBody jpa ) {
        PlatformOperator platformOperator = new PlatformOperator();
        xml2jpa(c, xml.getPlatformOperator(), platformOperator);
        jpa.setPlatformOperator(platformOperator);

        OtherPlatformOperator otherPlatformOperators = new OtherPlatformOperator();
        xml2jpa(c, xml.getOtherPlatformOperators(), otherPlatformOperators);
        jpa.setOtherPlatformOperators(otherPlatformOperators);

        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);

        for( CorrectableReportableSellerType child : xml.getReportableSeller()){
            ReportableSeller rs = new ReportableSeller();
            xml2jpa(c,child,rs);
            MessageBodyReportableSeller link = new MessageBodyReportableSeller();
            link.setMessageBody(jpa);
            link.setReportableSeller(rs);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }

    }

    private void xml2jpa(MapContext c, CorrectableReportableSellerType xml, ReportableSeller jpa ) {
        xml2jpa(c, xml.getDocSpec(),jpa);
        xml2jpa(c,(ReportableSellerType)xml,jpa);
    }

    private void xml2jpa(MapContext c, ReportableSellerType xml, ReportableSeller jpa ) {
        RelevantActivity ra = new RelevantActivity();
        xml2jpa(c,xml.getRelevantActivities(),ra);
        jpa.setRelevantActivities(ra);

        Party p = new Party();
        xml2jpa(c,xml.getIdentity(),p);
        jpa.setParty(p);

        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, ReportableSellerType.Identity xml, Party jpa ) {
        ReportableSellerType.Identity.EntitySeller xmlEntitySeller = xml.getEntitySeller();
        if( xmlEntitySeller != null ){
            EntitySeller es = new EntitySeller();
            xml2jpa(c,xmlEntitySeller,es);
            jpa.setEntitySeller(es);
        }
        ReportableSellerType.Identity.IndividualSeller xmlIndividualSeller = xml.getIndividualSeller();
        if( xmlIndividualSeller != null ){
            IndividualSeller is = new IndividualSeller();
            xml2jpa(c,xmlIndividualSeller, is);
            jpa.setIndividualSeller(is);
        }
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, ReportableSellerType.Identity.EntitySeller xml, EntitySeller jpa) {
        // TODO Attenzione Standard e GVS sono alternativi
        ReportableSellerType.Identity.EntitySeller.Standard xmls = xml.getStandard();
        if( xmls != null ){
            StandardEntitySeller s = new StandardEntitySeller();
            xml2jpa(c,xmls,s);
            jpa.setStandard(s);
        }

        GVSType xmlgvs = xml.getGVS();
        if( xmlgvs != null ){
            Gvs gvs = new Gvs();
            xml2jpa(c,xmlgvs,gvs);
            jpa.setGvs(gvs);
        }
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }
    private void xml2jpa(MapContext c, ReportableSellerType.Identity.EntitySeller.Standard xml, StandardEntitySeller jpa) {
        Organisation o = new Organisation();
        xml2jpa(c,xml.getEntSellerID(),o);
        jpa.setEntSeller(o);

        PermanentEstablishmentsType xmlpet = xml.getPermanentEstablishments();
        if( xmlpet != null ){
            // TODO Mappatura fuori standard, legame multiplo non gestito
            for(MSCountryCodeType ms : xmlpet.getPermanentEstablishment()) {
                PermanentEstablishment pe = new PermanentEstablishment();
                pe.setPermanentEstablishment(findMsCountry(ms));
                pe.setCreatedBy(c.createdBy);
                pe.setCreatedOn(c.createdAt);
                c.insert(pe);
                jpa.setPermanentEstablishments(pe);
            }
        }
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);

        for( FinancialIdentifierType child : xml.getFinancialIdentifier()){
            FinancialIdentifier fi = new FinancialIdentifier();
            xml2jpa(c,child,fi);

            StandardEntitySellerOidFinancialIdentifier link = new StandardEntitySellerOidFinancialIdentifier();
            link.setStandardEntitySellerOid( jpa );
            link.setFinancialIdentifier(fi);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
    }

    private void xml2jpa(MapContext c, PermanentEstablishmentsType xml, PermanentEstablishment jpa) {
        // TODO mappato diversamente ma in modo errato
    }
    private void xml2jpa(MapContext c, FinancialIdentifierType xml, FinancialIdentifier jpa) {
        Identifier identifier = new Identifier();
        xml2jpa(c,xml.getIdentifier(),identifier);
        jpa.setIdentifier(identifier);

        jpa.setAccountHolderName(xml.getAccountHolderName());
        jpa.setOtherInfo(xml.getOtherInfo());
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }
    private void xml2jpa(MapContext c, IdentifierType xml, Identifier jpa) {
        //jpa.setIdentifierId(Long.toString(System.currentTimeMillis())); // ???
        jpa.setValue(xml.getValue());
        jpa.setAccountNumberType(xml.getAccountNumberType());
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, GVSType xml, Gvs jpa) {
        jpa.setNameGvs(xml.getNameGVS());
        jpa.setJurisdictionGvs(findCountry(xml.getJurisdictionGVS()));
        jpa.setReferenceGvs(xml.getReferenceGVS());
        jpa.setOtherTinGvs(xml.getOtherTINGVS());
        FinancialIdentifierType xmlfi = xml.getFinancialIdentifier();
        if( xmlfi != null ){
            FinancialIdentifier fi = new FinancialIdentifier();
            xml2jpa(c,xmlfi,fi);
            jpa.setFinancialIdentifier(fi);
        }
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, ReportableSellerType.Identity.IndividualSeller xml, IndividualSeller jpa) {
        // TODO Attenzione Standard e GVS sono alternativi
        ReportableSellerType.Identity.IndividualSeller.Standard xmls = xml.getStandard();
        if( xmls != null ){
            StandardIndividualSeller s = new StandardIndividualSeller();
            xml2jpa(c,xmls,s);
            jpa.setStandard(s);
        }

        GVSType xmlgvs = xml.getGVS();
        if( xmlgvs != null ){
            Gvs gvs = new Gvs();
            xml2jpa(c,xmlgvs,gvs);
            jpa.setGvs(gvs);
        }
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, ReportableSellerType.Identity.IndividualSeller.Standard xml, StandardIndividualSeller jpa) {
        Person o = new Person();
        xml2jpa(c,xml.getIndSellerID(),o);
        jpa.setPerson(o);

        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);

        for( FinancialIdentifierType child : xml.getFinancialIdentifier()){
            FinancialIdentifier fi = new FinancialIdentifier();
            xml2jpa(c,child,fi);

            StandardIndividualSellerFinancialIdentifier link = new StandardIndividualSellerFinancialIdentifier();
            link.setSellerStandardIndividualSeller( jpa );
            link.setFinancialIdentifier(fi);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
    }

    private void xml2jpa(MapContext c, PersonPartyType xml, Person jpa ) {
        jpa.setVat(xml.getVAT());
        xml2jpa(c,xml.getBirthInfo(),jpa);
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);

        for( CountryCodeType child:xml.getResCountryCode()){
            PersonResCountry link = new PersonResCountry();
            link.setCountry(findCountry(child));
            link.setPerson(jpa);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for( TINType child:xml.getTIN()){
            Tin tin = new Tin();
            xml2jpa(c,child,tin);

            PersonTin link = new PersonTin();
            link.setPerson(jpa);
            link.setTin(tin);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for( NamePersonType child : xml.getName()){
            NamePerson np = new NamePerson();
            xml2jpa(c,child,np);

            PersonNamePerson link = new PersonNamePerson();
            link.setPerson(jpa);
            link.setNamePerson(np);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for( AddressType child:xml.getAddress()){
            Address a = new Address();
            xml2jpa(c,child,a);

            PersonAddress link = new PersonAddress();
            link.setPerson(jpa);
            link.setAddress(a);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }

        for( CountryCodeType child:xml.getNationality()){
            PersonNationality link = new PersonNationality();
            link.setPerson(jpa);
            link.setCountry(findCountry(child));
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
    }

    private void xml2jpa(MapContext c, PersonPartyType.BirthInfo xml, Person jpa ) {
        jpa.setBirthDate(toLocalDate(xml.getBirthDate()));
        xml2jpa( c, xml.getBirthPlace(), jpa);
    }
    private void xml2jpa(MapContext c, BirthPlaceType xml, Person jpa ) {
        jpa.setBirthCity(xml.getCity());
        jpa.setBirthCitySubentity(xml.getCitySubentity());
        xml2jpa( c, xml.getCountryInfo(), jpa);
    }
    private void xml2jpa(MapContext c, BirthPlaceType.CountryInfo xml, Person jpa ) {
        jpa.setBirthCountry(findCountry(xml.getCountryCode()));
        jpa.setBirthFormerCountryName(xml.getFormerCountryName());
    }

    private void xml2jpa(MapContext c, NamePersonType xml, NamePerson jpa ) {
        jpa.setPrecedingTitle(xml.getPrecedingTitle());
        jpa.setFirstName(xml.getFirstName().getValue());
        jpa.setFirstNameType(xml.getFirstName().getXnlNameType());
        // TODO Attenzione middle name è mappato sia come link semplice che come tabella
        jpa.setNamePrefix(xml.getNamePrefix().getValue());
        jpa.setNamePrefixType(xml.getNamePrefix().getXnlNameType());
        jpa.setLastName(xml.getLastName().getValue());
        jpa.setLastNameType(xml.getLastName().getXnlNameType());
        // TODO Attenzione c'è un Suffix di troppo
        jpa.setGeneralSuffix(xml.getGeneralSuffix());
        // TODO manca nameType in jpa
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);

        for( String child:xml.getTitle()){
            Title t = new Title();
            t.setTitle(child);
            t.setCreatedBy(c.createdBy);
            t.setCreatedOn(c.createdAt);
            c.insert(t);

            NamePersonTitle link = new NamePersonTitle();
            link.setNamePerson(jpa);
            link.setTitle(t);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for(NamePersonType.MiddleName child:xml.getMiddleName()){
            MiddleName mn = new MiddleName();
            mn.setMiddleName(child.getValue());
            mn.setCreatedBy(c.createdBy);
            mn.setCreatedOn(c.createdAt);
            c.insert(mn);
            NamePersonMiddleName link = new NamePersonMiddleName();
            link.setNamePerson(jpa);
            link.setMiddleName(mn);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for( String child:xml.getGenerationIdentifier()){
            GenerationIdentifier gi = new GenerationIdentifier();
            gi.setGenerationIdentifier(child);
            gi.setCreatedBy(c.createdBy);
            gi.setCreatedOn(c.createdAt);
            c.insert(gi);
            NamePersonGenerationIdentifier link = new NamePersonGenerationIdentifier();
            link.setNamePerson(jpa);
            link.setGenerationIdentifier(gi);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for( String child:xml.getSuffix()){
            Suffix s = new Suffix();
            s.setSuffix(child);
            s.setCreatedBy(c.createdBy);
            s.setCreatedOn(c.createdAt);
            c.insert(s);
            NamePersonSuffix link = new NamePersonSuffix();
            link.setNamePerson(jpa);
            link.setSuffix(s);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
    }
    private void xml2jpa(MapContext c, ReportableSellerType.RelevantActivities xml, RelevantActivity jpa ) {
        ReportableSellerType.RelevantActivities.ImmovableProperty xmlImmovableProperty = xml.getImmovableProperty();
        if( xmlImmovableProperty != null ){
            ImmovableProperty ip = new ImmovableProperty();
            xml2jpa(c,xmlImmovableProperty,ip);
            jpa.setImmovableProperty(ip);
        }

        OtherActivitiesType xmlPersService = xml.getPersonalServices();
        if( xmlPersService != null ){
            OtherActivity oa = new OtherActivity();
            xml2jpa(c,xmlPersService, oa);
            jpa.setPersonalServices(oa);
        }

        OtherActivitiesType xmlSaleOfGoods = xml.getSaleOfGoods();
        if( xmlSaleOfGoods != null ){
            OtherActivity oa = new OtherActivity();
            xml2jpa(c,xmlSaleOfGoods, oa);
            jpa.setSaleOfGoods(oa);
        }

        OtherActivitiesType xmlTransp = xml.getTransportationRental();
        if( xmlTransp != null ){
            OtherActivity oa = new OtherActivity();
            xml2jpa(c,xmlTransp, oa);
            jpa.setTransportationRental(oa);
        }

        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, OtherActivitiesType xml, OtherActivity jpa ) {
        Consideration cons = new Consideration();
        xml2jpa(c, xml.getConsideration(),cons);
        jpa.setConsideration(cons);

        NumberOfActivity noa = new NumberOfActivity();
        xml2jpa(c,xml.getNumberOfActivities(), noa);
        jpa.setNumberOfActivities(noa);

        Fees fees = new Fees();
        xml2jpa(c,xml.getFees(),fees);
        jpa.setFees(fees);

        Taxes t = new Taxes();
        xml2jpa(c,xml.getTaxes(),t);
        jpa.setTaxes(t);

        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, ReportableSellerType.RelevantActivities.ImmovableProperty xml, ImmovableProperty jpa ) {
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);

        for( PropertyListingType child : xml.getPropertyListing()){
            PropertyListing pl = new PropertyListing();
            xml2jpa(c,child,pl);
            ImmovablePropertyPropertyListing link = new ImmovablePropertyPropertyListing();
            link.setImmovableProperty(jpa);
            link.setPropertyListing(pl);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
    }

    private void xml2jpa(MapContext c, PropertyListingType xml, PropertyListing jpa ) {
        Address a = new Address();
        xml2jpa(c, xml.getAddress(),a);
        jpa.setAddress(a);

        jpa.setLandRegistrationNumber(xml.getLandRegistrationNumber());

        Consideration cons = new Consideration();
        xml2jpa(c, xml.getConsideration(),cons);
        jpa.setConsideration(cons);

        NumberOfActivity noa = new NumberOfActivity();
        xml2jpa(c,xml.getNumberOfActivities(), noa);
        jpa.setNumberOfActivities(noa);

        Fees fees = new Fees();
        xml2jpa(c,xml.getFees(),fees);
        jpa.setFees(fees);

        Taxes t = new Taxes();
        xml2jpa(c,xml.getTaxes(),t);
        jpa.setTaxes(t);

        jpa.setPropertyType(xml.getPropertyType() == null ? null : xml.getPropertyType().value());
        jpa.setOtherPropertyType(xml.getOtherPropertyType());
        jpa.setRentedDays(xml.getRentedDays() == null ? null : xml.getRentedDays().shortValue());
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, FeesType xml, Fees jpa ) {
        Amount feesQ1 = new Amount();
        xml2jpa(c, xml.getFeesQ1(),feesQ1);
        jpa.setFeesQ1(feesQ1);

        Amount feesQ2 = new Amount();
        xml2jpa(c, xml.getFeesQ2(),feesQ2);
        jpa.setFeesQ2(feesQ2);

        Amount feesQ3 = new Amount();
        xml2jpa(c, xml.getFeesQ3(),feesQ3);
        jpa.setFeesQ3(feesQ3);

        Amount feesQ4 = new Amount();
        xml2jpa(c, xml.getFeesQ4(),feesQ4);
        jpa.setFeesQ4(feesQ4);

        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, MonAmntType xml, Amount jpa ) {
        jpa.setValue(xml.getValue().longValue());
        jpa.setCurrCode(findCurrency(xml.getCurrCode()));
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, TaxesType xml, Taxes jpa ) {
        Amount taxQ1 = new Amount();
        xml2jpa(c, xml.getTaxQ1(),taxQ1);
        jpa.setTaxQ1(taxQ1);

        Amount taxQ2= new Amount();
        xml2jpa(c, xml.getTaxQ2(),taxQ2);
        jpa.setTaxQ2(taxQ2);

        Amount taxQ3 = new Amount();
        xml2jpa(c, xml.getTaxQ3(),taxQ3);
        jpa.setTaxQ3(taxQ3);

        Amount taxQ4 = new Amount();
        xml2jpa(c, xml.getTaxQ4(),taxQ4);
        jpa.setTaxQ4(taxQ4);

        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }


    private void xml2jpa(MapContext c, ConsiderationType xml, Consideration jpa ) {
        Amount consQ1 = new Amount();
        xml2jpa(c, xml.getConsQ1(),consQ1);
        jpa.setConsQ1(consQ1);

        Amount consQ2 = new Amount();
        xml2jpa(c, xml.getConsQ2(),consQ2);
        jpa.setConsQ2(consQ2);

        Amount consQ3 = new Amount();
        xml2jpa(c, xml.getConsQ3(),consQ3);
        jpa.setConsQ3(consQ3);

        Amount consQ4 = new Amount();
        xml2jpa(c, xml.getConsQ4(),consQ4);
        jpa.setConsQ4(consQ4);

        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, NumberOfActivitiesType xml, NumberOfActivity jpa ) {
        jpa.setNumbQ1(xml.getNumbQ1().longValue());
        jpa.setNumbQ2(xml.getNumbQ2().longValue());
        jpa.setNumbQ3(xml.getNumbQ3().longValue());
        jpa.setNumbQ4(xml.getNumbQ4().longValue());
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, DocSpecType xml, ReportableSeller jpa ) {
        jpa.setDocTypeIndic(xml.getDocTypeIndic().value());
        jpa.setDocRefId(xml.getDocRefId());
        // TODO Manca CorrMessageRefId
        jpa.setCorrDocRefId(xml.getCorrDocRefId());
    }

    private void xml2jpa(MapContext c, CorrectablePlatformOperatorType xml, PlatformOperator jpa ) {
        if (xml.getDocSpec() != null) {
            xml2jpa(c, xml.getDocSpec(), jpa);
        }
        Organisation organisation = new Organisation();
        xml2jpa(c,xml, organisation);
        jpa.setOrganisationParty(organisation);
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, OtherPlatformOperatorsType xml, OtherPlatformOperator jpa ) {
        CorrectableOtherRPOType assumingPlatformOperator = xml.getAssumingPlatformOperator();
        if( assumingPlatformOperator != null ){
            OtherRpo rpo = new OtherRpo();
            xml2jpa(c, assumingPlatformOperator, rpo);
            jpa.setAssumingPlatformOperator(rpo);
        }
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
        for( CorrectableOtherRPOType child : xml.getAssumedPlatformOperator()){
            OtherRpo rpo = new OtherRpo();
            xml2jpa(c,child,rpo);
            OtherPlatformOperatorsOtherRpo link = new OtherPlatformOperatorsOtherRpo();
            link.setOtherPlatformOperators(jpa);
            link.setOtherRpo(rpo);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
    }

    private void xml2jpa(MapContext c, CorrectableOtherRPOType xml, OtherRpo jpa ) {
        xml2jpa(c, xml.getDocSpec(), jpa);
        xml2jpa(c, (OtherRPOType) xml, jpa);
    }

    private void xml2jpa(MapContext c, OtherRPOType xml, OtherRpo jpa ) {
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
        for( CountryCodeType child : xml.getResCountryCode()){
            OtherRpoCountry link = new OtherRpoCountry();
            link.setOtherRpo(jpa);
            link.setCountry(findCountry(child));
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for( TINType child : xml.getTIN()){
            Tin tin = new Tin();
            xml2jpa(c,child,tin);
            OtherRpoTin link = new OtherRpoTin();
            link.setOtherRpo(jpa);
            link.setTin(tin);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        // TODO bastava link semplice senza tabella
        NameOrganisation no = new NameOrganisation();
        xml2jpa(c,xml.getName(),no);
        OtherRpoNameOrganisation link = new OtherRpoNameOrganisation();
        link.setNameOrganisation(no);
        link.setOtherRpo(jpa);
        link.setCreatedBy(c.createdBy);
        link.setCreatedOn(c.createdAt);
        c.insert(link);

        // TODO bastava link semplice senza tabella
        Address a = new Address();
        xml2jpa(c,xml.getAddress(),a);
        OtherRpoAddress link2 = new OtherRpoAddress();
        link2.setOtherRpo(jpa);
        link2.setAddress(a);
        link2.setCreatedBy(c.createdBy);
        link2.setCreatedOn(c.createdAt);
        c.insert(link2);
    }

    private void xml2jpa(MapContext c, DocSpecType xml, OtherRpo jpa ) {
        jpa.setDocTypeIndic(xml.getDocTypeIndic().value());
        jpa.setDocRefId(xml.getDocRefId());
        // TODO Manca CorrMessageRefId
        jpa.setCorrDocRefId(xml.getCorrDocRefId());
    }

    private void xml2jpa(MapContext c, OrganisationPartyType xml, Organisation jpa ) {
        jpa.setVat(xml.getVAT());
        if (xml.getNexus() != null) {
            jpa.setNexus(nexusRepository.findByValue(xml.getNexus().value()));
        }
        jpa.setAssumedReporting(xml.isAssumedReporting());
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
        for( CountryCodeType child : xml.getResCountryCode()){
            OrganisationCountry link = new OrganisationCountry();
            link.setOrganisation(jpa);
            link.setCountry(findCountry(child));
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for(TINType child : xml.getTIN()){
            Tin tin = new Tin();
            xml2jpa(c,child,tin);
            OrganisationTin link = new OrganisationTin();
            link.setOrganisation(jpa);
            link.setTin(tin);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for( OrganisationINType child : xml.getIN()){
            OrganisationIn oin = new OrganisationIn();
            xml2jpa(c,child,oin);
            OrganisationOrganisationIn link = new OrganisationOrganisationIn();
            link.setOrganisation(jpa);
            link.setOrganisationIn(oin);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for( NameOrganisationType child: xml.getName()){
            NameOrganisation no = new NameOrganisation();
            xml2jpa(c,child,no);
            OrganisationNameOrganisation link = new OrganisationNameOrganisation();
            link.setOrganisation(jpa);
            link.setNameOrganisation(no);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for(String child:xml.getPlatformBusinessName()){
            PlatformBusinessName pbm = new PlatformBusinessName();
            pbm.setPlatformBusinessName(child);
            pbm.setCreatedBy(c.createdBy);
            pbm.setCreatedOn(c.createdAt);
            c.insert(pbm);
            OrganisationPlatformBusinessName link = new OrganisationPlatformBusinessName();
            link.setOrganisation(jpa);
            link.setPlatformBusinessName(pbm);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
        for(AddressType child : xml.getAddress()) {
            Address address = new Address();
            xml2jpa(c, child, address);
            OrganisationAddress link = new OrganisationAddress();
            link.setOrganisation(jpa);
            link.setAddress(address);
            link.setCreatedBy(c.createdBy);
            link.setCreatedOn(c.createdAt);
            c.insert(link);
        }
    }

    private void xml2jpa(MapContext c, NameOrganisationType xml, NameOrganisation jpa) {
        jpa.setValue(xml.getValue());
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, OrganisationINType xml, OrganisationIn jpa) {
        jpa.setValue(xml.getValue());
        jpa.setIssuedBy(findCountry(xml.getIssuedBy()));
        jpa.setIn(inRepository.findByValue(xml.getINType().value()));
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, TINType xml, Tin jpa){
        jpa.setValue(xml.getValue());
        jpa.setUnknown(xml.isUnknown());
        jpa.setIssuedBy(findCountry(xml.getIssuedBy()));
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, DocSpecType xml, PlatformOperator jpa ) {
        if (xml.getDocTypeIndic() != null) {
            jpa.setDocTypeIndic(xml.getDocTypeIndic().value());
        }
        jpa.setDocRefId(xml.getDocRefId());
        jpa.setCorrDocRefId(xml.getCorrDocRefId());
    }

    private void xml2jpa(MapContext c, AddressType xml, Address jpa) {
        jpa.setCountryCode(findCountry(xml.getCountryCode()));
        if (xml.getLegalAddressType() != null) {
            jpa.setLegalAddressType(xml.getLegalAddressType().value());
        }
        jpa.setAddressFree(xml.getAddressFree());
        // TODO ??? jpa.setAddressFixId();
        if (xml.getAddressFix() != null) {
            xml2jpa(c, xml.getAddressFix(), jpa);
        }
        jpa.setCreatedBy(c.createdBy);
        jpa.setCreatedOn(c.createdAt);
        c.insert(jpa);
    }

    private void xml2jpa(MapContext c, AddressFixType xml, Address jpa) {
        jpa.setStreet(xml.getStreet());
        jpa.setBuildingIdentifier(xml.getBuildingIdentifier());
        jpa.setSuiteIdentifier(xml.getSuiteIdentifier());
        jpa.setFloorIdentifier(xml.getFloorIdentifier());
        jpa.setDistrictName(xml.getDistrictName());
        jpa.setPob(xml.getPOB());
        jpa.setPostCode(xml.getPostCode());
        jpa.setCity(xml.getCity());
        jpa.setCountrySubentity(xml.getCountrySubentity());
    }

    private LocalDate toLocalDate(XMLGregorianCalendar xgc) {
        return xgc != null ? LocalDate.of(xgc.getYear(), xgc.getMonth(), xgc.getDay()) : null;
    }

    private Country findCountry(CountryCodeType cc){
        return (cc == null) ? null : countryRepository.findByValue(cc.value());
    }

    private Currency findCurrency(CurrCodeType cc ){
        return (cc == null) ? null : currencyRepository.findByValue(cc.value());
    }

    private MsCountryCode findMsCountry(MSCountryCodeType cc){
        return (cc == null) ? null : msCountryCodeRepository.findByValue(cc.value());
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
