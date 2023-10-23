package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryServiceBean {

    private String countryName;
    private String codiceIso;
    private String appl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataInizioScambio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataFineScambio;
    private Long limite;
    private String folderCts;
    private String ccnCsiGatewayName;
    private Boolean dataIniziaValorizzata;
    private Long accountsThreshold;
    private Long idFather; // padre
    private Long idChild; // figlio
    private String countryNameFather;
    private String isoCodeFather;
    private boolean isChild; // e' true se id e idChild sono diversi


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryServiceBean that = (CountryServiceBean) o;
        return codiceIso.equals(that.codiceIso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceIso);
    }
}
