package com.github.wp17.lina.game.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResult {
    /**0表示成功*/
    private int result;
    private String tips;
}
