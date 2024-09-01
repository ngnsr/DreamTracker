package com.rr.dreamtracker.repository;

import com.rr.dreamtracker.dto.DreamDto;
import com.rr.dreamtracker.entity.Dream;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DreamRepository extends PagingAndSortingRepository<Dream, Long> {
    Optional<Dream> findDreamById(Long id);
    List<Dream> findAll();

    Dream save(Dream dream);
    void deleteDreamById(Long id);
}
