package com.github.hronosf.services;

import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.model.ProductData;

public interface ProductDataService {

    ProductData saveProductData(PreTrialAppealRequestDTO request);
}
