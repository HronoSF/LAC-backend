package com.github.hronosf.repository;

import com.github.hronosf.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DocumentRepository extends PagingAndSortingRepository<Document, String>,
        JpaSpecificationExecutor<Document> {

    List<Document> getAllByClientId(String clientId);

    Page<Document> findAll(Specification<Document> spec, Pageable pageable);

    void deleteByClientId(String clientId);
}
