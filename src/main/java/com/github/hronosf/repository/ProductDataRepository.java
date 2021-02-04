package com.github.hronosf.repository;

import com.github.hronosf.model.ProductData;
import org.springframework.data.repository.CrudRepository;

public interface ProductDataRepository extends CrudRepository<ProductData, String> {
}
