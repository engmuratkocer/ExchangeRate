package com.task.exchange.util;

import com.task.exchange.model.ExchangeRate;
import com.task.exchange.model.ExchangeRateDTO;
import org.modelmapper.ModelMapper;

public class ExchangeRateDataTypeConverter {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static ExchangeRateDTO convertToDto(ExchangeRate post) {
        ExchangeRateDTO postDto = null;
        if (post != null) {
            postDto = MODEL_MAPPER.map(post, ExchangeRateDTO.class);
            postDto.setSaveDate(post.getSaveTime());
        }
        return postDto;
    }

}
