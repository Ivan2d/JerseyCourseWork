package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.dao.OperatorDao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Application;
import net.thumbtack.school.auction.model.Operator;
import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatorDaoImpl extends BaseDaoImpl implements OperatorDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorDaoImpl.class);
    @Override
    public Operator insert(Operator operator) throws ServerException {
        LOGGER.debug("DAO insert Customer {}", operator);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(operator);
                getOperatorMapper(sqlSession).insert(operator);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Customer {}", operator, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return operator;
    }

    @Override
    public User getById(int id) throws ServerException {
        LOGGER.debug("DAO get Customer by login {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getOperatorMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Customer by login {}", id, ex);
            throw ex;
        }
    }
}
