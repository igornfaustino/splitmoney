package com.nfaustino.splitmoney.queries.group.mappers;

import org.mapstruct.Mapper;

import com.nfaustino.splitmoney.queries.group.dto.GroupDetails;
import com.nfaustino.splitmoney.shared.infra.data.GroupData;

@Mapper(componentModel = "spring")
public interface GroupQueryMapper {
    public GroupDetails groupDetailsFromGroupData(GroupData data);
}
