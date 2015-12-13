package com.gn.tapp.service;

import com.gn.tapp.domain.ConversionQuery;
import com.gn.tapp.repository.ConversionQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ConversionQuery.
 */
@Service
@Transactional
public class ConversionQueryService {

    private final Logger log = LoggerFactory.getLogger(ConversionQueryService.class);

    @Inject
    private ConversionQueryRepository conversionQueryRepository;

    /**
     * Save a conversionQuery.
     * @return the persisted entity
     */
    public ConversionQuery save(ConversionQuery conversionQuery) {
        log.debug("Request to save ConversionQuery : {}", conversionQuery);
        ConversionQuery result = conversionQueryRepository.save(conversionQuery);
        return result;
    }

    /**
     * get all the conversionQuerys.
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ConversionQuery> findAll() {
        log.debug("Request to get all ConversionQuerys");
        List<ConversionQuery> result = conversionQueryRepository.findAll();
        return result;
    }

    /**
     * get one conversionQuery by id.
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ConversionQuery findOne(Long id) {
        log.debug("Request to get ConversionQuery : {}", id);
        ConversionQuery conversionQuery = conversionQueryRepository.findOne(id);
        return conversionQuery;
    }

    /**
     * get the list of latest conversionQuery.
     * @return the list with entities
     */
    @Transactional(readOnly = true)
    public List<ConversionQuery> findLatestResults(final String login) {
        log.debug("Request to get latest ConversionQuery");
        List<ConversionQuery> conversionQueryList = conversionQueryRepository.findTop10ByOwnerLoginOrderByCreatedOnDesc(login);
        return conversionQueryList;
    }

    /**
     * delete the  conversionQuery by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConversionQuery : {}", id);
        conversionQueryRepository.delete(id);
    }
}
