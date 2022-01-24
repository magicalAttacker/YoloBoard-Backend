package com.example.yoloboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Dream it

@Controller
@RequestMapping(path = "/yolo-board-apis")
public class MainController {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private ContentRespository contentRespository;

    @GetMapping(path = "/get-user-list")
    public @ResponseBody List<User> getAllUsers() {
        return userRespository.findAll();
    }

    @GetMapping(path = "/get-user-by-id/{id}")
    public @ResponseBody User getUser(@PathVariable Integer id) {
        return userRespository.findById(id).orElseThrow();
    }

    @GetMapping(path = "/get-user-by-name/{name}")
    public @ResponseBody User getUser(@PathVariable String name) {
        List<User> m = userRespository.findAll();
        Integer userId = -1;
        for (User n:
             m) {
            if (n.getUser().equals(name)) {
                userId = n.getId();
                break;
            }
        }
        return userRespository.findById(userId).orElseThrow();
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

    @DeleteMapping(path = "delete-user-all")
    public @ResponseBody String deleteAll() {
        userRespository.deleteAll();
        return "clear";
    }

    @GetMapping(path = "/get-content-list")
    public @ResponseBody List<Content> getContentAll() {
        return contentRespository.findAll();
    }

    @GetMapping(path = "/get-content-by-id/{id}")
    public @ResponseBody Content getContentById(@PathVariable Integer id) {
        return contentRespository.findById(id).orElseThrow();
    }

    @PostMapping(path = "/post-content")
    public @ResponseBody String postContent(@RequestParam String post, @RequestParam Integer userid) {
        Content n = new Content();
        n.setContent(post);
        n.setUserId(userid);
        n.setDate(new Date());
        contentRespository.save(n);
        return "save content success";
    }

    @DeleteMapping(path = "/delete-content-by-id")
    public @ResponseBody String deleteContent(@RequestParam Integer id) {
        contentRespository.deleteById(id);
        return "delete success";
    }

    @DeleteMapping(path = "/delete-contents-by-userid")
    public @ResponseBody String deleteContentsByUserId(@RequestParam Integer userid) {
        List<Content> m = contentRespository.findAll();
        m.removeIf(n -> !n.getUserId().equals(userid));
        List<Integer> ids = new ArrayList<>();
        for (Content n:
             m) {
            ids.add(n.getId());
        }
        contentRespository.deleteAllById(ids);
        return "selected clear";
    }

    @DeleteMapping(path = "/delete-content-all")
    public @ResponseBody String deleteContent() {
        contentRespository.deleteAll();
        return "clear";
    }

    @GetMapping(path = "/get-contents-by-userid/{userid}")
    public @ResponseBody List<Content> findAllByUserId(@PathVariable Integer userid) {
        List<Content> m = contentRespository.findAll();
        m.removeIf(n -> !n.getUserId().equals(userid));
        return m;
    }
}
