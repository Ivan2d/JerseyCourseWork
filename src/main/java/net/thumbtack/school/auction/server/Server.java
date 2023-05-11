package net.thumbtack.school.auction.server;
import net.thumbtack.school.auction.service.*;

import java.util.UUID;

public class Server {

    private CustomerService customerService = new CustomerService();
    private ExecutorService executorService = new ExecutorService();
    private OperatorService operatorService = new OperatorService();
    private UserService userService = new UserService();
    private DebugService debugService = new DebugService();

    public ServerResponse registerCustomer (String requestJsonString){
        return customerService.registerUser(requestJsonString);
    }
    public ServerResponse registerExecutor (String requestJsonString){
        return executorService.registerUser(requestJsonString);
    }

    public ServerResponse registerOperator (String requestJsonString){
        return operatorService.registerUser(requestJsonString);
    }
    public ServerResponse loginUser (String requestJsonString) {
        return userService.login(requestJsonString);
    }

    public ServerResponse logoutUser (UUID token){
        return userService.logout(token);
    }

    public ServerResponse getApplicationInfo (String token, String requestJsonString){
        return customerService.getInfoApplication(token, requestJsonString);
    }

    public ServerResponse addApplication(String token, String requestJsonString){
        return customerService.addApplication(token, requestJsonString);
    }

    public ServerResponse takeApplication (String token, String requestJsonString){
        return executorService.takeApplication(token, requestJsonString);
    }

    public ServerResponse giveApplication (String token, String requestJsonString){
        return executorService.giveApplicationToAnotherDivision(token, requestJsonString);
    }

    public void clear(){
        debugService.clear();
    }
}

