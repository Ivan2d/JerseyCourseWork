package net.thumbtack.school.auction.mapper.mapstruct;
import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.dto.response.UserDtoResponse;
import net.thumbtack.school.auction.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapperFromLogin {
    UserMapperFromLogin MAPPER = Mappers.getMapper(UserMapperFromLogin.class);
    User toUser(LoginDtoRequest loginUserDtoRequest);
    User toUser(UserDtoResponse userDtoResponse);
    @InheritInverseConfiguration
    LoginDtoRequest fromUser(User user);
}