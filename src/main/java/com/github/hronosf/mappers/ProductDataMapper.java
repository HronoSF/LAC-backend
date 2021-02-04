package com.github.hronosf.mappers;

import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.dto.ProductDataResponseDTO;
import com.github.hronosf.model.ProductData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductDataMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    ProductData toProductData(PreTrialAppealRequestDTO requestDTO);

    ProductDataResponseDTO toDto(ProductData productData);
}
