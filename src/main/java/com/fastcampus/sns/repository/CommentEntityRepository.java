package com.fastcampus.sns.repository;

import com.fastcampus.sns.model.entity.CommentEntity;
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
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
    // PostEntity를 통해서 DB에서 가지고 오는데 이땐 인덱스가 안걸려 있으므로
    // 조인이 되어 Post ID로 검색하므로 속도가 굉장히 느려지게 되므로 성능상 이슈가 생긴다
    // 따라서 애초에 DB 테이블을 만들때부터 Index를 Post ID로 한다고 설정해주면 Join이 일어나지 않으므로 더 빨라진다.
    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE CommentEntity entity SET deleted_at = NOW() where entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity postEntity);

//    void deleteAllByPost(PostEntity post); // delete 같은 것은 JPA에서 제공하는 것을 쓰지 말고 위와 같이 직접 query를 작성해서 쓰는 것이 훨씬 효율적으로 서버를 만들 수 있다
}
