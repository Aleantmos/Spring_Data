package softuni.service.user;

import softuni.entities.Client;

public interface UserService {

    String registerUser(Client client);

    Client findUserById(long id);


}

