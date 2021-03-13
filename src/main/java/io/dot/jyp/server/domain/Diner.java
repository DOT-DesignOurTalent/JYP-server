package io.dot.jyp.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Diner {
    private String placeName;
    private String category_name;
    private String road_address_name;
    private String phone;
    private String place_url;
    private String distance;
    private String x;
    private String y;

    public static Diner fromKakaoLocal(
            String placeName,
            String category_name,
            String road_address_name,
            String phone,
            String place_url,
            String distance,
            String x,
            String y
    ) {
        return new Diner(
                placeName,
                category_name,
                road_address_name,
                phone,
                place_url,
                distance,
                x,
                y
        );
    }
}
