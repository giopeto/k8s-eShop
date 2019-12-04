package com.files.v1.files.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.files.config.TestConfig;
import com.files.v1.files.domain.FilesToDomainMapper;
import com.files.v1.files.domain.FilesUpload;
import com.files.v1.files.service.FilesService;
import com.files.v1.files.service.FilesToDomainMapperService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.files.common.FilesConstants.FILES_BASE_URL;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FilesController.class)
@Import(TestConfig.class)
@TestPropertySource("classpath:test.properties")
public class FilesControllerTest {

    private static final String FILES_URL = "/" + FILES_BASE_URL + "/files";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FilesService filesService;
    @MockBean
    private FilesToDomainMapperService filesToDomainMapperService;

    private ObjectMapper jacksonObjectMapper;
    private String domainId;

    @Before
    public void setUp() {
        jacksonObjectMapper = new ObjectMapper();
        jacksonObjectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        domainId = randomUUID().toString();
    }

    @Test
    @Ignore
    public void testSave() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "imageName.jpeg", String.valueOf(MediaType.IMAGE_JPEG), "image data".getBytes());
        FilesUpload filesUpload = new FilesUpload(domainId, asList(imageFile));

        String fileId = randomUUID().toString();
        FilesToDomainMapper filesToDomainMapper = new FilesToDomainMapper(domainId, asList(fileId));

        when(filesService.store(filesUpload)).thenReturn(filesToDomainMapper);
        String request = jacksonObjectMapper.writeValueAsString(filesUpload);
        String response = jacksonObjectMapper.writeValueAsString(filesService.store(filesUpload));

        mockMvc.perform(post(FILES_URL)
                .content(request)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    public void testGetByDomainId() throws Exception {

        String fileId = randomUUID().toString();
        FilesToDomainMapper filesToDomainMapper = new FilesToDomainMapper(domainId, asList(fileId));

        when(filesToDomainMapperService.getByDomainId(domainId)).thenReturn(filesToDomainMapper);
        String response = jacksonObjectMapper.writeValueAsString(filesToDomainMapperService.getByDomainId(domainId));

        mockMvc.perform(get(FILES_URL + "/" + domainId)
                .content(domainId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }
}
