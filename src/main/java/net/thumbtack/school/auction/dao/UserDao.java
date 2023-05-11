package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.User;

import java.util.UUID;

public interface UserDao {
    void login(User user) throws ServerException;
    void logout(UUID token) throws ServerException;
    User getByLogin(String login) throws ServerException;
    User getUserByToken(UUID uuid) throws ServerException;
}
