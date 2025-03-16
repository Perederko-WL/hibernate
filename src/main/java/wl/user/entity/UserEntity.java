package wl.user.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * User entity record.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public final class UserEntity implements wl.contract.Entity<Long> {

  /**
   * User ID.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Email.
   */
  private String email;

  /**
   * Phone number.
   */
  private String phoneNumber;

  /**
   * Password.
   */
  private String password;

  /**
   * User roles list.
   */
  @ManyToMany(
      fetch = FetchType.EAGER
  )
  @CollectionTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id")
  )
  private Set<RoleEntity> roles = new LinkedHashSet<>();

}
