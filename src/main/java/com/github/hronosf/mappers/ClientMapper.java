package com.github.hronosf.mappers;

import com.github.hronosf.dto.ClientProfileResponseDTO;
import com.github.hronosf.dto.ClientRegistrationRequestDTO;
import com.github.hronosf.model.Client;
import com.github.hronosf.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE
        , imports = {Collectors.class, Role.class, UUID.class})
public interface ClientMapper {

    @Mapping(target = "roles",
            expression = "java(client.getRoles().stream().map(Role::getName).collect(Collectors.toList()))")
    ClientProfileResponseDTO toDto(Client client);

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    Client toClient(ClientRegistrationRequestDTO request);
}
