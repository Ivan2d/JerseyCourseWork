import com.google.gson.Gson;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.server.Server;
import net.thumbtack.school.auction.utils.MyBatisUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestAuction {

    private final Server server = new Server();
    private final Gson gson = new Gson();
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;

    @BeforeAll
    public static void setUp() {
        MyBatisUtils.initSqlSessionFactory();
    }

    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }

    @Test
    public void testRegisterUser() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosd",
                "firetreesd",
                "nicool2003"
        );

        ServerResponse serverResponseRegister = server.registerCustomer(gson.toJson(dtoRequest));
        Assertions.assertEquals(serverResponseRegister.getResponseData(), gson
                .toJson(new EmptySuccessDtoResponse()));
        Assertions.assertEquals(serverResponseRegister.getResponseCode(), SUCCESS_CODE);
    }

    @Test
    public void testRegisterUserWithNullOrEmptyName() {
        RegisterDtoRequest nullFirstnameDtoRequest = new RegisterDtoRequest(
                null,
                "s",
                "ssssssssss",
                "12134321",
                "nicool@mail.ru"
        );

        RegisterDtoRequest emptyFirstnameDtoRequest = new RegisterDtoRequest(
                "",
                "s",
                "sssssssss",
                "1232321312",
                "Nicool2002@mail.ru"
        );

        RegisterDtoRequest nullLastnameDtoRequest = new RegisterDtoRequest(
                "sdsd",
                null,
                "ssssssssss",
                "12134321",
                "Nikita@mail.ru"
        );

        RegisterDtoRequest emptyLastnameDtoRequest = new RegisterDtoRequest(
                "sdsd",
                "",
                "sssssssss",
                "1232321312",
                "Nik@mail.ru"
        );

        ServerResponse serverNullNameResponse = server.registerCustomer(gson.toJson(nullFirstnameDtoRequest));
        Assertions.assertEquals(serverNullNameResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(serverNullNameResponse.getResponseData(), "Empty first name");

        ServerResponse serverEmptyNameResponse = server.registerCustomer(gson.toJson(emptyFirstnameDtoRequest));
        Assertions.assertEquals(serverEmptyNameResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(serverEmptyNameResponse.getResponseData(), "Empty first name");

        ServerResponse serverNullLastnameResponse = server.registerCustomer(gson.toJson(nullLastnameDtoRequest));
        Assertions.assertEquals(serverNullLastnameResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(serverNullLastnameResponse.getResponseData(), "Empty last name");

        ServerResponse serverEmptyLastnameResponse = server.registerCustomer(gson.toJson(emptyLastnameDtoRequest));
        Assertions.assertEquals(serverEmptyLastnameResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(serverEmptyLastnameResponse.getResponseData(), "Empty last name");
    }

    @Test
    public void testRegisterWithProblemLoginOrPassword() {
        RegisterDtoRequest nullLoginRequest = new RegisterDtoRequest(
                "sdffdg",
                "rgrgrd",
                null,
                "firetreesd",
                "Nikita@mail.ru"
        );

        RegisterDtoRequest emptyLoginRequest = new RegisterDtoRequest(
                "jrofjrfijo",
                "Асаевичъ",
                "",
                "eojfejofoj",
                "Nikita@mail.ru"
        );

        RegisterDtoRequest nullPasswordRequest = new RegisterDtoRequest(
                "depdpkekpd",
                "Аefwefewf",
                "Nicoolenkosdds",
                null,
                "Nikita@mail.ru"
        );

        RegisterDtoRequest emptyPasswordRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosdsdsdsds",
                "",
                "Nikita@mail.ru"
        );

        ServerResponse nullLoginResponse = server.registerCustomer(gson.toJson(nullLoginRequest));
        Assertions.assertEquals(nullLoginResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(nullLoginResponse.getResponseData(), "Empty login");

        ServerResponse emptyLoginResponse = server.registerCustomer(gson.toJson(emptyLoginRequest));
        Assertions.assertEquals(emptyLoginResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(emptyLoginResponse.getResponseData(), "Empty login");

        ServerResponse nullPasswordResponse = server.registerCustomer(gson.toJson(nullPasswordRequest));
        Assertions.assertEquals(nullPasswordResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(nullPasswordResponse.getResponseData(), "Empty password");

        ServerResponse emptyPasswordResponse = server.registerCustomer(gson.toJson(emptyPasswordRequest));
        Assertions.assertEquals(emptyPasswordResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(emptyPasswordResponse.getResponseData(), "Empty password");
    }

    @Test
    public void testLoginUser() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosddsadsda",
                "firetreesd",
                "Nikita@mail.ru"
        );
        server.registerExecutor(gson.toJson(dtoRequest));

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
                "Nicoolenkosddsadsda", "firetreesd");

        ServerResponse serverResponseLogin = server.loginUser(gson.toJson(loginDtoRequest));
        Assertions.assertEquals(serverResponseLogin.getResponseCode(), SUCCESS_CODE);

        LoginDtoResponse loginDtoResponse = gson.fromJson(serverResponseLogin.getResponseData(), LoginDtoResponse.class);
        assertNotNull(loginDtoResponse.getToken());
    }

    @Test
    public void testLoginWithoutRegister() {
        //logout невозможен из-за того, что мы не сможем получить токен
        //пока не зарегистрируемся и не залогинимся

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
                "Nicoolenkosdasdsadsadadsasd", "firetreesd");

        ServerResponse withoutRegisterResponse = server.loginUser(gson.toJson(loginDtoRequest));
        Assertions.assertEquals(withoutRegisterResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(withoutRegisterResponse.getResponseData(), "This user don't exist");
    }

    @Test
    public void testLoginAndLogoutUser() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "ooewpwqsss",
                "firetreesd",
                "nic@mail.ru"
        );
        server.registerCustomer(gson.toJson(dtoRequest));

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest
                ("ooewpwqsss", "firetreesd");

        ServerResponse RegisterResponse = server.loginUser(gson.toJson(loginDtoRequest));
        LoginDtoResponse loginDtoResponse = gson.fromJson
                (RegisterResponse.getResponseData(), LoginDtoResponse.class);

        ServerResponse logoutUserResponse = server.logoutUser(loginDtoResponse.getToken());
        Assertions.assertEquals(logoutUserResponse.getResponseCode(), SUCCESS_CODE);
        Assertions.assertEquals(logoutUserResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
    }


    @Test
    public void testBuyerAddLotsLikeSeller() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosdasdasdsadsadsadadsa",
                "firetreesd",
                "ea@mail.ru"
        );
        server.registerCustomer(gson.toJson(dtoRequest));
        ServerResponse response = server.loginUser(gson.toJson(new LoginDtoRequest
                (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (response.getResponseData(), LoginDtoResponse.class);

        TakeHandleApplicationRequest takeHandleApplicationRequest = new TakeHandleApplicationRequest
                ("type", "status");

        ServerResponse buyerAddLotRequest = server.takeApplication
                (String.valueOf(loginDtoResponse.getToken()), gson.toJson(takeHandleApplicationRequest));

        Assertions.assertEquals(buyerAddLotRequest.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(buyerAddLotRequest.getResponseData(), "This user not seller");
    }


    @Test
    public void testSellerAddPriceLikeBuyer() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosd12312ewsaeqwe",
                "firetreesd",
                "s.@mail.ru"
        );
        server.registerOperator(gson.toJson(dtoRequest));

        ServerResponse response = server.loginUser(gson.toJson(new LoginDtoRequest
                (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (response.getResponseData(), LoginDtoResponse.class);

        AddApplicationToOperatorRequest priceDtoRequest = new AddApplicationToOperatorRequest
                ("type", "status");

        ServerResponse serverResponse = server.addApplication
                (String.valueOf(loginDtoResponse.getToken()), gson.toJson(priceDtoRequest));

        Assertions.assertEquals(serverResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals(serverResponse.getResponseData(), "This user not seller");
    }

    @Test
    public void testAddApplication() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "qwertyuiosspp",
                "firetreesddas",
                "customer@mail.ru"
        );

        server.registerCustomer(gson.toJson(dtoRequest));

        ServerResponse serverResponseSeller = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponseSeller = gson.fromJson
                (serverResponseSeller.getResponseData(), LoginDtoResponse.class);

        String sellerUuid = loginDtoResponseSeller.getToken().toString();

        ServerResponse serverResponseAddLot = server.addApplication
                (sellerUuid, gson.toJson(new AddApplicationToCustomer
                        ("Подключение к тарифу", "На рассмотрении")));

        Assertions.assertEquals
                (serverResponseAddLot.getResponseData(), gson.toJson
                        (Integer.parseInt(serverResponseAddLot.getResponseData())));
        Assertions.assertEquals(serverResponseAddLot.getResponseCode(), SUCCESS_CODE);
    }


    @Test
    public void testAddLotWithProblemData() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "jmishenko",
                "firetreesd",
                "gromozeka@mail.ru"
        );

        server.registerCustomer(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);
        String uuid = loginDtoResponse.getToken().toString();

        AddApplicationToCustomer addApplicationEmptyNameDtoRequest = new AddApplicationToCustomer
                ("", "good");

        ServerResponse addLotEmptyNameResponse = server.addApplication
                (uuid, gson.toJson(addApplicationEmptyNameDtoRequest));

        Assertions.assertEquals(addLotEmptyNameResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals
                (addLotEmptyNameResponse.getResponseData(), "Empty first name");

        AddApplicationToCustomer addLotNullNameDtoRequest = new AddApplicationToCustomer
                (null, "aboba");

        ServerResponse addLotNullNameResponse = server.addApplication
                (uuid, gson.toJson(addLotNullNameDtoRequest));

        Assertions.assertEquals
                (addLotNullNameResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals
                (addLotNullNameResponse.getResponseData(), "Empty first name");

        AddApplicationToCustomer addLotEmptyDescriptionRequest = new AddApplicationToCustomer
                ("Table", "");

        ServerResponse addLotEmptyDescriptionResponse = server.addApplication
                (uuid, gson.toJson(addLotEmptyDescriptionRequest));

        Assertions.assertEquals(addLotEmptyDescriptionResponse.getResponseCode(), ERROR_CODE);
        Assertions.assertEquals
                (addLotEmptyDescriptionResponse.getResponseData(), "Empty description");
    }

    @Test
    public void testAddPriceNotExistLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "weweweqwewqe",
                "firetreesd",
                "kto@mail.ru"
        );

        server.registerExecutor(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = loginDtoResponse.getToken().toString();

        GiveApplicationToAnotherDivisionRequest priceDtoRequest = new GiveApplicationToAnotherDivisionRequest
                (1, 1);

        ServerResponse addPriceServerResponse = server.giveApplication(uuid, gson.toJson(priceDtoRequest));
        Assertions.assertEquals(addPriceServerResponse.getResponseData(), "This division not exist");
        Assertions.assertEquals(addPriceServerResponse.getResponseCode(), ERROR_CODE);

    }

    @Test
    public void testGetInfoLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd",
                "exex@mail.ru"
        );

        server.registerCustomer(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));


        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();

        ServerResponse addResponse = server.addApplication(uuid, gson.toJson
                (new AddApplicationToCustomer
                        ("Подключение дополнительных минут", "На рассмотрении")));

        ServerResponse checkResults = server.getApplicationInfo(uuid, gson.toJson(
                new WatchStatusOfApplicationRequest(Integer.parseInt(addResponse.getResponseData()))));

        Assertions.assertEquals(checkResults.getResponseCode(), SUCCESS_CODE);
    }
}
