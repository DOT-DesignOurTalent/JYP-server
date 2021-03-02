package io.dot.jyp.server.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateResponse {
    private String groupCode;
    private String nickname;

    public static GroupCreateResponse of(
            String groupCode,
            String nickname
    ) {
        return new GroupCreateResponse(
                groupCode,
                nickname
        );
    }
}
