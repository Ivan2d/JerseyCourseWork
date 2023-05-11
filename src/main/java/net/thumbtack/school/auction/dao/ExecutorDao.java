package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Application;
import net.thumbtack.school.auction.model.Division;
import net.thumbtack.school.auction.model.Executor;

public interface ExecutorDao {
    Executor insert(Executor operator) throws ServerException;
    Executor getById(int id) throws ServerException;
    void takeApplication(Executor executor, Application application) throws ServerException;
    void giveApplicationToAnotherDivision(Application application, Division division, Executor executor);
}
