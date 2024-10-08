package com.leets.team2.xclone.domain.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "Authentication", description = "Auth 관련 API")
public interface AuthController {

  void getLogin(HttpServletResponse httpServletResponse) throws IOException;

}
