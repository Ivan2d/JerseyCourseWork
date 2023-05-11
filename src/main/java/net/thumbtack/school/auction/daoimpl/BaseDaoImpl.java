package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.mapper.mybatis.*;
import net.thumbtack.school.auction.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class BaseDaoImpl {
    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

    protected CustomerMapper getCustomerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(CustomerMapper.class);
    }

    protected ExecutorMapper getExecutorMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ExecutorMapper.class);
    }

    protected ApplicationMapper getApplicationMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ApplicationMapper.class);
    }
    protected OperatorMapper getOperatorMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(OperatorMapper.class);
    }

    protected DivisionMapper getDivisionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DivisionMapper.class);
    }

    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }
}
