package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {

    private final ProfileDao profileDao;
    private final UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }


    @GetMapping
    public Profile getProfile(Principal principal)
    {
        int userId = getUserId(principal);
        return profileDao.getByUserId(userId);
    }

    @PutMapping
    public void updateProfile(@RequestBody Profile profile, Principal principal)
    {
        int userId = getUserId(principal);
        profileDao.update(userId, profile);
    }
    @PostMapping
    public Profile createProfile(@RequestBody Profile profile, Principal principal)
    {
        int userId = getUserId(principal);
        profile.setUserId(userId);
        return profileDao.create(profile);
    }

    private int getUserId(Principal principal)
    {
        if (principal == null)
            throw new RuntimeException("User not authenticated");

        String username = principal.getName();
        return userDao.getIdByUsername(username);
    }
}

