package com.leets.team2.xclone.exception;

public class FollowAlreadyExistsException extends ApplicationException{
    public FollowAlreadyExistsException(){
        super(ErrorInfo.FOLLOW_ALREADY_EXISTS);
    }
}
