package ru.tolstikhin.pastebox.exception;

/**
 * Класс собственного исключения в случае, когда хеш не найден
 */
public class HashNotFoundException extends RuntimeException {
    public HashNotFoundException(String message) {
        super(message);
    }
}
