package com.krista.rings.controllers;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.krista.rings.models.Ring;
import com.krista.rings.models.Role;
import com.krista.rings.models.Team;
import com.krista.rings.models.User;
import com.krista.rings.repositories.RingRepository;
import com.krista.rings.repositories.RoleRepository;
import com.krista.rings.services.RingService;
import com.krista.rings.services.RoleService;
import com.krista.rings.services.TeamService;
import com.krista.rings.services.UserService;
import com.krista.rings.validator.UserValidator;

@Controller
public class Users {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private RingService ringService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RingRepository ringRepository;
	
	public Users(UserService userService, RoleService roleService, UserValidator userValidator, RingService ringService, TeamService teamService, RoleRepository roleRepository, RingRepository ringRepository) {
		this.userService = userService;
		this.roleService = roleService;
		this.userValidator = userValidator;
		this.ringService = ringService;
		this.teamService = teamService;
		this.roleRepository = roleRepository;
		this.ringRepository = ringRepository;
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
		return "redirect:/home";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, @ModelAttribute("role") Role role) {
		
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			return "loginAndRegPage";
		}
		if(roleService.findByName("ROLE_ADMIN").getUsers().size() < 1) {
			userService.saveUserWithAdminRole(user);

		}
		else {
			System.out.println("Saved as User");
			userService.saveWithUserRole(user);
		}
		return "redirect:/login";
	}

	@RequestMapping(value= {"/", "/home"})
    public String home(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        model.addAttribute("rings", ringService.allRings());
     
        
        return "homePage";
    }
	
	@RequestMapping("/admin")
	public String admin(Principal principal, Model model) {
		model.addAttribute("admin", userService.findByEmail(principal.getName()));
		model.addAttribute("users", userService.allUsers());
		model.addAttribute("rings", ringService.allRings());
		
		
		return "admin";
	}
	
	@RequestMapping("/admin/{id}/createRing")
	public String makeRings(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user",userService.getById(id));
		return "rings";
	}
	
	@RequestMapping("/admin/{id}/ring")
	public String makeRing(@PathVariable("id") Long id, @ModelAttribute("ring") Ring ring, @ModelAttribute("user") User user, Model model) {
		model.addAttribute("user",userService.getById(id));
		return "rings";
	}
	
	@PostMapping("/admin/{id}/ring")
	public String addRing(@PathVariable("id") Long id, @ModelAttribute("ring") Ring ring, BindingResult result, Model model, @ModelAttribute("user") User user) {
		ringService.createRing(ring);
		model.addAttribute("user",userService.getById(id));
		return "redirect:/admin";
	}
	
	@RequestMapping("/selectRing/{currentUser_id}")
	public String selectRing(@ModelAttribute("ring") Ring ring, @ModelAttribute("currentUser") User user, @PathVariable("currentUser_id") Long id) {
		return "homePage";
	}
	
		
	@PostMapping("/selectRing/{currentUser_id}")	
	public String takeRing(@ModelAttribute("ring") Ring ring, Model model, @ModelAttribute("currentUser") User user, @PathVariable("currentUser_id") Long id) {
	        	Ring ringCheck = ringService.findByName(ring.getName());
	    		User userCheck = userService.getById(id);
	    		ringCheck.setUser(userCheck);
	    		ringCheck.setPickedUp(true);
	    		ringService.update(ringCheck);
	    		return "redirect:/home";
	}
	
	
	@RequestMapping("/admin/{id}/createTeam")
	public String addTeam(@PathVariable("id") Long id, @ModelAttribute("team")Team team, @ModelAttribute("user") User user, Model model, Principal principal) {
		model.addAttribute("users", userService.allUsers());
		model.addAttribute("teams", teamService.allTeams());
		model.addAttribute("user", new User());
		model.addAttribute("team", new Team());
		model.addAttribute("user",userService.getById(id));
		model.addAttribute("admin", userService.findByEmail(principal.getName()));
		return "teamsAdmin";
	}
	
	@RequestMapping("/admin/{id}/makeTeam")
	public String makeTeam(@PathVariable("id") Long id, @ModelAttribute("team") Team team, Model model) {
		model.addAttribute("user",userService.getById(id));
		return "teamsAdmin";
	}
	
	@PostMapping("/admin/{id}/makeTeam")
	public String addTeam(@PathVariable("id") Long id, @ModelAttribute("team") Team team, BindingResult result, Model model) {
		teamService.createTeam(team);
		model.addAttribute("user",userService.getById(id));
		model.addAttribute("team", team);

//		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//		Date current = new Date();
//		model.addAttribute("current", df.format(current.getTime()));
//		System.out.print(df.format(current.getTime()));
//		<c:out value="${Math.floor((current- user.getCreated_at().getTime()) / (24*60*60*1000))} days"/>

		
		return "redirect:/admin/{id}/createTeam";
	}
	
	@RequestMapping("/admin/{id}/addToTeam")
	public String selectGuild(@PathVariable("id") Long id, @ModelAttribute("user") User user, @ModelAttribute("team") Team team, @RequestParam("guild") Long idTeam, @RequestParam("username") Long idUser, Model model ) {
		model.addAttribute("user", userService.getById(id));
		return "teamsAdmin";
	}
	
	@PostMapping("/admin/{id}/addToTeam")
	public String makeTeam(@PathVariable("id") Long id, Model model, @RequestParam("guild") Long idTeam, @RequestParam("username") Long idUser, RedirectAttributes redirectAttributes) {
		Team team = teamService.findTeamById(idTeam);
		User user = userService.findUserById(idUser);
		model.addAttribute("user",userService.getById(id));
		
		int capacity = team.getSize();
		
		if(team.getUsers().size() == capacity) {
			redirectAttributes.addFlashAttribute("message", "Guild is at capacity, please select another");
		
		}
		else {
			List<User> users = team.getUsers();
			users.add(user);
			team.setUsers(users);
			teamService.update(team);
			model.addAttribute("user", user);
			model.addAttribute("team", team);
		}
		
		return "redirect:/admin/{id}/createTeam";
	}
	
	
	@RequestMapping("/admin/{adminId}/makeAdmin/{id}")
	public String makeAdmin(@PathVariable("adminId") Long adminId, @PathVariable("id") Long userId, Model model) {
		User user = userService.getById(userId);
		List<Role> userRoles = user.getRoles();
		userRoles.add(roleService.findByName("ROLE_ADMIN"));
		userService.update(user);
		model.addAttribute("user", userService.findUserById(userId));
		model.addAttribute("admin",userService.findUserById(adminId));
;		System.out.println("Made user an admin");
		return "redirect:/admin/"+adminId+"/createTeam";
	}
	
	@RequestMapping("/admin/{adminId}/delete/{id}")
	public String delete(@PathVariable("adminId") Long adminId,@PathVariable("id") Long userId, Model model) {
		userService.destroy(userId);
		model.addAttribute("admin", userService.findUserById(adminId));
;		model.addAttribute("user", userService.findUserById(userId));
		return "redirect:/admin/"+adminId+"/createTeam";
	}
	
	@RequestMapping("/destroy/{id}")
	public String loseRing(@PathVariable("id") Long id, @ModelAttribute("ring") Ring ring, @ModelAttribute("user") User user, Model model) {
		Ring ringCheck = ringRepository.findOne(id);
		ringCheck.setUser(null);
		ringCheck.setPickedUp(false);
		ringService.update(ringCheck);
		return "redirect:/home";
	}
	
	
	@RequestMapping("/admin/{adminId}/guilds/{teamId}")
	public String viewGuild(@PathVariable("adminId") Long adminId,@PathVariable("teamId") Long teamId, Model model) {
		model.addAttribute("team", teamService.findTeamById(teamId));
		model.addAttribute("admin", userService.getById(adminId));
		return "guilds";
	}

	
	@RequestMapping("/admin/{adminId}/update/{userId}/onTeam/{teamId}")
	public String makeUpdate(@PathVariable("adminId") Long adminId, @PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId, Model model) {
		model.addAttribute("user", userService.findUserById(userId));
		model.addAttribute("admin", userService.findUserById(adminId));
		model.addAttribute("team", userService.findUserById(teamId));
		System.out.println("check1");
		return "updateUser";
	}
	
	@PostMapping("/admin/{adminId}/update/{userId}/onTeam/{teamId}")
	public String saveUpdate(@PathVariable("adminId") Long adminId, @PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId, Model model, @RequestParam("username") String username) {
		User userCheck = userService.getById(userId);
		userCheck.setUsername(username);
		userService.update(userCheck);
		model.addAttribute("admin", userService.findUserById(adminId));
		model.addAttribute("team", teamService.findTeamById(teamId));
		model.addAttribute("user", userCheck);
		return "redirect:/admin/"+adminId+"/update/"+userId+"/onTeam/"+teamId;
	}

	
//	@RequestMapping("/admin/{adminid}/backToGuild/{teamId}")
//	public String returnPage(@PathVariable("adminid") Long adminId, @PathVariable("teamId") Long teamId, Model model ) {
//		model.addAttribute("user",userService.getById(adminId));
//		model.addAttribute("team", userService.getById(teamId));
//		return "redirect:/admin/guilds/{teamId}";
//	}
	
}
