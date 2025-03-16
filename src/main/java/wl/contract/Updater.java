package wl.contract;

/**
 * Interface to update entities.
 *
 * @param <T> Type of entity to store.
 * @param <R> Type of returned value.
 */
public interface Updater<T, R> {

  /**
   * Updates an entity.
   *
   * @param entity Entity to update.
   * @return Result of update.
   */
  R update(T entity);
}
