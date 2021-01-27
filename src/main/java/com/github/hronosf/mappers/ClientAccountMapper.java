package com.github.hronosf.mappers;

import com.github.hronosf.model.ClientBankData;
import com.github.hronosf.dto.ClientBankDataResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientAccountMapper {

    ClientBankDataResponseDTO toDto(ClientBankData clientBankData);
}
