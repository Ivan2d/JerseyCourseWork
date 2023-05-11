package net.thumbtack.school.auction.mapper.mapstruct;

import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.model.Executor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper

public interface ExecutorMapperFromRegister {
    ExecutorMapperFromRegister MAPPER = Mappers.getMapper(ExecutorMapperFromRegister.class);
    Executor toExecutor(RegisterDtoRequest request);
    @InheritInverseConfiguration
    RegisterDtoRequest fromExecutor(Executor executor);
}
