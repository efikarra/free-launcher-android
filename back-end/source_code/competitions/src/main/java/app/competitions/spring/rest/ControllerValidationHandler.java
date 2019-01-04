package app.competitions.spring.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import app.competitions.dto.FieldErrorDTO;

@ControllerAdvice
public class ControllerValidationHandler {

	@Autowired
	private MessageSource msgSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<FieldErrorDTO> processValidationError(
			MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> error = result.getFieldErrors();

		return processFieldError(error);
	}

	private List<FieldErrorDTO> processFieldError(List<FieldError> fieldErrors) {
		List<FieldErrorDTO> fieldErrorDTOs = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {

			fieldErrorDTOs.add(new FieldErrorDTO(fieldError.getField(),
					fieldError.getDefaultMessage()));
		}

		return fieldErrorDTOs;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<FieldErrorDTO> processValidationError(
			ConstraintViolationException ex) {

		List<FieldErrorDTO> fieldErrorDTOs = new ArrayList<>();

		String message = ex.getCause().getMessage();
		if (message.contains("username"))
			fieldErrorDTOs.add(new FieldErrorDTO("username", "already exists"));
		if (message.contains("email"))
			fieldErrorDTOs.add(new FieldErrorDTO("email", "already exists"));
		return fieldErrorDTOs;
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<FieldErrorDTO> processValidationError(
			DataIntegrityViolationException ex) {

		List<FieldErrorDTO> fieldErrorDTOs = new ArrayList<>();

		String message = ex.getRootCause().getMessage();
		System.out.println(message);
		if (message.contains("username"))
			fieldErrorDTOs.add(new FieldErrorDTO("username", "already exists"));
		if (message.contains("email"))
			fieldErrorDTOs.add(new FieldErrorDTO("email", "already exists"));
		return fieldErrorDTOs;
	}

}