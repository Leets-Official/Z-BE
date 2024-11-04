package com.leets.team2.xclone.exception;

public class InvalidFileException extends ApplicationException{
    public InvalidFileException(){
        super(ErrorInfo.INVALID_FILE);
    }
}
