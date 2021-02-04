package com.github.hronosf.mappers;

import com.github.hronosf.dto.DocumentDataResponseDTO;
import com.github.hronosf.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ClientBankDataMapper.class, ProductDataMapper.class},
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        imports = {Collectors.class})
public interface DocumentMapper {

    ClientBankDataMapper BANK_DATA_MAPPER = Mappers.getMapper(ClientBankDataMapper.class);
    ProductDataMapper PRODUCT_DATA_MAPPER = Mappers.getMapper(ProductDataMapper.class);

    @Mapping(target = "clientBankData", expression = "java(BANK_DATA_MAPPER.toDto(document.getClientBankData()))")
    @Mapping(target = "productData", expression = "java(PRODUCT_DATA_MAPPER.toDto(document.getProductData()))")
    DocumentDataResponseDTO toDto(Document document);
}
