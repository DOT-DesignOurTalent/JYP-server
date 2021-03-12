package io.dot.jyp.server.domain;

import org.springframework.data.domain.Pageable;

public interface DinerFinderClient {
    Pagination<Diner> getDinersByKeyword(String keyword, Map map, Pageable pageable);
}
