package wl.user.contract;

import wl.contract.Creator;
import wl.contract.Retrieval;
import wl.contract.Updater;
import wl.user.entity.RoleEntity;

/**
 * Interface for role repository.
 */
public interface RoleRepository extends
    Creator<RoleEntity, Long>,
    Updater<RoleEntity, Boolean>,
    Retrieval<RoleEntity, Long> {

}
