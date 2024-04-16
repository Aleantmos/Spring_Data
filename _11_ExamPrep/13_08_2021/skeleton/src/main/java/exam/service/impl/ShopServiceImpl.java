package exam.service.impl;

import exam.model.dto.shop.ShopSeedDTO;
import exam.model.dto.shop.ShopSeedRootDTO;
import exam.model.entity.Shop;
import exam.repository.ShopRepository;
import exam.service.ShopService;
import exam.service.TownService;
import exam.util.MyValidation;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ShopServiceImpl implements ShopService {
    private static final String SHOP_FILE_PATH = "src/main/resources/files/xml/shops.xml";
    private final ShopRepository shopRepository;
    private final MyValidation myValidation;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final TownService townService;
    public ShopServiceImpl(ShopRepository shopRepository, MyValidation myValidation, XmlParser xmlParser, ModelMapper modelMapper, TownService townService) {
        this.shopRepository = shopRepository;
        this.myValidation = myValidation;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOP_FILE_PATH));
    }

    @Override
    public String importShops() throws JAXBException, IOException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(SHOP_FILE_PATH, ShopSeedRootDTO.class)
                .getShops()
                .forEach(shopSeedDTO -> {
                    boolean filtered = validateShop(shopSeedDTO);
                    if (filtered) {
                        Shop shop = modelMapper.map(shopSeedDTO, Shop.class);
                        shop.setTown(townService.getTownByName(shopSeedDTO.getTown().getName()));


                        shopRepository.save(shop);

                        sb.append(String.format("Successfully imported Shop %s - %d",
                                shop.getName(), shop.getShopArea()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid shop")
                                .append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    private boolean validateShop(ShopSeedDTO shopSeedDTO) {
        return myValidation.isValid(shopSeedDTO) &&
                shopRepository.checkNameUniqueness(shopSeedDTO.getName());
    }

    @Override
    public Shop getShopByName(String name) {
        return shopRepository.getShopByName(name);
    }
}
