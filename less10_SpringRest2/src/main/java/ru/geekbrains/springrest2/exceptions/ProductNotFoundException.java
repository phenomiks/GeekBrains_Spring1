package ru.geekbrains.springrest2.exceptions;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
