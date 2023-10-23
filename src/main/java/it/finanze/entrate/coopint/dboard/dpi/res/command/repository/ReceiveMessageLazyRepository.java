package it.finanze.entrate.coopint.dboard.dpi.res.command.repository;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.ReceiveMessageLazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceiveMessageLazyRepository extends JpaRepository<ReceiveMessageLazy, String>, JpaSpecificationExecutor<ReceiveMessageLazy> {

    @Query(value = "SELECT * " +
            "FROM RECEIVE_MESSAGE M " +
            "WHERE M.FISCAL_YEAR >= :startYear " +
            "AND M.FISCAL_YEAR <= :endYear", nativeQuery = true)
    List<ReceiveMessageLazy> getListMsgByDateFromTo(Integer startYear, Integer endYear);

    @Query(value = "SELECT QM.*" +
            "            FROM STATUS_MESSAGE SM" +
            "            INNER JOIN RECEIVE_MESSAGE QM on SM.MSG_UUID = QM.MSG_UUID" +
            "            WHERE SM.SM_STATUS_ID IN (:listStatusId)" +
            "            AND SM_CHANGE_DATE = (SELECT MAX(SM2.SM_CHANGE_DATE)" +
            "            FROM STATUS_MESSAGE SM2" +
            "            WHERE SM.MSG_UUID = SM2.MSG_UUID)" +
            " ORDER BY QM.RECEIVED_ON", nativeQuery = true)
    List<ReceiveMessageLazy> findMessageByStatusMessageStatusIdIn(List<Short> listStatusId);

    @Query(value = "SELECT QM.*" +
            "            FROM STATUS_MESSAGE SM" +
            "            INNER JOIN RECEIVE_MESSAGE QM on SM.MSG_UUID = QM.MSG_UUID" +
            "            WHERE SM.SM_STATUS_ID IN (:listStatusId)" +
            "            AND SM_CHANGE_DATE = (SELECT MAX(SM2.SM_CHANGE_DATE)" +
            "            FROM STATUS_MESSAGE SM2" +
            "            WHERE SM.MSG_UUID = SM2.MSG_UUID)" +
            " ORDER BY QM.RECEIVED_ON", nativeQuery = true)
    List<ReceiveMessageLazy> findMessageByStatusMessageStatusId(List<Short> listStatusId);

    @Query(value = "SELECT COUNT(QM.MSG_UUID) " +
            "FROM STATUS_MESSAGE SM " +
            "INNER JOIN RECEIVE_MESSAGE QM on SM.MSG_UUID = QM.MSG_UUID " +
            "WHERE SM.SM_STATUS_ID IN (:listStatusId) " +
            "AND SM_CHANGE_DATE = (SELECT MAX(SM2.SM_CHANGE_DATE) " +
            "FROM STATUS_MESSAGE SM2 " +
            "WHERE SM.MSG_UUID = SM2.MSG_UUID) " +
            "ORDER BY QM.RECEIVED_ON", nativeQuery = true)
    Integer countMessageByStatusMessageStatusId(List<Short> listStatusId);

}
