package com.leets.team2.xclone.exception;

public class PostNotFoundException extends ApplicationException{//게시물을 찾지 못했을 경우 에러
    public PostNotFoundException(){
        super(ErrorInfo.NO_SUCH_POST);
    }
}
