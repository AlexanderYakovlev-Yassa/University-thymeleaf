package ua.foxminded.yakovlev.university.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.service.PositionService;

@Controller
@RequestMapping("/positions")
public class PositionController {
	
	private final ApplicationContext context;
	
	public PositionController(ApplicationContext context) {
		this.context = context;
	}
	
	@GetMapping()
    public String showPositions(Model model) {
		
		List<Position> positions = context.getBean("positionService", PositionService.class).findAll();
		model.addAttribute("positions", positions);
		
        return "positions/show-positions";
    }
}