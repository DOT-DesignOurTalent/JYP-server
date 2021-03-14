package io.dot.jyp.server.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
  @Setter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Meta {

    private SameName sameName;
    private Integer pageable_count;
    private Integer totalCount;
    private boolean isEnd;

    public Meta(
        SameName sameName,
        Integer pageable_count,
        Integer totalCount,
        boolean isEnd
    ) {
      this.sameName = new SameName(
          sameName.getRegion(),
          sameName.getKeyword(),
          sameName.getSelectedRegion()
      );
      this.pageable_count = pageable_count;
      this.totalCount = totalCount;
      this.isEnd = isEnd;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SameName {

      private List<String> region;
      private String keyword;
      private String selectedRegion;
    }
  }

}
