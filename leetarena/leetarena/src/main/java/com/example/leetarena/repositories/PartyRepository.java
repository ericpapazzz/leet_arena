package com.example.leetarena.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.leetarena.models.Party;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {

    @Query(value = "SELECT ap FROM Party ap WHERE ap.user.user_id = :user_id")
    List<Party> getActivePartiesByUserId(@Param("user_id") Integer userId);

    @Query(value = "SELECT par FROM Party par WHERE par.invitation_code = :invitation_code")
    Optional<Party> getPartyByInvitationCode(@Param("invitation_code") String invitationCode);
    
    @Modifying
    @Query(value = "DELETE FROM Party ap WHERE ap.user.user_id = :user_id")
    void deleteActivePartiesByUserId(@Param("user_id") Integer userId);


}
