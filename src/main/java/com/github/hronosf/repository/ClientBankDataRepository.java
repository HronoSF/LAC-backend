package com.github.hronosf.repository;

import com.github.hronosf.model.ClientBankData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientBankDataRepository extends PagingAndSortingRepository<ClientBankData, String> {

    List<ClientBankData> getByClientId(String clientId, Sort sort);

    @Query(value = "SELECT * FROM client_bank_data cbk WHERE cbk.client_id=:client_id ORDER BY cbk.created_at DESC LIMIT 1", nativeQuery = true)
    Optional<ClientBankData> findTopByClientIdOrderByCreatedAt(@Param("client_id") String clientId);
}
