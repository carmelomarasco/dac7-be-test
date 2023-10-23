package it.finanze.entrate.coopint.dboard.dpi.common.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConfigurationProperties(prefix = "topic")
public class TopicProperties {

    public static String DPI_NAT_ERR;
    public static String DPI_NAT_COMMAND;
    public static String DPI_NAT_EVENT;
    public static String DPI_NR_ERR;
    public static String DPI_NR_COMMAND;
    public static String DPI_NR_EVENT;
    public static String DPI_RES_ERR;
    public static String DPI_RES_COMMAND;
    public static String DPI_RES_EVENT;
       
    public static String BOUNDARY_SERVICE;
    //public static String DUE_DATE_SERVICE;
    public static String EXCHANGE_RES_EVENT;
    public static String EXCHANGE_NR_EVENT;
	public static void setDPI_NAT_ERR(String dPI_NAT_ERR) {
		DPI_NAT_ERR = dPI_NAT_ERR;
	}
	public static void setDPI_NAT_COMMAND(String dPI_NAT_COMMAND) {
		DPI_NAT_COMMAND = dPI_NAT_COMMAND;
	}
	public static void setDPI_NAT_EVENT(String dPI_NAT_EVENT) {
		DPI_NAT_EVENT = dPI_NAT_EVENT;
	}
	public static void setDPI_NR_ERR(String dPI_NR_ERR) {
		DPI_NR_ERR = dPI_NR_ERR;
	}
	public static void setDPI_NR_COMMAND(String dPI_NR_COMMAND) {
		DPI_NR_COMMAND = dPI_NR_COMMAND;
	}
	public static void setDPI_NR_EVENT(String dPI_NR_EVENT) {
		DPI_NR_EVENT = dPI_NR_EVENT;
	}
	public static void setDPI_RES_ERR(String dPI_RES_ERR) {
		DPI_RES_ERR = dPI_RES_ERR;
	}
	public static void setDPI_RES_COMMAND(String dPI_RES_COMMAND) {
		DPI_RES_COMMAND = dPI_RES_COMMAND;
	}
	public static void setDPI_RES_EVENT(String dPI_RES_EVENT) {
		DPI_RES_EVENT = dPI_RES_EVENT;
	}
	public static void setBOUNDARY_SERVICE(String bOUNDARY_SERVICE) {
		BOUNDARY_SERVICE = bOUNDARY_SERVICE;
	}
	public static void setEXCHANGE_RES_EVENT(String eXCHANGE_RES_EVENT) {
		EXCHANGE_RES_EVENT = eXCHANGE_RES_EVENT;
	}
	public static void setEXCHANGE_NR_EVENT(String eXCHANGE_NR_EVENT) {
		EXCHANGE_NR_EVENT = eXCHANGE_NR_EVENT;
	}
        
    
    
	

    
}
