package ua.foxminded.yakovlev.university.exceptionhandler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
class GlobalControllerExceptionHandler {
	
	private static final String DEFAULT_ERROR_VIEW = "error";
	
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;

	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFoundException(HttpServletRequest req, NotFoundException exception) {
		return getModelAndView(DEFAULT_ERROR_VIEW, exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleIllegalArgument(HttpServletRequest req, IllegalArgumentException exception) {
		return getModelAndView(DEFAULT_ERROR_VIEW, exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ModelAndView handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException exception) {
		
		BindingResult result = exception.getBindingResult();
		String errorsMessage = result.getAllErrors().stream().map((e) -> e.getDefaultMessage())
				.reduce((s1, s2) -> s1 + "; " + s2).orElse("");
		
		return getModelAndView(DEFAULT_ERROR_VIEW, errorsMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ModelAndView MethodArgumentTypeMismatchException(HttpServletRequest req,
			MethodArgumentTypeMismatchException exception) {

		return getModelAndView(DEFAULT_ERROR_VIEW, exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView exception(HttpServletRequest req,
			Exception exception) {
		return getModelAndView(DEFAULT_ERROR_VIEW, "error_message.access_denied", HttpStatus.FORBIDDEN);
	}

	private ModelAndView getModelAndView(String view, String message, HttpStatus status) {

		ModelAndView modelAndView = new ModelAndView(view, status);
		
		modelAndView.addObject("errorMessage", messageSource.getMessage(message, null, Locale.getDefault()));

		return modelAndView;
	}
}