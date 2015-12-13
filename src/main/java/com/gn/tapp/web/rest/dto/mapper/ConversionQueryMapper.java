package com.gn.tapp.web.rest.dto.mapper;

import com.gn.tapp.domain.ConversionQuery;
import com.gn.tapp.web.rest.dto.ConversionQueryDTO;
import com.google.common.base.Preconditions;

public class ConversionQueryMapper {

    /**
     * Converts a ConversionQueryDTO to domain entity.
     * @param conversionQueryDTO the DTO
     * @return ConversionQuery
     */
    public static ConversionQuery fromDTO(final ConversionQueryDTO conversionQueryDTO) {
        Preconditions.checkNotNull(conversionQueryDTO, "The provided ConversionQuery DTO is null");
        ConversionQuery conversionQuery = new ConversionQuery();
        conversionQuery.setAmount(conversionQueryDTO.getAmount());
        conversionQuery.setFromCurrency(conversionQueryDTO.getFromCurrency());
        conversionQuery.setToCurrency(conversionQueryDTO.getToCurrency());
        conversionQuery.setConversionDate(conversionQueryDTO.getConversionDate());
        return conversionQuery;
    }
}
