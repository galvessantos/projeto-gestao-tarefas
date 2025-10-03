package project.study.gestao_tarefas.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(
    @JsonProperty("access_token")
    String accessToken,
    
    @JsonProperty("token_type")
    String tokenType
) {
    public AuthenticationResponse(String accessToken) {
        this(accessToken, "Bearer");
    }
}
