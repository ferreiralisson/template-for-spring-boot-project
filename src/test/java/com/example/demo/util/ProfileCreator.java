package com.example.demo.util;

import com.example.demo.domain.Profile;

public class ProfileCreator {

    public static Profile createProfileValid() {
        return Profile.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();
    }

    public static Profile createProfileToBeSaved() {
        return Profile.builder()
                .name("ROLE_USER")
                .build();
    }
}
