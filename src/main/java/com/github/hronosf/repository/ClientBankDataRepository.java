package com.github.hronosf.repository;

import com.github.hronosf.model.ClientBankData;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientBankDataRepository extends PagingAndSortingRepository<ClientBankData, String> {
}
