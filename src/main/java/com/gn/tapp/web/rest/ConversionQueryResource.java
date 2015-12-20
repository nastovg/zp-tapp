package com.gn.tapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gn.tapp.domain.ConversionQuery;
import com.gn.tapp.domain.User;
import com.gn.tapp.security.AuthoritiesConstants;
import com.gn.tapp.security.SecurityUtils;
import com.gn.tapp.service.ConversionQueryService;
import com.gn.tapp.service.UserService;
import com.gn.tapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ConversionQuery.
 */
@RestController
@RequestMapping("/api")
public class ConversionQueryResource {

    private final Logger log = LoggerFactory.getLogger(ConversionQueryResource.class);

    @Inject
    private ConversionQueryService conversionQueryService;

    @Inject
    private UserService userService;

    /**
     * POST  /conversionQuerys -> Create a new conversionQuery.
     */
    @RequestMapping(value = "/conversionQuerys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<ConversionQuery> createConversionQuery(@Valid @RequestBody ConversionQuery conversionQuery) throws URISyntaxException {
        log.debug("REST request to save ConversionQuery : {}", conversionQuery);
        if (conversionQuery.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("conversionQuery", "idexists", "A new conversionQuery cannot already have an ID")).body(null);
        }
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> currentUser = userService.getUserWithAuthoritiesByLogin(currentUserLogin);
        conversionQuery.setOwner(currentUser.get());
        conversionQuery.setCreatedOn(ZonedDateTime.now());
        ConversionQuery result = conversionQueryService.save(conversionQuery);
        return ResponseEntity.created(new URI("/api/conversionQuerys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("conversionQuery", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /conversionQuerys -> Updates an existing conversionQuery.
     */
    @RequestMapping(value = "/conversionQuerys",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<ConversionQuery> updateConversionQuery(@Valid @RequestBody ConversionQuery conversionQuery) throws URISyntaxException {
        log.debug("REST request to update ConversionQuery : {}", conversionQuery);
        if (conversionQuery.getId() == null) {
            return createConversionQuery(conversionQuery);
        }
        ConversionQuery result = conversionQueryService.save(conversionQuery);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("conversionQuery", conversionQuery.getId().toString()))
                .body(result);
    }

    /**
     * GET  /conversionQuerys -> get all the conversionQuerys.
     */
    @RequestMapping(value = "/conversionQuerys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public List<ConversionQuery> getAllConversionQuerys() {
        log.debug("REST request to get all ConversionQuerys");
        return conversionQueryService.findAll();
    }

    /**
     * GET  /conversionQuerys -> get all the conversionQuerys.
     */
    @RequestMapping(value = "/conversionQuerys/latest",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public List<ConversionQuery> getLatestConversionQuerys() {
        log.debug("REST request to get latest ConversionQuerys for current user");
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        return conversionQueryService.findLatestResults(currentUserLogin);
    }

    /**
     * GET  /conversionQuerys/:id -> get the "id" conversionQuery.
     */
    @RequestMapping(value = "/conversionQuerys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<ConversionQuery> getConversionQuery(@PathVariable Long id) {
        log.debug("REST request to get ConversionQuery : {}", id);
        ConversionQuery conversionQuery = conversionQueryService.findOne(id);
        return Optional.ofNullable(conversionQuery)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /conversionQuerys/:id -> delete the "id" conversionQuery.
     */
    @RequestMapping(value = "/conversionQuerys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteConversionQuery(@PathVariable Long id) {
        log.debug("REST request to delete ConversionQuery : {}", id);
        conversionQueryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("conversionQuery", id.toString())).build();
    }
}
