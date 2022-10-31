package com.portfoliobaez.gnb.Interface;

import com.portfoliobaez.gnb.DTO.UserRequestDTO;
import com.portfoliobaez.gnb.DTO.UserResponseDTO;


public interface ISessionService {
    UserResponseDTO login(UserRequestDTO user);
    void regis(UserRequestDTO user);
}
