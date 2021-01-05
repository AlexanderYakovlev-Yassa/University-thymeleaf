package ua.foxminded.yakovlev.university.controller.api;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ua.foxminded.yakovlev.university.dto.PositionDto;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.mapper.PositionMapper;
import ua.foxminded.yakovlev.university.service.PositionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/positions")
public class ApiPositionController {
	
	private final PositionService positionService;
	private final PositionMapper positionMapper;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
    public ResponseEntity<List<PositionDto>> findAll() {		
		
        return ResponseEntity.ok(positionMapper.toPositionDtos(positionService.findAll()));
    }
	
	@PostMapping
    public ResponseEntity<PositionDto> create(@Valid@RequestBody PositionDto positionDto) {
		
        Position position = positionService.save(positionMapper.toPosition(positionDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(positionMapper.toPositionDto(position));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<PositionDto> findById(@PathVariable Long id) {

        Position position = positionService.findById(id);
               
        return ResponseEntity.ok(positionMapper.toPositionDto(position));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
    public ResponseEntity<PositionDto> update(@PathVariable Long id, @Valid@RequestBody PositionDto positionDto) {
				
		Position position = positionMapper.toPosition(positionDto);
		position.setId(id);
		
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(positionMapper.toPositionDto(positionService.save(position)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        positionService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }	
}