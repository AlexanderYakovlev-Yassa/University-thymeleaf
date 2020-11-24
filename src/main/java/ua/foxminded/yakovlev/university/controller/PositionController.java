package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Position;
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
	
	@PostMapping("/new")
    public String save(
    		@RequestParam(name = "name") String name) {
		
		Position position = new Position();
		position.setName(name);
		positionService.save(position);
		
		return "redirect:/positions";
	}
	
	@PostMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
		positionService.delete(id);
        return "redirect:/positions";
    }
	
	@PostMapping("/edit")
    public String edit(
    		@RequestParam(name = "id") Long id,
    		@RequestParam(name = "name") String name) {
		
		Position position = new Position();
		position.setId(id);
		position.setName(name);
		positionService.update(position);
		
		return "redirect:/positions";
	}
}