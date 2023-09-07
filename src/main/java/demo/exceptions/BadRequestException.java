package demo.exceptions;

public class BadRequestException extends RuntimeException{
    //TODO: add 400 wala excpetiob
    public BadRequestException(String message) {
        super(message);
    }

}
