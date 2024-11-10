package com.leets.team2.xclone.common.auth.aop;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.common.auth.MemberContext;
import com.leets.team2.xclone.common.auth.MemberExtractor;
import com.leets.team2.xclone.common.auth.annotations.UseGuards;
import com.leets.team2.xclone.common.auth.guards.Guard;
import com.leets.team2.xclone.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UseGuardsAspect {

  private final ApplicationContext applicationContext;
  private final MemberExtractor memberExtractor;

  @Around("@annotation(useGuards)")
  public Object applyGuards(ProceedingJoinPoint joinPoint, UseGuards useGuards) throws Throwable {
    try {
      this.memberExtractor.extractMemberFromToken();
    } catch (ApplicationException e) {
      return this.createErrorResponse(e);
    }

    Class<? extends Guard>[] guardClasses = useGuards.value();

    for (Class<? extends Guard> guardClass : guardClasses) {
      Guard guard = applicationContext.getBean(guardClass);

      try {
        guard.canActivate();
      } catch (ApplicationException e) {
        return this.createErrorResponse(e);
      }
    }

    Object proceed = joinPoint.proceed();

    MemberContext.clear();

    return proceed;
  }

  private ResponseEntity<ApiData<String>> createErrorResponse(ApplicationException exception) {
    return ApiData.errorFrom(exception.getErrorInfo());
  }
}
