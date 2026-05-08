package org.example.lab5_20202132.controller;

import org.example.lab5_20202132.model.Customer;
import org.example.lab5_20202132.repository.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CustomerController {
    final CustomerRepository customerRepository;
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @GetMapping(value = "")
    public String displayCustomers(Model model) {
        return "title";
    }
    @GetMapping(value = {"/cliente"})
    public String listarEmpleados(Model model) {

        model.addAttribute("employeeList", customerRepository.findAll());

        return "list";
    }

    @GetMapping("/new")
    public String nuevoEmpleadoFrm(Model model) {
        model.addAttribute("employee", new Customer());
        model.addAttribute("managers", customerRepository.findAll());
        return "newFrm";
    }

}
