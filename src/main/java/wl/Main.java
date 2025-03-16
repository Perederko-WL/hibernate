package wl;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.codec.digest.Crypt;
import wl.user.contract.RoleRepository;
import wl.user.contract.UserRepository;
import wl.user.entity.RoleEntity;
import wl.user.entity.UserEntity;
import wl.user.repository.RoleRepositoryImpl;
import wl.user.repository.UserRepositoryImpl;

/**
 * Main.
 */
final class Main {

  private Main() {
  }

  /**
   * Main.
   *
   * @param args List of arguments.
   */
  public static void main(final String[] args) {
    RoleRepository roleRepository = new RoleRepositoryImpl();
    List<Long> rolesId = Stream.of(
            "Admin",
            "Creator",
            "Editor",
            "Manager",
            "BO",
            "Client",
            "Staff"
        )
        .map((String roleName) -> {
          RoleEntity role = new RoleEntity();
          role.setName(roleName);
          return role;
        })
        .map(roleRepository::create)
        .toList();

    System.out.println("Roles created: " + rolesId);

    UserRepository userRepository = new UserRepositoryImpl();

    UserEntity user = new UserEntity();
    user.setEmail("dsdfs@g.com");
    user.setRoles(roleRepository.getAll(rolesId));

    Long userId = userRepository.create(user);

    System.out.println(
        "User saved with ID: "
            + userId
            + " and roles: "
            + rolesId
    );

    UserEntity userSaved = userRepository.getById(userId).orElseThrow();
    userSaved.setPhoneNumber("+342352342324");
    userSaved.setPassword(Crypt.crypt("pwd"));
    userSaved.setRoles(Set.of(
        roleRepository.getById(rolesId.getFirst()).orElseThrow()
    ));

    userRepository.update(userSaved);

    Set<RoleEntity> userRoles = userRepository
        .getById(user.getId())
        .orElseThrow()
        .getRoles();

    System.out.println("Final user state ID: " + user.getId()
        + "; roles: " + userRoles.stream()
        .map(RoleEntity::getId)
        .toList()
    );
  }
}
