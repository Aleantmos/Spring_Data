package softuni.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.constants.Messages;
import softuni.entities.Client;
import softuni.repositories.UserRepository;

import static softuni.constants.ExceptionMessages.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String registerUser(Client client) {


            if (userRepository.findAll().contains(client)) {
                throw new IllegalStateException(ALREADY_REGISTERED);
            }
            if (client.getAge() < 18) {
                throw new IllegalStateException(NOT_OF_AGE);
            }
            if (client.getUsername().isEmpty()) {
                throw new IllegalStateException(NOT_VALID_USERNAME);
            }
            userRepository.save(client);

        return Messages.REGISTERED_SUCCESSFULLY;
    }

    @Override
    public Client findUserById(long id) {

        return userRepository.findUserById(id);
    }
}
