package com.example.workshop.controllers;

import com.example.workshop.services.CompanyService;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class ImportXMLController {
    private CompanyService companyService;

    public ImportXMLController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/import/xml")
    public String index(Model model) {
        boolean companiesImported = this.companyService.areImported();
        boolean[] importStatuses = {false, false, false};

        model.addAttribute("areImported", importStatuses);

        return "/xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String viewImportCompanies(Model model) throws IOException {
        String companiesXML = this.companyService.getXMLContent();

        model.addAttribute("companies", companiesXML);

        return "/xml/import-companies";
    }
    @PostMapping("/import/companies")
    public String importCompanies(Model model) throws IOException, JAXBException {
        this.companyService.importCompanies();

        return "redirect:/import/xml";
    }
}
