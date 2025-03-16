package wl.user.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.entity.EntityPersister;
import wl.contract.Creator;
import wl.contract.Entity;
import wl.contract.Retrieval;
import wl.contract.Updater;

/**
 * Repository to maintain entity.
 *
 * @param <T> Type of entity.
 * @param <I> Type of returned value.
 */
public abstract class Repository<T extends Entity<I>, I> implements
    Creator<T, I>,
    Updater<T, Boolean>,
    Retrieval<T, I> {

  /**
   * Storage.
   */
  private final EntityManagerFactory emf =
      Persistence.createEntityManagerFactory(this.getClass().getPackageName());

  /**
   * Retrieves entity class name.
   *
   * @return Entity class name.
   */
  @SuppressWarnings("unchecked")
  private Class<T> getEntityClass() {
    return (Class<T>) ((ParameterizedType) this.getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<T> getById(final I id) {
    try (EntityManager entityManager = emf.createEntityManager()) {
      return Optional.ofNullable(entityManager.find(this.getEntityClass(), id));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<T> getAll(final List<I> ids) {
    try (EntityManager entityManager = emf.createEntityManager()) {
      return new HashSet<>(entityManager
          .unwrap(Session.class)
          .byMultipleIds(this.getEntityClass())
          .multiLoad(ids));
    } catch (Exception e) {
      return Set.of();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public I create(final T entity) {
    EntityTransaction transaction = null;
    try (EntityManager entityManager = emf.createEntityManager()) {
      transaction = entityManager.getTransaction();
      transaction.begin();

      EntityPersister entityPersister = entityManager
          .unwrap(SharedSessionContractImplementor.class)
          .getEntityPersister(entity.getClass().getName(), entity);

      if (entityPersister.hasNaturalIdentifier()) {
        Map<String, Object> naturalIdMap = Arrays.stream(
                entityPersister.getNaturalIdentifierProperties()
            )
            .boxed()
            .collect(Collectors.toMap(
                (final Integer key) -> entityPersister.getPropertyNames()[key],
                (final Integer key) -> entityPersister.getValues(entity)[key]
            ));

        T natural = entityManager
            .unwrap(Session.class)
            .byNaturalId(this.getEntityClass())
            .with(LockOptions.UPGRADE)
            .using(naturalIdMap)
            .load();

        if (natural != null) {
          entity.setId(natural.getId());
          entityManager.merge(entity);
        } else {
          entityManager.persist(entity);
        }
      } else {
        entityManager.persist(entity);
      }

      transaction.commit();

      return entity.getId();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean update(final T entity) {
    EntityTransaction transaction = null;
    try (EntityManager entityManager = emf.createEntityManager()) {
      transaction = entityManager.getTransaction();
      transaction.begin();

      entityManager.merge(entity);

      transaction.commit();

      return true;
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
    }

    return false;
  }
}
