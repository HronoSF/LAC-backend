package com.github.hronosf.repository;

import com.github.hronosf.domain.ClientBankData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ClientBankDataRepository extends PagingAndSortingRepository<ClientBankData, String> {

    List<ClientBankData> getByClientId(String clientId, Sort sort);

    @Query(value = "SELECT * FROM client_bank_data cbk WHERE cbk.client_id='?0' ORDER BY cbk.created_at DESC LIMIT 1", nativeQuery = true)
    Optional<ClientBankData> findTopByClientIdOrderByCreatedAt(String clientId);
}
