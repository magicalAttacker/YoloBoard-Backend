package com.example.yoloboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/yolo-board-apis")
public class MainController {
    @Autowired
    private UserRespository userRespository;

    @GetMapping(path = "/get-user-list")
    public @ResponseBody List<User> getAllUsers() {
        return userRespository.findAll();
    }

    @GetMapping(path = "/get-user-by-id/{id}")
    public @ResponseBody User getUser(@PathVariable Integer id) {
        return userRespository.findById(id).orElseThrow();
    }

    @PostMapping(path = "/add-user")
    public @ResponseBody String addUser(@RequestParam String user, @RequestParam String pass) {
        User n = new User();
        n.setUser(user);
        n.setPass(pass);
        n.setDate(new Date());
        userRespository.save(n);
        return "save user " + n.getUser() + " at " + n.getDate();
    }

    @DeleteMapping(path = "/delete-user/{id}")
    public @ResponseBody String deleteUser(@PathVariable Integer id) {
        userRespository.deleteById(id);
        return "delete user success";
    }
}
