package io.dot.jyp.server.application.dto;

import io.dot.jyp.server.domain.Account;
import io.dot.jyp.server.domain.Diner;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupCreateRequest {
    private Account account;
    private List<Diner> diners;
}
