package com.leets.team2.xclone.exception;

public class UnauthorizedException extends ApplicationException{
    public UnauthorizedException(){
        super(ErrorInfo.UNAUTHORIZED);
    }

}
