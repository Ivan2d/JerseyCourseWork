package net.thumbtack.school.auction.mapper.mapstruct;
import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.model.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapperFromRegister {
    CustomerMapperFromRegister MAPPER = Mappers.getMapper(CustomerMapperFromRegister.class);
    Customer toCustomer(RegisterDtoRequest request);

    @InheritInverseConfiguration
    RegisterDtoRequest fromUser(Customer customer);
}
