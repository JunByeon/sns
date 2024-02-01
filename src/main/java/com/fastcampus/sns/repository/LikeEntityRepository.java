package com.fastcampus.sns.repository;

import com.fastcampus.sns.model.entity.LikeEntity;
import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    // select count(*) from "like" where post_id = (내가 부르는 값)
    //    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post =:post")
    //    Integer countByPost(@Param("post") PostEntity post);

    long countByPost(PostEntity post); // JPA에서 제공하는 기본 메소드



    List<LikeEntity> findAllByPost(PostEntity post);

    @Transactional
    @Modifying
    @Query("UPDATE LikeEntity entity SET deleted_at = NOW() where entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity postEntity);

}
