package sample.cafekiosk.spring.docs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {

    protected MockMvc mockMvc;


 /* 이러한 방식은 `@SpringBootTest` 를 사용하여
    RestDocs 를 띄우는 것인데 즉, 스프링 서버를 띄워 문서를 생성하는 방식
    -> 근데 문서 보는데 서버 띄울 필요가 있을까?
    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider provider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(provider))
                .build();
    }*/

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider){
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .apply(documentationConfiguration(provider))
                .build();
    }

    /**
     * 스프링 서버를 안띄우기 위해 setup에 연결할 Controller를 인자로 처리하면 된다.
     * 하지만 모든 컨트롤러를 실행할 때 마다 넣을 수 없으니 추상 클래스로 생성
     */
    protected  abstract Object initController();
}
