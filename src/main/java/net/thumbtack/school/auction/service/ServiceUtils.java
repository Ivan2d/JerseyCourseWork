package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.StringUtils.isBlank;


public class ServiceUtils {
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    private static final Gson GSON = new Gson();

    public static <T> T getObjectFromJson(String requestJsonString, Class<T> classOfT) throws ServerException {
            if (isBlank(requestJsonString)) {
                throw new ServerException(ServerErrorCode.WRONG_JSON);
            }
            return GSON.fromJson(requestJsonString, classOfT);
    }

    public static void checkRequest(RegisterDtoRequest request) throws ServerException {
        if (request.getFirstname() == null || StringUtils.isEmpty(request.getFirstname())) {
            throw new ServerException(ServerErrorCode.EMPTY_FIRST_NAME);
        }
        if (request.getLastname() == null || StringUtils.isEmpty(request.getLastname())) {
            throw new ServerException(ServerErrorCode.EMPTY_LAST_NAME);
        }
        if (request.getLogin() == null || StringUtils.isEmpty(request.getLogin())) {
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        }
        if (request.getLogin().length() < MIN_LOGIN_LEN) {
            throw new ServerException(ServerErrorCode.SHORT_LOGIN);
        }
        if (request.getPassword() == null || StringUtils.isEmpty(request.getPassword())) {
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
        }
        if (request.getPassword().length() < MIN_PASSWORD_LEN) {
            throw new ServerException(ServerErrorCode.SHORT_PASSWORD);
        }
    }


    public static void checkInfoSomeLotRequest(WatchStatusOfApplicationRequest dtoRequest) throws ServerException {
        if(dtoRequest.getIDApplication() <= 0){
            throw new ServerException(ServerErrorCode.ID_LESSER_THAN_ZERO);
        }
    }

    public static void checkAddApplicationRequest(AddApplicationToOperatorRequest dtoRequest) throws ServerException {
        if(dtoRequest.getType() == null ||dtoRequest.getType().equals("")) {
            throw new ServerException(ServerErrorCode.EMPTY_FIRST_NAME);
        }
        if(dtoRequest.getStatus() == null ||dtoRequest.getStatus().equals("")){
            throw new ServerException(ServerErrorCode.EMPTY_DESCRIPTION);
        }

    }
}

