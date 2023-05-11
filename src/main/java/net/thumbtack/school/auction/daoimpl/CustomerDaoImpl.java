package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.dao.CustomerDao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Application;
import net.thumbtack.school.auction.model.Customer;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDaoImpl extends BaseDaoImpl implements CustomerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoImpl.class);
    @Override
    public Customer insert(Customer user) throws ServerException {
        LOGGER.debug("DAO insert Customer {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(user);
                getCustomerMapper(sqlSession).insert(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Customer {}", user, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return user;
    }

    @Override
    public Customer getById(int id) {
        LOGGER.debug("DAO get Customer by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getCustomerMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Customer by login {}", id, ex);
            throw ex;
        }
    }

    @Override
    public void addApplication(Customer customer, Application application) {
        LOGGER.debug("DAO add Customer to Application {} {}", customer, application);
        try (SqlSession sqlSession = getSession()) {
            try {
                getApplicationMapper(sqlSession).insert(application);
                getApplicationMapper(sqlSession).addApplicationToCustomer(customer, application.getId());
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't add Customer to Application {} {}", customer, application, ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }


    @Override
    public void deleteApplication(int id) {
        LOGGER.debug("DAO delete Application from Customer {}", id);
        try (SqlSession sqlSession = getSession()) {
            getApplicationMapper(sqlSession).deleteByIdFromCustomer(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete Application from Customer {}", id, ex);
            throw ex;
        }
    }

    @Override
    public String watchStatusOfApplication(int id) throws ServerException {
        LOGGER.debug("DAO watch status of application {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getApplicationMapper(sqlSession).getStatusByApplicationId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete Application from Customer {}", id, ex);
            throw ex;
        }
    }
}
