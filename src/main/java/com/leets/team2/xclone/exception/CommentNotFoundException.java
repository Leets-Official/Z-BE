package com.leets.team2.xclone.exception;

public class CommentNotFoundException extends ApplicationException{
    public CommentNotFoundException(){
        super(ErrorInfo.NO_SUCH_COMMENT);
    }
}
