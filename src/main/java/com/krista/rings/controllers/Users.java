package com.krista.rings.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.krista.rings.models.Role;
import com.krista.rings.models.User;
import com.krista.rings.services.RoleService;
import com.krista.rings.services.UserService;
import com.krista.rings.validator.UserValidator;

@Controller
public class Users {
	
	private UserService userService;
	private RoleService roleService;
	private UserValidator userValidator;
	
	public Users(UserService userService, RoleService roleService, UserValidator userValidator) {
		this.userService = userService;
		this.roleService = roleService;
		this.userValidator = userValidator;
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam(value="error", required=false)String error, @RequestParam(value="logout", required=false) String logout,@ModelAttribute("user") User user, Model model) {
		
		if(error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if(logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
	
		return "loginAndRegPage";
	}
	
	@RequestMapping("/registration")
	public String registerForm(@Valid @ModelAttribute("user") User user) {
		return "loginAndRegPage";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, @ModelAttribute("role") Role role) {
		
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			return "loginAndRegPage";
		}
		if(roleService.findByName("ROLE_ADMIN").getUsers().size() < 1) {
//			userService.saveUserWithAdminRole(user);
		}
		else {
			System.out.println("Saved as User");
//			userService.saveWithUserRole(user);
		}
		return "redirect:/home";
	}

	@RequestMapping(value= {"/", "/home"})
    public String home(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
//        User user = userService.findByEmail(email);
//        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, YYYY");
//        Date date = new Date();
//        Date created = user.getCreated_at();
//        model.addAttribute("created", dateFormat.format(created));
//        model.addAttribute("date", dateFormat.format(date));
        return "homePage";
    }
	
	@RequestMapping("/admin")
	public String admin(Principal principal, Model model) {
		model.addAttribute("admin", userService.findByEmail(principal.getName()));
		model.addAttribute("users", userService.allUsers());
		return "admin";
	}

}
