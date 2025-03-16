package wl.contract;

/**
 * Interface to store entities.
 *
 * @param <T> Type of entity to store.
 * @param <R> Type of returned value.
 */
public interface Creator<T, R> {

  /**
   * Stores an entity.
   *
   * @param entity Entity to store.
   * @return Result of entity store.
   */
  R create(T entity);
}
