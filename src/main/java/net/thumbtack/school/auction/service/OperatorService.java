package net.thumbtack.school.auction.service;

import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.OperatorDao;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.OperatorDaoImpl;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.mapper.mapstruct.OperatorMapperFromRegister;
import net.thumbtack.school.auction.model.*;
import net.thumbtack.school.auction.server.ServerResponse;

import java.util.UUID;

public class OperatorService {
    private OperatorDao operatorDao = new OperatorDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse registerUser(String requestJsonString) {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, RegisterDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Operator operator = OperatorMapperFromRegister.MAPPER.toOperator(dtoRequest);
            operatorDao.insert(operator);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    private Operator getOperatorByToken(String token) throws ServerException {
        if (token == null){
            throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
        }
        User user = userDao.getUserByToken(UUID.fromString(token));
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        if (!(user instanceof Operator)) {
            throw new ServerException(ServerErrorCode.NOT_A_SELLER);
        }
        return (Operator) user;
    }

}
