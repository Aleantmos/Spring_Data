package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.customer.CustomerSeedDTO;
import exam.model.entity.Customer;
import exam.repository.CustomerRepository;
import exam.service.CustomerService;
import exam.service.TownService;
import exam.util.MyValidation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final String CUSTOMER_FILE_PATH = "src/main/resources/files/json/customers.json";
    private final CustomerRepository customerRepository;
    private final MyValidation myValidation;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final TownService townService;

    public CustomerServiceImpl(CustomerRepository customerRepository, MyValidation myValidation, ModelMapper modelMapper, Gson gson, TownService townService) {
        this.customerRepository = customerRepository;
        this.myValidation = myValidation;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMER_FILE_PATH));
    }

    private boolean filterCustomers(CustomerSeedDTO customerSeedDTO) {
        return myValidation.isValid(customerSeedDTO) &&
                customerRepository.checkEmailUniqueness(customerSeedDTO.getEmail());
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readCustomersFileContent(), CustomerSeedDTO[].class))
                .forEach(customerSeedDTO -> {
                    boolean filtered = filterCustomers(customerSeedDTO);

                    if (filtered) {
                        Customer customer = modelMapper.map(customerSeedDTO, Customer.class);

                        customer.setTown(townService.getTownByName(customerSeedDTO.getTown().getName()));

                        customerRepository.save(customer);

                        sb.append(String.format("Successfully imported Customer %s %s - %s",
                                        customer.getFirstName(), customer.getLastName(), customer.getEmail()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid Customer")
                                .append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
