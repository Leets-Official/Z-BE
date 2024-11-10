package com.leets.team2.xclone.common.auth.annotations;

import com.leets.team2.xclone.common.auth.guards.Guard;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UseGuards {

  Class<? extends Guard>[] value();
}