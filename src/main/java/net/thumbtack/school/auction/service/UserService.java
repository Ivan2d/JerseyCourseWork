package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.dto.response.TokenDtoResponse;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.mapper.mapstruct.UserMapperFromLogin;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;

import java.util.UUID;

public class UserService {
    private UserDao userDao = new UserDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse login(String requestJsonString) {
        try {
            LoginDtoRequest loginDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            User user = UserMapperFromLogin.MAPPER.toUser(loginDtoRequest);
            user.setUuid(UUID.randomUUID().toString());
            userDao.login(user);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new TokenDtoResponse(user.getUuid())));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

    public ServerResponse logout(UUID token)
    {
        try {
            userDao.logout(token);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        }
        catch (ServerException e){
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

}
