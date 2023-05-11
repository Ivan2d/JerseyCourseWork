package net.thumbtack.school.auction.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDtoRequest {
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String mail;
}
