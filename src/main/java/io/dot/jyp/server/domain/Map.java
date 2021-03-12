package io.dot.jyp.server.domain;

import lombok.Data;
import lombok.Getter;

@Data
public class Map {
    private String x;
    private String y;
    private Integer radius;

    public String rect() {
        return "";
    }
}
