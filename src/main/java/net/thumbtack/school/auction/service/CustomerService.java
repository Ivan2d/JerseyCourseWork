package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.CustomerDao;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.CustomerDaoImpl;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mapper.mapstruct.ApplicationMapperFromRequest;
import net.thumbtack.school.auction.mapper.mapstruct.CustomerMapperFromRegister;
import net.thumbtack.school.auction.model.Application;
import net.thumbtack.school.auction.model.Customer;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.ServerException;
import java.util.UUID;

public class CustomerService {
    private CustomerDao customerDao = new CustomerDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private static Gson gson = new Gson();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;


    public ServerResponse registerUser(String requestJsonString) {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, RegisterDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Customer customer = CustomerMapperFromRegister.MAPPER.toCustomer(dtoRequest);
            customerDao.insert(customer);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getInfoApplication(String token, String requestJsonString) {
        try {
            Customer customer = getCustomerByToken(token);
            WatchStatusOfApplicationRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, WatchStatusOfApplicationRequest.class);
            ServiceUtils.checkInfoSomeLotRequest(dtoRequest);
            String info = customerDao.watchStatusOfApplication(dtoRequest.getIDApplication());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(info));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }
    public ServerResponse addApplication(String token, String requestJsonString) {
        try {
            Customer customer = getCustomerByToken(token);
            AddApplicationToOperatorRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, AddApplicationToOperatorRequest.class);
            ServiceUtils.checkAddApplicationRequest(dtoRequest);
            Application application = ApplicationMapperFromRequest.MAPPER.toApplication(dtoRequest);
            application.setCustomer(customer);
            customerDao.addApplication(customer, application);
            return new ServerResponse(CODE_SUCCESS,
                    gson.toJson(application.getId()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    private Customer getCustomerByToken(String token) throws ServerException {
        if (token == null){
            throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
        }
        User user = userDao.getUserByToken(UUID.fromString(token));
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        Customer customer = customerDao.getById(user.getId());
        if (customer == null) {
            throw new ServerException(ServerErrorCode.NOT_A_SELLER);
        }
        return customer;
    }

}
