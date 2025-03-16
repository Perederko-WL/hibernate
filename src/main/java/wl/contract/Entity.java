package wl.contract;

/**
 * Base entity record.
 *
 * @param <T> Type of entity ID.
 */
public interface Entity<T> {

  /**
   * Returns entity ID.
   *
   * @return Entity ID.
   */
  T getId();

  /**
   * Sets entity ID.
   *
   * @param id Entity ID.
   */
  void setId(T id);
}
