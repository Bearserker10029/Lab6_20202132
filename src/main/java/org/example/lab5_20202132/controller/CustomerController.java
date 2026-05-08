package org.example.lab5_20202132.controller;

import jakarta.validation.Valid;
import org.example.lab5_20202132.model.Customer;
import org.example.lab5_20202132.repository.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
        model.addAttribute("document_type");
        return "newFrm";
    }

    @PostMapping("/save")
    public String guardarNuevoEmpleado(@ModelAttribute("employee") @Valid Customer customer,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", customer);
            model.addAttribute("document_type");
            if(customer.getId()>0){
                return "editFrm";
            }
            return "newFrm";
        }
        Customer customerexiste = null;
        if(customer.getId()>0){
            customerexiste = customerRepository.findById(customer.getId()).orElse(null);
        }
        if(customerexiste!=null){
            customerexiste.setDocument(customer.getDocument());
            customerexiste.setName(customer.getName());
            customerexiste.setDocumentType(customer.getDocumentType());
        }
        if (customer.getDocumentType().equals("DNI")){
            if(customer.getDocument().length()!=8){
                bindingResult.rejectValue("documentType", "documentType.documentType", "El documento debe ser igual a 8 caracteres");
            }
        } else if(customer.getDocumentType().equals("RUC")){
            if(customer.getDocument().length()!=11){
                bindingResult.rejectValue("documentType", "documentType.documentType", "El documento debe ser igual a 11 caracteres");
            }
        }
        customerRepository.save(customer);
        redirectAttributes.addFlashAttribute("message", "Se ha registrado correctamente");


    return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editarEmpleado(Model model, @PathVariable int id, RedirectAttributes redirectAttributes) {

        Optional<Customer> optEmployee = customerRepository.findByIdWithRelations(id);

        if (optEmployee.isPresent()) {
            Customer employee = optEmployee.get();
            model.addAttribute("employee", employee);
            redirectAttributes.addFlashAttribute("msg","Empleado actualizado correctamente");
            return "editFrm";
        } else {
            return "redirect:/list";
        }
    }
}

