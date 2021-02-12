package com.github.hronosf;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hronosf.mappers.ClientBankDataMapper;
import com.github.hronosf.mappers.ClientMapper;
import com.github.hronosf.mappers.DocumentMapper;
import com.github.hronosf.mappers.ProductDataMapper;
import com.github.hronosf.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@DirtiesContext
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    // ============================================> MOCK BEAN AREA:

    @MockBean
    protected RestTemplate restTemplate;

    // repositories:

    @MockBean
    protected ClientAccountActivationRepository clientAccountActivationRepository;

    @MockBean
    protected ClientBankDataRepository clientBankDataRepository;

    @MockBean
    protected ClientRepository clientRepository;

    @MockBean
    protected DocumentRepository documentRepository;

    @MockBean
    protected ProductDataRepository productDataRepository;

    @MockBean
    protected RoleRepository roleRepository;

    // mappers:
    @MockBean
    protected ClientMapper clientMapper;

    @MockBean
    protected DocumentMapper documentMapper;

    @MockBean
    protected ProductDataMapper productDataMapper;

    @MockBean
    protected ClientBankDataMapper clientBankDataMapper;

    @MockBean
    protected AWSCognitoIdentityProvider identityProvider;

    @MockBean
    protected AmazonS3 s3;

    // ============================================> TEST INIT AREA:

    @BeforeAll
    public void setupMockMvc() {
        DefaultMockMvcBuilder builder = MockMvcBuilders
                .webAppContextSetup(context);

        mvc = builder.build();
    }
}
