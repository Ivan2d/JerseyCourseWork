package net.thumbtack.school.auction.mapper.mybatis;

import net.thumbtack.school.auction.model.Division;
import net.thumbtack.school.auction.model.Executor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface DivisionMapper {

    @Insert("INSERT INTO division (name)" +
            "VALUES (#{division.name})")
    void insert(Division division);

    @Select("SELECT * FROM division WHERE id = #{id}")
    Division getById(int id);

    @Insert("INSERT INTO executor_division (executorId, divisionId)" +
            "VALUES (executor.id, division.id)")
    void addExecutorToDivision(Executor executor, Division division);

    @Select("SELECT executorId FROM executor_division " +
            " WHERE id = #{division.id} " +
            " AND executorId = #{executor.id}")
    Integer takeExecutorFromDivision(@Param("division") Division division,
                                     @Param("executor") Executor executor);

    @Delete("DELETE FROM executor_division " +
            "WHERE executor = #{executor.id}, #{division.id}")
    void deleteExecutorFromDivision(Executor executor, Division division);
}
