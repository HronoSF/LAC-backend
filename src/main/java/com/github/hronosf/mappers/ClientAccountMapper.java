package com.github.hronosf.mappers;

import com.github.hronosf.domain.ClientBankData;
import com.github.hronosf.dto.response.ClientBankDataResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientAccountMapper {

    ClientBankDataResponseDTO toDto(ClientBankData clientBankData);
}
