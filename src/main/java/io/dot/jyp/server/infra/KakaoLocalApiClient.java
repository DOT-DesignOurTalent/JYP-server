package io.dot.jyp.server.infra;

import io.dot.jyp.server.domain.*;
import io.dot.jyp.server.infra.dto.KakaoLocalSearchKeywordDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Component
public class KakaoLocalApiClient implements DinerFinderClient {
    private final RestTemplate restTemplate;

    public KakaoLocalApiClient(
            @Qualifier("kakaoLocalApiRestTemplate") RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Pagination<Diner> getDinersByKeyword(String keyword, Map map, Pageable pageable) {
        Pagination<KakaoLocalSearchKeywordDto> response = this.restTemplate.exchange(
                String.format(
                        "search/keyword?query=%s&category_group_code=%s&x=%s&y=%s&radius=%d&page=%s&size=%s&sort=%s",
                        keyword,
                        "FD6",
                        map.getX(),
                        map.getY(),
                        map.getRadius(),
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "distance"
                ),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Pagination<KakaoLocalSearchKeywordDto>>() {
                }
        ).getBody();

        return new Pagination<>(
                response.getPagination(),
                response.getDocuments().stream()
                        .map(result -> Diner.fromKakaoLocal(
                                result.getPlaceName(),
                                result.getCategory_name(),
                                result.getRoad_address_name(),
                                result.getPhone(),
                                result.getPlace_url(),
                                result.getDistance(),
                                result.getX(),
                                result.getY()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
