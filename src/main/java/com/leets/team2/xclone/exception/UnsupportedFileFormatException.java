package com.leets.team2.xclone.exception;

public class UnsupportedFileFormatException extends ApplicationException {

    public UnsupportedFileFormatException() {
        super(ErrorInfo.UNSUPPORTED_FILE_FORMAT);
    }
}