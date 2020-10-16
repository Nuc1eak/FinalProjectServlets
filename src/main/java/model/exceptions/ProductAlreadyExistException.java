package model.exceptions;

public class ProductAlreadyExistException extends RuntimeException{
    public ProductAlreadyExistException(String message) {super(message);}
}
