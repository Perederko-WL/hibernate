package wl.contract;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface to retrieve objects.
 *
 * @param <T> Type of object to retrieve.
 * @param <K> Type of object id.
 */
public interface Retrieval<T, K> {

  /**
   * Finds entity by id.
   *
   * @param id Entity id.
   * @return A container object which may or may not contain entity.
   */
  Optional<T> getById(K id);

  /**
   * Finds entities by ids.
   *
   * @param ids List of entity id.
   * @return Set of entities.
   */
  Set<T> getAll(List<K> ids);
}
