package com.tutorials.social.springsocial.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by archithrapaka on 6/30/17.
 */
@Controller
@RequestMapping("/")
public class FacebookConnectController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public FacebookConnectController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        String [] fields = { "id", "email",  "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, fields);

        model.addAttribute("facebookProfile", userProfile);
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);
        return "hello";
    }
}
