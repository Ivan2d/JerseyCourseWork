package net.thumbtack.school.auction.mapper.mybatis;

import net.thumbtack.school.auction.model.Application;
import net.thumbtack.school.auction.model.Customer;
import net.thumbtack.school.auction.model.Division;
import net.thumbtack.school.auction.model.Executor;
import org.apache.ibatis.annotations.*;

public interface ApplicationMapper {

    @Insert("INSERT INTO application (type, status) " +
            " VALUES ( #{application.type}, #{application.status} )")
    @Options(useGeneratedKeys = true, keyProperty = "application.id")
    Integer insert(@Param("application") Application application);


    @Insert("INSERT INTO customer_application (customerId, applicationId)" +
            " VALUES ( #{customer.id}, #{application} )")
    void addApplicationToCustomer(@Param("customer") Customer customer,
                                  @Param("application") Integer application);

    @Delete("DELETE FROM customer_application" +
            "WHERE applicationId = #{applicationId}")
    void deleteByIdFromCustomer(int applicationId);

    @Select("SELECT status FROM application " +
            " WHERE id IN (" +
            "               SELECT applicationId FROM customer_application " +
            "                WHERE applicationId = #{applicationId} " +
            "            )")
    String getStatusByApplicationId(int applicationId);

    @Insert("INSERT INTO executor_application (executorId, applicationId)" +
            "VALUES ( #{executor.id}, #{application.id} )")
    void addApplicationToExecutor(@Param("executor") Executor executor,
                                  @Param("application") Application application);

    @Delete("DELETE FROM application")
    void deleteAll();

}
