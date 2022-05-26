package com.restclientapi.restclient.service.impl;

import com.restclientapi.restclient.Dto.UserDto;
import com.restclientapi.restclient.service.UserService;
import com.restclientapi.restclient.ui.Models.request.UserDetailsRequestModel;
import com.restclientapi.restclient.ui.Models.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final String path = "http://192.168.1.111:8080/restfulapiserver/";

    @Override
    public List<UserRest> getUsers(int page, int limit) {
        List<UserRest> returnValue = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String resourseUrl = path + "users?page=1";
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UserRest[]> response = restTemplate.exchange(resourseUrl, HttpMethod.GET,entity, UserRest[].class);
        returnValue.addAll(Arrays.asList(response.getBody()));
        return returnValue;
    }

    public UserRest guardarUsuario(UserDetailsRequestModel userDetails) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String resourseUrl = path + "users";
        /*
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("firstName", userDetails.getFirstName());
        map.add("lastName", userDetails.getLastName());
        map.add("email", userDetails.getEmail());
        map.add("password", userDetails.getPassword());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map,headers);*/

        HttpEntity<UserDetailsRequestModel> requestEntity = new HttpEntity<>(userDetails,headers);

        ResponseEntity<UserRest> responseEntity = restTemplate.postForEntity(resourseUrl, requestEntity, UserRest.class);

        return responseEntity.getBody();
    }

    @Override
    public UserRest actualizarUsuario(UserRest userRest) {
        UserDetailsRequestModel userDetails = new  UserDetailsRequestModel();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRest, userDto);
        BeanUtils.copyProperties(userDto, userDetails);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String resourseUrl =path + "users/" + userRest.getUserId();

        HttpEntity<UserDetailsRequestModel> requestEntity = new HttpEntity<>(userDetails,headers);

        ResponseEntity<UserRest> responseEntity = restTemplate.exchange(resourseUrl,HttpMethod.PUT,requestEntity, UserRest.class);

        return responseEntity.getBody();
    }

    @Override
    public UserRest getUserByUserId(String userId) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String resourseUrl = path + "users/" + userId;
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UserRest> response = restTemplate.exchange(resourseUrl,HttpMethod.GET,entity, UserRest.class);
        response.getBody();
        return response.getBody();
    }

    @Override
    public String eliminarUsuario(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String resourseUrl = path + "users/" + userId;
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(resourseUrl,HttpMethod.DELETE,entity, String.class);
        response.getBody();
        return response.getBody();
    }
}
