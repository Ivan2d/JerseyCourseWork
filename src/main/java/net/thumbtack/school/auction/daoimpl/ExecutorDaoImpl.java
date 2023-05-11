package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.dao.ExecutorDao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Application;
import net.thumbtack.school.auction.model.Division;
import net.thumbtack.school.auction.model.Executor;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorDaoImpl extends BaseDaoImpl implements ExecutorDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorDaoImpl.class);
    @Override
    public Executor insert(Executor executor) throws ServerException {
        LOGGER.debug("DAO insert Operator {}", executor);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(executor);
                getExecutorMapper(sqlSession).insert(executor);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Operator {}", executor, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return executor;
    }

    @Override
    public Executor getById(int id) {
        LOGGER.debug("DAO get Customer by login {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getExecutorMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Customer by login {}", id, ex);
            throw ex;
        }
    }

    @Override
    public void takeApplication(Executor executor, Application application) {
        LOGGER.debug("DAO add Executor to Application {} {}", executor, application);
        try (SqlSession sqlSession = getSession()) {
            getApplicationMapper(sqlSession).addApplicationToExecutor(executor, application);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't add Executor to Application {} {}", executor, application, ex);
            throw ex;
        }
    }

    @Override
    public void giveApplicationToAnotherDivision(Application application, Division division, Executor executor) {
        LOGGER.debug("DAO give Application to Division {} {}", application, division);
        try (SqlSession sqlSession = getSession()) {
            Integer executorId = getDivisionMapper(sqlSession)
                    .takeExecutorFromDivision(division, executor);
            getApplicationMapper(sqlSession).addApplicationToExecutor(executor, application);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't add Executor to Application {} {} {}", application, division, ex, ex);
            throw ex;
        }
    }
}
