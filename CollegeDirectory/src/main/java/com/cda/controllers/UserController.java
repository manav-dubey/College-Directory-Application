package com.cda.controllers;

import com.cda.entities.Roles;
import com.cda.entities.Users;
import com.cda.payload.UserDto;
import com.cda.repositries.UserRepository;
import com.cda.services.RolesServices;
import com.cda.services.UserServices;
import com.cda.utils.Message;
import com.cda.utils.MessageType;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    static List<Roles> allRoles ;

    private UserServices userService;

    @Autowired
    private  RolesServices roleService;

    @Autowired
    private UserRepository userRepository;

    public UserController(UserServices userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loadLoginPage(){
        return "user/loginpage";
    }

    @GetMapping("/home")
    public String loadHomePage(){
        return "user/home";
    }


    @PostConstruct
    public void initializeRoleList() {
        allRoles = roleService.getAllRoles();
    }

    /*
    @ModelAttribute
    public void methodForButtonDisableLogin(Principal principal,Model model){
        if(principal  != null){
            String principalName = principal.getName();
            System.out.println("Principal Name: " + principalName);
            Optional<Users> users = userRepository.findByUsernameOrEmail(principalName, principalName);
            System.out.println("userToCheck: " + users.get());
            model.addAttribute("userToCheck", users.get());
        }else{
            model.addAttribute("userToCheck", null);
        }
    }
*/


    // registration page
    @GetMapping("/signup")
    public String register(Model model) {
        UserDto userDto = new UserDto();
        initializeRoleList();
        model.addAttribute("userDto", userDto);
        model.addAttribute("roles", allRoles);
        return "user/signuppage";
    }


    // processing register

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserDto userDto, BindingResult rBindingResult,
                                  HttpSession session,Model model) {
        System.out.println("Processing registration");

        System.out.println(userDto);

        // validate form data
        if (rBindingResult.hasErrors()) {
            model.addAttribute("roles", allRoles);
            return "user/signuppage";
        }

        UserDto savedUser = userService.saveUser(userDto);

        System.out.println("user saved :");

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message", message);

        // redirectto login page
        return "redirect:login";
    }

}
