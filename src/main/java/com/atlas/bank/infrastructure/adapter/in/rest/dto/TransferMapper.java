package com.atlas.bank.infrastructure.adapter.in.rest.dto;

import com.atlas.bank.domain.model.transaction.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    TransferResponse toResponse(Transaction transaction);

    List<TransferResponse> toResponseList(List<Transaction> transactions);
}
