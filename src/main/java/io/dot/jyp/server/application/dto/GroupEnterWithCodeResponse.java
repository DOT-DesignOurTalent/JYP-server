package io.dot.jyp.server.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupEnterWithCodeResponse {
    private String nickname;
    private GroupEnterWithCodeResponse(String nickname) {
        this.nickname = nickname;
    }
    public static GroupEnterWithCodeResponse of(String nickname) {
        return new GroupEnterWithCodeResponse(nickname);
    }
}
