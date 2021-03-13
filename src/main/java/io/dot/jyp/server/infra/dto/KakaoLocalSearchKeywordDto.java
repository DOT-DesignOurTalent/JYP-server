package io.dot.jyp.server.infra.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoLocalSearchKeywordDto {
    private String placeName;
    private String distance;
    private String place_url;
    private String category_name;
    private String address_name;
    private String road_address_name;
    private String id;
    private String phone;
    private String category_group_code;
    private String category_group_name;
    private String x;
    private String y;
}
