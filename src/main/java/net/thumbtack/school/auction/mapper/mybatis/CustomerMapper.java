package net.thumbtack.school.auction.mapper.mybatis;

import net.thumbtack.school.auction.model.Customer;
import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CustomerMapper {

    @Insert("INSERT INTO customer (userid) VALUES ( #{customer.id} )")
    Integer insert(@Param("customer") Customer customer);

    @Select("SELECT * FROM user " +
            "WHERE id IN (" +
            "               SELECT userid FROM customer" +
            "               WHERE userid = #{id} " +
            "            )")
    Customer getById(int id);

    @Delete("DELETE FROM customer")
    void deleteAll();
}
