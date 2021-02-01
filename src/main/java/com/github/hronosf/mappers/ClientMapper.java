package com.github.hronosf.mappers;

import com.github.hronosf.dto.ClientProfileResponseDTO;
import com.github.hronosf.model.Client;
import com.github.hronosf.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = ClientAccountMapper.class, unmappedSourcePolicy = ReportingPolicy.IGNORE,
        imports = {Collectors.class, Role.class})
public interface ClientMapper {

    ClientAccountMapper BANK_DATA_MAPPER = Mappers.getMapper(ClientAccountMapper.class);

    @Mapping(target = "latestBankData",
            expression = "java(client.getBankData() == null || client.getBankData().isEmpty() ? null : BANK_DATA_MAPPER.toDto(client.getBankData().get(0)))")
    @Mapping(target = "roles",
            expression = "java(client.getRoles().stream().map(Role::getName).collect(Collectors.toList()))")
    ClientProfileResponseDTO toDto(Client client);
}
