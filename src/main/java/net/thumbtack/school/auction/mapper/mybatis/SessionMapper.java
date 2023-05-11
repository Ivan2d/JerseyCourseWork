package net.thumbtack.school.auction.mapper.mybatis;

import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface SessionMapper {

    @Insert("INSERT INTO session (id, uuid) VALUES (#{buyer.id}, #{buyer.uuid})")
    void login(@Param("buyer") User user);
    @Delete("DELETE FROM session WHERE (uuid = #{token})")
    void deleteByToken(String token);
    @Select("SELECT id FROM user WHERE id IN (uuid = #{token})")
    Integer takeIdByLogin(String login);
    @Select("SELECT id FROM session WHERE (uuid = #{token})")
    Integer takeIdByUUID(String toString);
    @Delete("DELETE FROM session")
    void deleteAll();
}
