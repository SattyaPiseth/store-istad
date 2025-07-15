package co.istad.storeistad.base;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

/**
 * @author Sombath
 * create at 23/10/22 5:57 PM
 */
public class BaseService {


    protected StructureRS response() {
        return new StructureRS();
    }

    protected <T> StructureRS response(T data) {
        return new StructureRS(data);
    }

    protected <T> StructureRS response(T data, PagingRS pagingRS) {
        return new StructureRS(data, pagingRS);
    }

    protected <T, S> StructureRS response(T data, Page<S> page) {
        return new StructureRS(data, new PagingRS(page));
    }

    protected StructureRS response(HttpStatus httpStatus, String message) {
        return new StructureRS(httpStatus, message);
    }

    protected <T> StructureRS response(HttpStatus httpStatus, String message, T data) {
        return new StructureRS(httpStatus, message, data);
    }

}
