package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.service.PositionService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {
	
	private final PositionService positionService;
	
	@GetMapping()
    public String showPositions(Model model) {
		
		model.addAttribute("positions", positionService.findAll());
		
        return "positions/show-positions";
    }
}