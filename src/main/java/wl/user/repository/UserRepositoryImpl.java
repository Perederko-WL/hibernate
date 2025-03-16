package wl.user.repository;

import wl.user.contract.UserRepository;
import wl.user.entity.UserEntity;

/**
 * Repository to maintain {@link UserEntity} entity.
 */
public final class UserRepositoryImpl
    extends Repository<UserEntity, Long> implements UserRepository {

}
