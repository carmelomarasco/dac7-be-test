package it.finanze.entrate.coopint.dboard.dpi.com.command.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/dpi-com/search-communication")
@Deprecated
public class SearchCommunicationQueryRest {

	 /**
     * EUROPEAN COMMISSION
     */
    @GetMapping(value = "/getBlobByComUuid/{comUuid}")
    public ResponseEntity<String> getBlobByComUuid(@PathVariable(value = "comUuid") String comUuid) {
    	// TODO verificare
        log.info("search for communication uuid ---> " + comUuid);
        
//        Optional<ComDataEntity> optComDataEntity = comDataRepository.findByComUuid(comUuid);
//        
//        if (optComDataEntity.isPresent()) {
//        	log.info("Found com...");
//        	String xmlBase64 = IOUtils.convertByteArrayToBase64(optComDataEntity.get().getComBlob());
//            return ResponseEntity.ok(xmlBase64);
//        } else {
//        	log.info("Not found com...");
//        	 return ResponseEntity.notFound().build();
//        }
       return null;
    }
}
