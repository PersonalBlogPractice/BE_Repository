package io.github.tato126.practice.post.service;

import io.github.tato126.practice.common.excetion.post.PostAccessDeniedException;
import io.github.tato126.practice.common.excetion.post.PostNotFoundException;
import io.github.tato126.practice.common.excetion.login.UserNotFoundException;
import io.github.tato126.practice.post.dto.request.PostRequest;
import io.github.tato126.practice.post.dto.request.PostUpdateRequest;
import io.github.tato126.practice.post.dto.response.PostResponse;
import io.github.tato126.practice.post.entity.Post;
import io.github.tato126.practice.post.repository.PostRepository;
import io.github.tato126.practice.user.entity.User;
import io.github.tato126.practice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * <p>
 * 게시글 생성, 조회(목록/단건), 수정, 삭제 등의 기능을 제공하며,
 * PUBLISHED 상태의 게시글만 공개되도록 제어합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 게시글을 생성합니다.
     * <p>
     * 인증된 사용자만 게시글을 작성할 수 있으며, 작성자 정보가 자동으로 설정됩니다.
     * </p>
     *
     * @param request   게시글 생성 요청 정보 (제목, 내용)
     * @param userEmail 인증된 사용자 이메일
     * @return 생성된 게시글 정보
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     */
    @Transactional
    public PostResponse register(PostRequest request, String userEmail) {

        log.debug("Creating post by user: {}", userEmail);

        // 1. 사용자 조회 (SecurityConfig에서 이미 인증 확인됨)
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        // 2. 게시글 생성 (작성자 설정)
        Post newPost = Post.form(request, author);

        // 3. 게시글 공개 설정

        // 3. 저장
        postRepository.save(newPost);


        log.debug("Post created: {}", newPost.getId());

        return PostResponse.form(newPost);
    }

    /**
     * 게시글을 수정합니다.
     * <p>
     * 작성자 본인만 수정할 수 있습니다.
     * </p>
     *
     * @param id                수정할 게시글 ID
     * @param postUpdateRequest 수정 요청 정보
     * @param userEmail         인증된 사용자 이메일
     * @return 수정된 게시글 정보
     * @throws PostNotFoundException     게시글이 존재하지 않는 경우
     * @throws PostAccessDeniedException 작성자가 아닌 경우
     * @throws UserNotFoundException     사용자가 존재하지 않는 경우
     */
    @Transactional
    public PostResponse update(Long id, PostUpdateRequest postUpdateRequest, String userEmail) {

        // 1. 사용자 조회
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        // 2. 게시글 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다. ID: " + id));

        // 3. 권한 체크 (작성자 본인인지 확인)
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new PostAccessDeniedException("본인이 작성한 포스트만 수정할 수 있습니다.");
        }

        // 4. 게시글 수정 (더티 체킹)
        post.update(postUpdateRequest.title(), postUpdateRequest.content(), postUpdateRequest.status());

        log.debug("Post updated: {}", post.getId());

        return PostResponse.form(post);
    }

    /**
     * 게시글을 삭제합니다.
     * <p>
     * 작성자 본인만 삭제할 수 있습니다.
     * </p>
     *
     * @param id        삭제할 게시글 ID
     * @param userEmail 인증된 사용자 이메일
     * @throws PostNotFoundException     게시글이 존재하지 않는 경우
     * @throws PostAccessDeniedException 작성자가 아닌 경우
     * @throws UserNotFoundException     사용자가 존재하지 않는 경우
     */
    @Transactional
    public void delete(Long id, String userEmail) {

        // 1. 사용자 조회
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        // 2. 게시글 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다. ID: " + id));

        // 3. 권한 체크 (작성자 본인인지 확인)
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new PostAccessDeniedException("본인이 작성한 포스트만 삭제할 수 있습니다.");
        }

        // 4. 삭제
        postRepository.delete(post);

        log.debug("Post deleted: {}", id);
    }

    /**
     * 발행된 게시글 목록을 페이징하여 조회합니다.
     * <p>
     * PUBLISHED 상태의 게시글만 조회되며, DRAFT 상태는 제외됩니다.
     * 기본 정렬은 생성일시 내림차순입니다.
     * </p>
     *
     * @param pageable 페이지 정보 (페이지 번호, 크기, 정렬)
     * @return 게시글 목록 (페이징 처리됨)
     */
    public Page<PostResponse> findAllPosts(Pageable pageable) {

        // PUBLISH 상태만 조회
        Page<Post> posts = postRepository.findAllByStatus(Post.PostStatus.PUBLISHED, pageable);

        // dto -> response
        return posts.map(PostResponse::form);
    }

    /**
     * 특정 게시글의 상세 정보를 조회합니다.
     * <p>
     * PUBLISHED 상태의 게시글만 조회 가능하며, DRAFT 상태인 경우 접근이 거부됩니다.
     * </p>
     *
     * @param id 조회할 게시글 ID
     * @return 게시글 상세 정보
     * @throws PostNotFoundException     게시글이 존재하지 않는 경우
     * @throws PostAccessDeniedException 비공개(DRAFT) 게시글에 접근하는 경우
     */
    public PostResponse findByPostId(Long id) {

        // post 조회
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("포스트를 찾을 수 없습니다. ID: " + id));

        // draft 면 비공개 포스트 입니다.
        if (post.getStatus() == Post.PostStatus.DRAFT) {
            throw new PostAccessDeniedException("비공개 포스트입니다.");
        }

        return PostResponse.form(post);
    }

}
