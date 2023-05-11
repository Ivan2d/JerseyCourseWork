package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.dao.DivisionDao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Division;
import net.thumbtack.school.auction.model.Executor;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DivisionDaoImpl extends BaseDaoImpl implements DivisionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DivisionDaoImpl.class);

    @Override
    public Division insert(Division division) throws ServerException {
        LOGGER.debug("DAO insert Customer {}", division);
        try (SqlSession sqlSession = getSession()) {
            try {
                getDivisionMapper(sqlSession).insert(division);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Customer {}", division, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return division;
    }

    @Override
    public Division getById(int id) throws ServerException {
        LOGGER.debug("DAO get Customer by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getDivisionMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Customer by login {}", id, ex);
            throw ex;
        }
    }

    @Override
    public void addExecutorToDivision(Executor executor, Division division) {
        LOGGER.debug("DAO add Customer to Application {} {}", executor, division);
        try (SqlSession sqlSession = getSession()) {
            getExecutorMapper(sqlSession).insert(executor);
            getDivisionMapper(sqlSession).addExecutorToDivision(executor, division);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't add Customer to Application {} {}", executor, division, ex);
            throw ex;
        }
    }

    @Override
    public void deleteExecutorFromDivision(Executor executor, Division division) throws ServerException {
        LOGGER.debug("DAO delete Application from Customer {} {}", executor, division);
        try (SqlSession sqlSession = getSession()) {
            getDivisionMapper(sqlSession).deleteExecutorFromDivision(executor, division);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete Application from Customer {} {}", executor, division, ex);
            throw ex;
        }
    }
}
