package com.github.hronosf.mappers;

import com.github.hronosf.domain.Client;
import com.github.hronosf.dto.response.ClientProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ClientAccountMapper.class, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientAccountMapper BANK_DATA_MAPPER = Mappers.getMapper(ClientAccountMapper.class);

    @Mapping(target = "latestBankData",
            expression = "java(client.getBankData().isEmpty()? null: BANK_DATA_MAPPER.toDto(client.getBankData().get(0)))")
    ClientProfileDTO toDto(Client client);
}
