package com.github.hronosf.repository;

import com.github.hronosf.domain.ClientBankData;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientBankDataRepository extends PagingAndSortingRepository<ClientBankData, String> {

    List<ClientBankData> getByClientId(String clientId, Sort sort);
}
