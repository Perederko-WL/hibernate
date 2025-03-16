package wl.user.contract;

import wl.contract.Creator;
import wl.contract.Retrieval;
import wl.contract.Updater;
import wl.user.entity.UserEntity;

/**
 * Interface for user repository.
 */
public interface UserRepository extends
    Creator<UserEntity, Long>,
    Updater<UserEntity, Boolean>,
    Retrieval<UserEntity, Long> {

}
