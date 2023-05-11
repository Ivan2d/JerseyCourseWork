package net.thumbtack.school.auction.model;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String mail;
    private String uuid;
    private int id;

    public User(String firstname, String lastname, String login, String password, String mail){
        setFirstname(firstname);
        setLastname(lastname);
        setLogin(login);
        setPassword(password);
        setMail(mail);
    }
}