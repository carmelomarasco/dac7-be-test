package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.FiscalYear;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class FiscalYearDto {
	
	public static SimpleDateFormat onlyDateItalianFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
    private Long fiscalYear;
    private String expirationDate;
    private String extendedExpirationDate;
    private boolean expired;
    private String today;
    private String tomorrow;

    private FiscalYearDto() {
    	super();
    	Date todayDate = new Date();
    	this.setToday(FiscalYearDto.onlyDateItalianFormatter.format(todayDate));
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(todayDate);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	this.setTomorrow(FiscalYearDto.onlyDateItalianFormatter.format(cal.getTime()));
    }
    
    private FiscalYearDto(FiscalYear fyentity) {
    	this();
    	if (fyentity != null) {
        	this.setFiscalYear(fyentity.getYear());
        	this.setExpirationDate(FiscalYearDto.onlyDateItalianFormatter.format(fyentity.getExpirationDate()));
        	this.setExtendedExpirationDate(FiscalYearDto.onlyDateItalianFormatter.format(fyentity.getExtendedExpDate()));
        	this.setExpired(fyentity.isExpired());
    	}
    }
    
    public static FiscalYearDto getDto(FiscalYear entity) {
    	if (entity != null) {
    		return new FiscalYearDto(entity);
    	} else {
    		return null;
    	}
    }
    
    public static List<FiscalYearDto> getDtos(List<FiscalYear> ents) {
    	List<FiscalYearDto> ret = new LinkedList<>();
    	if (!CollectionUtils.isEmpty(ents)) {
    		for (FiscalYear fy: ents) {
    			ret.add(FiscalYearDto.getDto(fy));
    		}
    	}
    	return ret;
    }

}