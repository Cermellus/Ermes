package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.ProspectService;
import nz.co.it4biz.domain.Prospect;
import nz.co.it4biz.repository.ProspectRepository;
import nz.co.it4biz.service.dto.ProspectDTO;
import nz.co.it4biz.service.mapper.ProspectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Prospect.
 */
@Service
@Transactional
public class ProspectServiceImpl implements ProspectService {

    private final Logger log = LoggerFactory.getLogger(ProspectServiceImpl.class);

    private final ProspectRepository prospectRepository;

    private final ProspectMapper prospectMapper;

    public ProspectServiceImpl(ProspectRepository prospectRepository, ProspectMapper prospectMapper) {
        this.prospectRepository = prospectRepository;
        this.prospectMapper = prospectMapper;
    }

    /**
     * Save a prospect.
     *
     * @param prospectDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProspectDTO save(ProspectDTO prospectDTO) {
        log.debug("Request to save Prospect : {}", prospectDTO);
        Prospect prospect = prospectMapper.toEntity(prospectDTO);
        prospect = prospectRepository.save(prospect);
        return prospectMapper.toDto(prospect);
    }

    /**
     * Get all the prospects.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProspectDTO> findAll() {
        log.debug("Request to get all Prospects");
        return prospectRepository.findAll().stream()
            .map(prospectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one prospect by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProspectDTO> findOne(Long id) {
        log.debug("Request to get Prospect : {}", id);
        return prospectRepository.findById(id)
            .map(prospectMapper::toDto);
    }

    /**
     * Delete the prospect by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prospect : {}", id);        prospectRepository.deleteById(id);
    }
}
