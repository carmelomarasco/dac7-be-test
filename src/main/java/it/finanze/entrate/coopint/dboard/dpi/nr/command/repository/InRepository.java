package it.finanze.entrate.coopint.dboard.dpi.nr.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository (value = "inRepositoryNR")
public interface InRepository extends JpaRepository<In, Long> {

    @Query (value = "select * from \"IN\" where IN_ID=:inId", nativeQuery = true)
    In getInById(@Param ("inId") Long id);


    In findByValue(String value);

}