package com.github.hronosf.mappers;

import com.github.hronosf.dto.ClientBankDataResponseDTO;
import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.model.ClientBankData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface ClientBankDataMapper {

    @Mapping(source = "clientAddress", target = "address")
    ClientBankDataResponseDTO toDto(ClientBankData clientBankData);

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(source = "consumerBankBik", target = "bik")
    @Mapping(source = "consumerBankName", target = "bankName")
    @Mapping(source = "consumerBankCorrAcc", target = "bankCorrAcc")
    @Mapping(source = "customerAccountNumber", target = "accountNumber")
    @Mapping(source = "address", target = "clientAddress")
    ClientBankData toClientBankDataData(PreTrialAppealRequestDTO requestDTO);
}
