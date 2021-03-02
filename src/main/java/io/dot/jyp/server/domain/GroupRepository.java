package io.dot.jyp.server.domain;

import io.dot.jyp.server.domain.exception.BadRequestException;
import io.dot.jyp.server.domain.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    default void existsByCodeThenThrow(String code) {
        if (this.existsByCode(code)) {
            throw new BadRequestException(String.format("group code '%s' is already exist", code), ErrorCode.INTERNAL_SERVER);
        }
    }

    boolean existsByCode(String code);

    Optional<Group> findGroupByCode(String code);

}

