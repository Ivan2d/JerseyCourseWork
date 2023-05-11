package net.thumbtack.school.auction.mapper.mapstruct;

import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.model.Operator;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperatorMapperFromRegister {
    OperatorMapperFromRegister MAPPER = Mappers.getMapper(OperatorMapperFromRegister.class);
    Operator toOperator(RegisterDtoRequest loginUserDtoRequest);

    @InheritInverseConfiguration
    RegisterDtoRequest fromUser(Operator operator);
}
