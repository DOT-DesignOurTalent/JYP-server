package io.dot.jyp.server.application.dto;

import io.dot.jyp.server.domain.Diner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupAddDinersRequest {
    private List<Diner> diners;
}
