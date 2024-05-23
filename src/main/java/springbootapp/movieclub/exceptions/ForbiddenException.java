package springbootapp.movieclub.exceptions;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException(String message){
        super(message);
    }

}
