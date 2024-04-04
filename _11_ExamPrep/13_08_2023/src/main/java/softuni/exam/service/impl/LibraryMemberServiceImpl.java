package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.LibraryMemberSeedDTO;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.MyValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private static final String LIBRARY_MEMBER_FILE_PATH = "src/main/resources/files/json/library-members.json";
    private final LibraryMemberRepository libraryMemberRepository;
    private final Gson gson;
    private final MyValidation myValidation;
    private final ModelMapper modelMapper;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, Gson gson, MyValidation myValidation, ModelMapper modelMapper) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.myValidation = myValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(LIBRARY_MEMBER_FILE_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readLibraryMembersFileContent(), LibraryMemberSeedDTO[].class))
                .filter(libraryMemberSeedDTO -> {
                    boolean isValid = myValidation.isValid(libraryMemberSeedDTO);

                    if (isValid && phoneNumberUniqueness(libraryMemberSeedDTO.getPhoneNumber())) {
                        sb.append(String.format("Successfully imported library member %s - %s",
                                libraryMemberSeedDTO.getFirstName(), libraryMemberSeedDTO.getLastName()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid library member")
                                .append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(libraryMemberSeedDTO -> modelMapper.map(libraryMemberSeedDTO, LibraryMember.class))
                .forEach(libraryMemberRepository::save);

        return sb.toString().trim();
    }

    private boolean phoneNumberUniqueness(String phoneNumber) {
        return !libraryMemberRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public LibraryMember getLibraryMemberById(int id) {
        return libraryMemberRepository.getMemberById(id);
    }
}
