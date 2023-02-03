package com.javarush.parfenov.validator;

import com.javarush.parfenov.dto.CreateUserDto;
import com.javarush.parfenov.entity.Role;
import com.javarush.parfenov.service.UserService;
import com.javarush.parfenov.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginUserValidator implements Validator<CreateUserDto>{
    INSTANCE;
//    @Override
//    public List<ValidationError> validityChecker(CreateUserDto createUserDto) {
//        List<ValidationError> result = new ArrayList<>();
//        if(!loginIsValid(createUserDto)) {
//            result.add(new ValidationError(PropertiesUtil.get("ver.invalid.login")));
//        }
//        if(!passwordIsValid(createUserDto)) {
//            result.add(new ValidationError(PropertiesUtil.get("ver.invalid.password")));
//        }
//        if(!roleIsValid(createUserDto)) {
//            result.add(new ValidationError(PropertiesUtil.get("ver.invalid.role")));
//        }
//        if(result.isEmpty() && !loginIsUnique(createUserDto)) {
//            result.add(new ValidationError(PropertiesUtil.get("ver.login.not.unique")));
//        }
//        return result;
//    }
//    private boolean loginIsValid(CreateUserDto createUserDto) {
//        return checkStringValidity(createUserDto.getLogin(),
//                Pattern.compile(LOGIN_REGEX_PATTERN));
//    }
//    private boolean passwordIsValid(CreateUserDto createUserDto) {
//        return checkStringValidity(createUserDto.getPassword(),
//                Pattern.compile(PASSWORD_REGEX_PATTERN));
//    }
//
//    private boolean checkStringValidity(String str, Pattern pattern) {
//        Matcher matcher = pattern.matcher(str);
//        return matcher.find();
//    }
//    private boolean roleIsValid(CreateUserDto createUserDto) {
//        try {
//            Role.valueOf(createUserDto.getRole());
//        } catch (IllegalArgumentException e) {
//            return false;
//        }
//        return true;
//    }
//    private boolean loginIsUnique(CreateUserDto createUserDto) {
//        String login = createUserDto.getLogin();
//        return UserService.INSTANCE.getByLogin(login).isEmpty();
//    }

    @Override
    public List<ValidationError> validityChecker(CreateUserDto o) {
        return null;
    }
}
