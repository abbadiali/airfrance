package com.users.test.service.impl;

import com.users.test.model.UserDto;
import com.users.test.exception.FunctionalException;
import com.users.test.mapper.UserMapper;
import com.users.test.model.User;
import com.users.test.repository.UserRepository;
import com.users.test.service.IUserService;
import com.users.test.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * Service implementation for User
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


/**
 * @param userDto
 * @return UserDto
 * @throws FunctionalException
 */
    @Override
    public UserDto createUser(UserDto userDto) throws FunctionalException {
        if (!DateUtils.isAdult(userDto.getBirthDate())){
            throw new FunctionalException("Only Adult residents are allowed to create an account!");
        }
        if(!userDto.getCountry().getCode().equals("FRA")){
            throw new FunctionalException("Only French residents are allowed to create an account!");
        }
        if(userRepository.existsByUsername(userDto.getUsername())){
            throw new FunctionalException("Username Already Exists!");
        }
        User user = userMapper.userDtoToUser(userDto);
        user = userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

/**
 * @param id
 * @return UserDto
 * @throws FunctionalException
 */
    @Override
    public UserDto getUserById(String id) throws FunctionalException {
        User user = userRepository.findById(id).orElseThrow(()-> new FunctionalException("User Not Found!"));
        return userMapper.userToUserDto(user);
    }
}