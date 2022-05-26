package com.restclientapi.restclient.ui.controller;

import com.restclientapi.restclient.service.UserService;
import com.restclientapi.restclient.ui.Models.request.UserDetailsRequestModel;
import com.restclientapi.restclient.ui.Models.response.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class Controlador {
    @Autowired
    UserService userService;

    @GetMapping("/listar")
    public String mostrarLista(Model model){
        List<UserRest> returnValue = new ArrayList<>();
        returnValue = userService.getUsers(0, 0);
        model.addAttribute("userrest",returnValue);
        return "index";
    }

    @GetMapping("/new")
    public String agregar(Model model){
        model.addAttribute("UserDetailsRequestModel", new UserDetailsRequestModel());
        return "form";
    }
    @PostMapping("/save")
    public String save(@Valid UserDetailsRequestModel userDetails, Model model){
        userService.guardarUsuario(userDetails);
        return "redirect:/listar";
    }

    //@PathVariable String id
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model modelo){
        modelo.addAttribute("UserRest",userService.getUserByUserId(id));
        return "formUpdate";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid UserRest userRest, Model model){
        userService.actualizarUsuario(userRest);
        return "redirect:/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable String id, Model modelo){
        userService.eliminarUsuario(id);
        return "redirect:/listar";
    }

}
