package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Operator;
import net.thumbtack.school.auction.model.User;

public interface OperatorDao {
    Operator insert(Operator operator) throws ServerException;
    User getById(int id) throws ServerException;
}
