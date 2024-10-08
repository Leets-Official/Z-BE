package com.leets.team2.xclone.common.oauth.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leets.team2.xclone.domain.member.dto.responses.KakaoInfo;
import com.leets.team2.xclone.domain.member.dto.responses.OAuth2ProviderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoProviderClient {

  private final KakaoURLBuilder kakaoURLBuilder;
  private final KakaoOAuthConfigProperties kakaoOAuthConfigProperties;

  public String getAccessToken(String authToken) {
    StringBuilder bodyBuilder = new StringBuilder();

    bodyBuilder.append("grant_type=authorization_code")
        .append("&client_id=").append(this.kakaoOAuthConfigProperties.getClientId())
        .append("&redirect_uri=").append(this.kakaoOAuthConfigProperties.getRedirectUri())
        .append("&code=").append(authToken);

    RestClient restClient = RestClient.create();

    OAuth2ProviderResponse oAuth2ProviderResponse =
        restClient.post()
            .uri(this.kakaoURLBuilder.getTokenUrl(authToken))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(bodyBuilder.toString())
            .retrieve()
            .toEntity(OAuth2ProviderResponse.class)
            .getBody();

    return oAuth2ProviderResponse.access_token();
  }

  public KakaoInfo getMemberInfo(String accessToken) throws JsonProcessingException {
    RestClient restClient = RestClient.create();

    String jsonResponse = restClient.post()
        .uri(this.kakaoURLBuilder.getUserInfoUrl(accessToken))
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .header("Authorization", "Bearer " + accessToken)
        .retrieve()
        .toEntity(String.class)
        .getBody();

    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonResponse, KakaoInfo.class);
  }
}
