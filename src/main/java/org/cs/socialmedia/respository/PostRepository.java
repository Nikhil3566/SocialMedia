package org.cs.socialmedia.respository;

import javax.transaction.Transactional;

import org.cs.socialmedia.model.dao.Newsfeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Newsfeed, Integer> {

	@Modifying
	@Transactional
	@Query(value = "delete from newsfeed where newsfeed_owner_id =:followerId and post_originator_id=:followeeId", nativeQuery = true)
	void removefolloweesPostsFromFollowersFeed(@Param("followerId") String followerId, @Param("followeeId") String followeeId);

	@Modifying
	@Query(value = "select post_id from newsfeed where newsfeed_owner_id =:userId order by creation_date desc limit 20", nativeQuery = true)
	String[] get20RecentNewsfeed(@Param("userId") String userId);
}
