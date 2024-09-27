package org.bobpark.bobchatsapi.domain.chat.model;

import static org.apache.commons.lang3.ObjectUtils.*;

import lombok.Builder;

@Builder
public record PageInfo(Integer page,
                       Integer size) {

    public PageInfo{
        page = defaultIfNull(page, 0);
        size = defaultIfNull(size, 25);
    }
}
