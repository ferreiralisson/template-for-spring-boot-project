package com.example.demo.util;

import com.example.demo.dto.EmailDTO;

public class EmailCreator {

    public static EmailDTO emailCreator(){
        return EmailDTO.builder()
                .email("alisson.advir@gmail.com")
                .title("nova senha")
                .subjectMatter("teste")
                .build();
    }
}
