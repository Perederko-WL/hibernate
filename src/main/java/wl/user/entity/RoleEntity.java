package wl.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

/**
 * User entity record.
 */
@Entity
@Table(name = "roles")
@Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)
@NaturalIdCache
@Getter
@Setter
public final class RoleEntity implements wl.contract.Entity<Long> {

  /**
   * User ID.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Role name.
   */
  @NaturalId
  @Column(unique = true)
  private String name;

  /**
   * Role description.
   */
  private String description;

  @Override
  public boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoleEntity role = (RoleEntity) o;
    return Objects.equals(name, role.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
