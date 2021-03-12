package io.dot.jyp.server.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pagination<T> {
    private Meta pagination;
    private List<T> documents = new ArrayList<>();

    public Pagination(
            Meta pagination,
            List<T> documents
    ) {
        this.pagination = new Meta(
                pagination.getSameName(),
                pagination.getPageable_count(),
                pagination.getTotalCount(),
                pagination.isEnd
        );
        this.documents = documents;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Meta {
        private SameName sameName;
        private Integer pageable_count;
        private Integer totalCount;
        private boolean isEnd;

        public static class SameName {
            private List<String> region;
            private String keyword;
            private String selectedRegion;
        }
    }

}
