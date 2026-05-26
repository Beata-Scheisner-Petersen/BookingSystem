package org.example.bookingsystem.pages.controller;

import jakarta.servlet.http.HttpSession;
import org.example.bookingsystem.customer.model.dto.CustomerInfoRequest;
import org.example.bookingsystem.pages.service.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        Long id = (Long) session.getAttribute("customerId");
        if (id == null) {
            return "redirect:/login";
        }
        CustomerInfoRequest customer = pageService.getCustomer(id);
        model.addAttribute("customer", customer);
        return "my_page";
    }

    @GetMapping("/updateCustomer")
    public String editCustomerPage() {
        return "update_customer";
    }

}
