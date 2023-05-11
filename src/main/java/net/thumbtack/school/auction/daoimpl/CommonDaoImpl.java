package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.dao.CommonDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDaoImpl extends BaseDaoImpl implements CommonDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);
    @Override
    public void clear() {
        LOGGER.debug("Clear Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteAll();
                getOperatorMapper(sqlSession).deleteAll();
                getCustomerMapper(sqlSession).deleteAll();
                getExecutorMapper(sqlSession).deleteAll();
                getSessionMapper(sqlSession).deleteAll();
                getApplicationMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
