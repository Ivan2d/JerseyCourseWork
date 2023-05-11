package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Division;
import net.thumbtack.school.auction.model.Executor;

public interface DivisionDao {
    Division insert(Division division) throws ServerException;
    Division getById(int id) throws ServerException;
    void addExecutorToDivision(Executor executor, Division division) throws ServerException;
    void deleteExecutorFromDivision(Executor executor, Division division) throws ServerException;
}
