package org.emanuel.offices.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(OfficeNotExistsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto officeNotExistsException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(OfficeBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto officeBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(OfficeLocalityNotExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto officeLocalityNotExistsException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(TypeOfficeNotExistsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto typeOfficeNotExistsException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(DeliveryScheduleNotExistsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto deliveryScheduleNotExistsException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(DeliveryScheduleBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto deliveryScheduleBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }


    @ExceptionHandler(CountryNotExistException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto countryNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(CountryBadRequestException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public ErrorMessageDto countryBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }


    @ExceptionHandler(ProvinceNotExistException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto provinceNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(ProvinceBadRequestException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public ErrorMessageDto provinceBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(LocalityNotExistException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto localityNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(LocalityBadRequestException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public ErrorMessageDto LocalityBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }


    private ErrorMessageDto getMessage(String message) {
        logger.error(message);
        return new ErrorMessageDto(message);
    }
}
