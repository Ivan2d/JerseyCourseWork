package net.thumbtack.school.auction.mapper.mybatis;
import net.thumbtack.school.auction.model.Executor;
import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ExecutorMapper {

    @Insert("INSERT INTO executor (userid) VALUES ( #{executor.id} )")
    Integer insert(@Param("executor") Executor executor);

    @Select("SELECT * FROM user " +
            "WHERE id IN (" +
            "               SELECT userid FROM executor" +
            "               WHERE userid = #{id} " +
            "            )")
    Executor getById(int id);

    @Delete("DELETE FROM executor")
    void deleteAll();

}
