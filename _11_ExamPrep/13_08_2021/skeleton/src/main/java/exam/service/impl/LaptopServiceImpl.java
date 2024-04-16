package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.BestLaptopProjection;
import exam.model.dto.laptop.LaptopSeedDTO;
import exam.model.entity.Laptop;
import exam.model.enums.WarrantyType;
import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import exam.service.ShopService;
import exam.util.MyValidation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class LaptopServiceImpl implements LaptopService {
    private static final String LAPTOP_FILE_PATH = "src/main/resources/files/json/laptops.json";
    private final LaptopRepository laptopRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;
    private final ShopService shopService;

    public LaptopServiceImpl(LaptopRepository laptopRepository, Gson gson, ModelMapper modelMapper, MyValidation myValidation, ShopService shopService) {
        this.laptopRepository = laptopRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
        this.shopService = shopService;
    }

    @Override
    public boolean areImported() {
        return laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOP_FILE_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readLaptopsFileContent(), LaptopSeedDTO[].class))
                .forEach(laptopSeedDTO -> {
                    boolean filtered = filterLaptop(laptopSeedDTO);

                    if (filtered) {
                        Laptop laptop = modelMapper.map(laptopSeedDTO, Laptop.class);
                        laptop.setShop(shopService.getShopByName(laptopSeedDTO.getShop().getName()));

                        laptopRepository.save(laptop);

                        sb.append(String
                                .format("Successfully imported Laptop %s - %.2f - %d - %d",
                                        laptop.getMacAddress(),
                                         laptop.getCpuSpeed(),
                                        laptop.getRam(),
                                        laptop.getStorage()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid laptop")
                                .append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    private boolean filterLaptop(LaptopSeedDTO laptopSeedDTO) {
        return myValidation.isValid(laptopSeedDTO) &&
                laptopRepository.checkMacAddressUniqueness(laptopSeedDTO.getMacAddress()) &&
                checkWarranty(laptopSeedDTO.getWarrantyType());
    }

    private boolean checkWarranty(WarrantyType warrantyType) {
        return warrantyType != null;
    }

    @Override
    public String exportBestLaptops() {
        List<BestLaptopProjection> bestLaptops =
                laptopRepository.getBestLaptops();

        StringBuilder sb = new StringBuilder();

        for (BestLaptopProjection bestLaptop : bestLaptops) {
            sb.append(String.format("\"Laptop - %s\n" +
                    "*Cpu speed - %.2f\n" +
                    "**Ram - %d\n" +
                    "***Storage - %d\n" +
                    "****Price - %.2f\n" +
                    "#Shop name - %s\n" +
                    "##Town - %s\n",
                    bestLaptop.getMacAddress(),
                    bestLaptop.getCpuSpeed(),
                    bestLaptop.getRam(),
                    bestLaptop.getStorage(),
                    bestLaptop.getPrice(),
                    bestLaptop.getShopName(),
                    bestLaptop.getTownName()
                    ))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
