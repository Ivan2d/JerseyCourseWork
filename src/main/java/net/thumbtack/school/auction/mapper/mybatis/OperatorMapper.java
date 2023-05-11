package net.thumbtack.school.auction.mapper.mybatis;

import net.thumbtack.school.auction.model.Operator;
import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OperatorMapper {

    @Insert("INSERT INTO operator (userid) VALUES ( #{operator.id} )")
    void insert(@Param("operator") Operator operator);

    @Select("SELECT * FROM user " +
            "WHERE id IN (" +
            "               SELECT userid FROM operator" +
            "               WHERE userid = #{id} " +
            "            )")
    User getById(int id);

    @Delete("DELETE FROM operator")
    void deleteAll();
}
