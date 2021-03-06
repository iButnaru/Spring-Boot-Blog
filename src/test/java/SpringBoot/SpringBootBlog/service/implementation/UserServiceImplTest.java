package SpringBoot.SpringBootBlog.service.implementation;

import SpringBoot.SpringBootBlog.model.Post;
import SpringBoot.SpringBootBlog.repository.PostRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class UserServiceImplTest {

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImple postService = new PostServiceImple();


    @Test
    void findByUsername() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void save() {
        Post post = new Post();
        when(postRepository.save(post)).thenReturn(post);

    }

    @Test
    void loadUserByUsername() {
    }
}