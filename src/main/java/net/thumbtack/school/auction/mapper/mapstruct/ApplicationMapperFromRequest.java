package net.thumbtack.school.auction.mapper.mapstruct;

import net.thumbtack.school.auction.dto.request.AddApplicationToDivisionRequest;
import net.thumbtack.school.auction.dto.request.AddApplicationToOperatorRequest;
import net.thumbtack.school.auction.dto.request.TakeHandleApplicationRequest;
import net.thumbtack.school.auction.model.Application;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplicationMapperFromRequest {
    ApplicationMapperFromRequest MAPPER = Mappers.getMapper(ApplicationMapperFromRequest.class);
    Application toApplication(AddApplicationToOperatorRequest request);
    Application toApplication(AddApplicationToDivisionRequest request);
    Application toApplication(TakeHandleApplicationRequest dtoRequest);
    @InheritInverseConfiguration
    AddApplicationToDivisionRequest fromDivisionRequest(Application application);
    @InheritInverseConfiguration
    AddApplicationToOperatorRequest fromOperatorRequest(Application application);
}
