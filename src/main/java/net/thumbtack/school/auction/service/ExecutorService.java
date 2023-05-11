package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.DivisionDao;
import net.thumbtack.school.auction.dao.ExecutorDao;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.DivisionDaoImpl;
import net.thumbtack.school.auction.daoimpl.ExecutorDaoImpl;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mapper.mapstruct.ApplicationMapperFromRequest;
import net.thumbtack.school.auction.mapper.mapstruct.ExecutorMapperFromRegister;
import net.thumbtack.school.auction.model.Application;
import net.thumbtack.school.auction.model.Division;
import net.thumbtack.school.auction.model.Executor;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.ServerException;

import java.util.UUID;

public class ExecutorService {

    private ExecutorDao executorDao = new ExecutorDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private DivisionDao divisionDao = new DivisionDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse registerUser(String requestJsonString) {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, RegisterDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Executor executor = ExecutorMapperFromRegister.MAPPER.toExecutor(dtoRequest);
            executorDao.insert(executor);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse takeApplication(String token, String requestJsonString) {
        try {
            Executor executor = getExecutorByToken(token);
            TakeHandleApplicationRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, TakeHandleApplicationRequest.class);
            Application application = ApplicationMapperFromRequest.MAPPER.toApplication(dtoRequest);
            executorDao.takeApplication(executor, application);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse giveApplicationToAnotherDivision(String token, String requestJsonString) {
        try {
            Executor executor = getExecutorByToken(token);
            GiveApplicationToAnotherDivisionRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, GiveApplicationToAnotherDivisionRequest.class);
            if(divisionDao.getById(dtoRequest.getIDDivision()) == null) {
                throw new ServerException(ServerErrorCode.DIVISION_NOT_EXIST);
            }
            Application application = new Application(dtoRequest.getIDApplication());
            Division division = new Division(dtoRequest.getIDDivision());
            executorDao.giveApplicationToAnotherDivision(application, division, executor);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    private Executor getExecutorByToken(String token) throws ServerException {
        if (token == null){
            throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
        }
        User user = userDao.getUserByToken(UUID.fromString(token));
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        Executor executor = executorDao.getById(user.getId());
        if (executor == null) {
            throw new ServerException(ServerErrorCode.NOT_A_SELLER);
        }
        return executor;
    }

}
