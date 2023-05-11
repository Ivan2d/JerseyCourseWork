package net.thumbtack.school.auction.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer extends User {
    private List<Application> applicationList = new ArrayList<>();
    public Customer(String firstName, String lastName, String login, String password, String mail){
        super(firstName, lastName, login, password, mail);
        setApplicationList(applicationList);
    }
}
