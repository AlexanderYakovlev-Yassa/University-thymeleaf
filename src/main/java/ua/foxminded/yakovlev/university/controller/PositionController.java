package ua.foxminded.yakovlev.university.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.service.PositionService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {
	
	private final PositionService positionService;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping()
    public String showPositions(Model model) {
		
		model.addAttribute("positions", positionService.findAll());
		
        return "positions/show-positions";
    }
	
	@GetMapping("/new")
    public String create(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			Model model
			) {
		
		model.addAttribute("newPosition", new Position());
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "positions/new-position";
	}
	
	@PostMapping("/new")
    public String save(
			RedirectAttributes redirectAttributes,			
			@Valid@ModelAttribute("newPosition") Position newPosition,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			
			return "redirect:/positions/new";
		}
		
		positionService.save(newPosition);
		
		return "redirect:/positions";
	}
	
	@GetMapping("/edit")
    public String showEditPage(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
    		@RequestParam(name = "id") Long id,
    		Model model
    		) {	

		model.addAttribute("position", positionService.findById(id));
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "positions/edit-position";
	}
	
	@PostMapping("/edit")
    public String edit(
			RedirectAttributes redirectAttributes,
			@Valid@ModelAttribute("position") Position position,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("id", position.getId());
			
			return "redirect:/positions/edit";
		}
		
		positionService.update(position);
		
		return "redirect:/positions";
	}
	
	@PostMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
		positionService.delete(id);
        return "redirect:/positions";
    }
}