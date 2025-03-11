package com.rr.dreamtracker.service;

import com.rr.dreamtracker.dto.DreamDto;
import com.rr.dreamtracker.entity.Dream;
import com.rr.dreamtracker.entity.DreamStatus;
import com.rr.dreamtracker.exception.NotFoundException;
import com.rr.dreamtracker.repository.DreamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DreamService {
    private final DreamRepository dreamRepository;

    public DreamService(DreamRepository dreamRepository) {
        this.dreamRepository = dreamRepository;
    }

    public List<Dream> getAll() {
        return dreamRepository.findAll();
    }

    public Dream create(DreamDto dreamDto) {
        Dream dream = new Dream();
        dream.setName(dreamDto.getName());
        dream.setDeadline(dreamDto.getDeadline());
        dream.setStatus(DreamStatus.PLANNED);
        dream.setPriority(dreamDto.getPriority());
        dream.setDescription(dreamDto.getDescription());
        Long parentDreamId = dreamDto.getParentDreamId();
        if(parentDreamId != null && parentDreamId > 0){
            Dream parent = get(dreamDto.getParentDreamId());
            dream.setParent(parent);
        }
        return dreamRepository.save(dream);
    }

    public Dream get(Long id){
        return dreamRepository.findDreamById(id).orElseThrow(() -> new NotFoundException("Dream not found"));
    }

    public void delete(Long id) {
        dreamRepository.deleteDreamById(id);
    }
}
