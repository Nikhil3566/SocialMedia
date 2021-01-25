package org.cs.socialmedia.respository;

import java.util.List;

import javax.transaction.Transactional;

import org.cs.socialmedia.model.dao.FollowDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<FollowDetails, Integer> {

	@Modifying
	@Transactional
	@Query(value = "insert into FollowDetails (follower_id, followee_id) values (:followerId, :followeeId)", nativeQuery = true)
	void follow(@Param("followerId") String followerId, @Param("followeeId") String followeeId);

	@Modifying
	@Transactional
	@Query(value = "delete from followdetails fd where fd.follower_id =:followerId and fd.followee_id=:followeeId", nativeQuery = true)
	void unfollow(@Param("followerId") String followerId, @Param("followeeId") String followeeId);

	@Modifying
	@Query(value = "select followee_id from followdetails where follower_id =:followerId", nativeQuery = true)
	String[] getFollowees(@Param("followerId") String followerId);

	@Modifying
	@Query(value = "select follower_id from followdetails where followee_id =:followeeId", nativeQuery = true)
	List<String> getFollowers(@Param("followeeId") String followeeId);

	@Modifying
	@Query(value = "select follower_id from followdetails where follower_id =:followerId and followee_id=:followeeId", nativeQuery = true)
	List<String> isAlreadyFollowing(@Param("followerId") String followerId,@Param("followeeId") String followeeId);

	
}
