package demo.exceptions;

import java.util.function.Supplier;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }

}
