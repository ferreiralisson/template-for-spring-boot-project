package com.example.demo.util;

import com.example.demo.dto.PasswordChangeDTO;

public class PasswordChangeCreator {

    public static PasswordChangeDTO passwordChangeCreatorValid(){
        return PasswordChangeDTO.builder()
                .oldPassword("12345678")
                .newPassword("87654321")
                .build();
    }
}
