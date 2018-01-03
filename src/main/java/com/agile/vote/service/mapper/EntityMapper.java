/**
 * Copyright (c) 2017 艾云信息科技有限公司 All rights reserved
 */

package com.agile.vote.service.mapper;

import java.util.List;


public interface EntityMapper<D, E> {

    public E toEntity(D dto);

    public D toDto(E entity);

    public List <E> toEntity(List<D> dtoList);

    public List <D> toDto(List<E> entityList);
}
