package ua.foxminded.yakovlev.university.controller.api;

import java.util.List;
import java.util.Locale;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.dto.TimetableRecordDto;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.mapper.TimetableRecordMapper;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.util.ErrorMessageHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetableRecords")
public class ApiTimetableRecordController {
	
	private static final String NOT_FOUND = "api.message.timetableRecord_not_found";
	private final TimetableRecordService timetableRecordService;
	private final TimetableRecordMapper timetableRecordMapper;
	private final ErrorMessageHandler errorMessageHandler;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
    public ResponseEntity<List<TimetableRecordDto>> findAll() {		
		
        return ResponseEntity.ok(timetableRecordMapper.toTimetableRecordDtos(timetableRecordService.findAll()));
    }
	
	@PostMapping
    public ResponseEntity<?> create(@Valid@RequestBody TimetableRecordDto timetableRecordDto,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageHandler.handle(bindingResult));
		}
		
        TimetableRecord timetableRecord = timetableRecordService.save(timetableRecordMapper.toTimetableRecord(timetableRecordDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(timetableRecordMapper.toTimetableRecordDto(timetableRecord));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        TimetableRecord timetableRecord = timetableRecordService.findById(id);
        
        if (timetableRecord == null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage(NOT_FOUND, null, Locale.getDefault()));
        }
               
        return ResponseEntity.ok(timetableRecordMapper.toTimetableRecordDto(timetableRecord));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid@RequestBody TimetableRecordDto timetableRecordDto, 
    		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageHandler.handle(bindingResult));
		}
		
        TimetableRecord timetableRecord = timetableRecordMapper.toTimetableRecord(timetableRecordDto);
        timetableRecord.setId(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(timetableRecordMapper.toTimetableRecordDto(timetableRecordService.save(timetableRecord)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        timetableRecordService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }	
}