package io.dot.jyp.server.domain;

import io.dot.jyp.server.domain.exception.BadRequestException;
import io.dot.jyp.server.domain.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    default void existsByGroupCodeThenThrow(String groupCode) {
        if (this.existsByGroupCode(groupCode)) {
            throw new BadRequestException(String.format("group code '%s' is already exist", groupCode), ErrorCode.INTERNAL_SERVER);
        }
    }

    boolean existsByGroupCode(String groupCode);

    Optional<Group> findGroupByGroupCode(String groupCode);

}

