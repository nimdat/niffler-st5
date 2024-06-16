package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    static UserRepository getInstance() {
        if ("sjdbc".equals(System.getProperty("repo"))) {
            return new UserRepositorySpringJdbc();
        }

        return new UserRepositoryJdbc();
    }

    UserAuthEntity createUserInAuth(UserAuthEntity user);

    UserEntity createUserInUserData(UserEntity user);

    UserAuthEntity updateUserInAuth(UserAuthEntity user);

    UserEntity updateUserInUserdata(UserEntity user);

    Optional<UserEntity> findUserInUserdataById(UUID id);
}