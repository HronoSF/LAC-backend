package com.github.hronosf.services.impl;

import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.mappers.ProductDataMapper;
import com.github.hronosf.model.ProductData;
import com.github.hronosf.repository.ProductDataRepository;
import com.github.hronosf.services.ProductDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDataServiceImpl implements ProductDataService {

    private final ProductDataMapper mapper;

    private final ProductDataRepository productDataRepository;

    @Override
    public ProductData saveProductData(PreTrialAppealRequestDTO request) {
        ProductData newProductData = mapper.toProductData(request);
        return productDataRepository.save(newProductData);
    }
}
