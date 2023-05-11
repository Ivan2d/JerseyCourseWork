package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public void login(User user) throws ServerException {
        LOGGER.debug("DAO get Lot by Buyer {}", user);
        try (SqlSession sqlSession = getSession()) {
            Integer id = getUserMapper(sqlSession).find(user);
            if(id == null){
                throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            user.setId(id);
            getSessionMapper(sqlSession).login(user);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {}", user, ex);
            throw ex;
        }
    }

    @Override
    public void logout(UUID token) throws ServerException {
        LOGGER.debug("DAO get Lot by Buyer {}", token);
        try (SqlSession sqlSession = getSession()) {
            if(token == null){
                throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
            }
            getSessionMapper(sqlSession).deleteByToken(token.toString());
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {}", token, ex);
            throw ex;
        }
    }

    @Override
    public User getByLogin(String login) throws ServerException {
        LOGGER.debug("DAO get User {}", login);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getUserMapper(sqlSession).getByLogin(login);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete User {}", login, ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public User getUserByToken(UUID uuid) throws ServerException {
        LOGGER.debug("DAO get User {}", uuid);
        try (SqlSession sqlSession = getSession()) {
            try {
                Integer id = getSessionMapper(sqlSession).takeIdByUUID(uuid.toString());
                if(id == null){
                    throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
                }
                return getUserMapper(sqlSession).getById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete User {}", uuid, ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

}
