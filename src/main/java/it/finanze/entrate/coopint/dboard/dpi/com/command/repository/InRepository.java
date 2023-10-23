package it.finanze.entrate.coopint.dboard.dpi.com.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.In;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

@Repository (value = "inRepositoryNazionale")
public interface InRepository extends JpaRepository<In, Long> {

    @Query (value = "select * from \"IN\" where IN_ID=:inId", nativeQuery = true)
    In getInById(@Param ("inId") Long id);


    In findByValue(String value);

}