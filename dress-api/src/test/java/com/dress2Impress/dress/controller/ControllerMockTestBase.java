package com.dress2Impress.dress.controller;

import com.dress2Impress.api.GenericSuccessfulResponse;
import com.dress2Impress.api.ResponseFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
public abstract class ControllerMockTestBase<T> {

	protected static final String generatedDocumentRoot = "target/generated-rest-docs";

	@Mock
	protected ResponseFactory responseFactory;
	@Mock
	protected MessageSource messageSource;

	protected MockMvc mockMvc;
	protected ObjectMapper objectMapper;

	@Rule
	public RestDocumentation restDocumentation = new RestDocumentation(generatedDocumentRoot);

	protected abstract T controller();

	protected abstract String root();

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller()).setMessageConverters(messageConverter())
				.apply(documentationConfiguration(this.restDocumentation))
				.build();

		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@After
	public void clearOutputDirSystemProperty() {
		System.clearProperty("org.springframework.restdocs.outputDir");
	}

	protected MockHttpServletRequestBuilder doGet(final String api, final Object... urlVariables) {
		return get(root().concat(api), urlVariables);
	}

	protected MockHttpServletRequestBuilder doDelete(final String api, final Object... urlVariables) {
		return delete(root().concat(api), urlVariables);
	}

	protected MockHttpServletRequestBuilder doPost(final String api, final Object... urlVariables) {
		return post(root().concat(api), urlVariables);
	}

	protected MockHttpServletRequestBuilder doPut(final String api, final Object... urlVariables) {
		return put(root().concat(api), urlVariables);
	}

	protected MockMultipartHttpServletRequestBuilder doFileUpload(final String api, final Object... urlVariables) {
		return fileUpload(root().concat(api), urlVariables);
	}

	protected OngoingStubbing<GenericSuccessfulResponse<Object>> mockResponse(final Object data) {
		GenericSuccessfulResponse response = new GenericSuccessfulResponse();
		response.setData(data).setSuccessMessage("Success message");

		return when(responseFactory.buildResponse(any(), anyString()))
				.thenReturn(response);
	}

	protected OngoingStubbing<GenericSuccessfulResponse<Object>> mockResponse() {
		GenericSuccessfulResponse response = new GenericSuccessfulResponse();
		response.setSuccessMessage("Success message");

		return when(responseFactory.buildResponse(any(), anyString()))
				.thenReturn(response);
	}

	protected String getGeneratedDocumentationPath(final String controllersMethodName) {
		return String.format("%s/%s", controller().getClass().getSimpleName(), controllersMethodName);
	}

	protected String getGeneratedDocumentationPath(final String className, final String controllersMethodName) {
		return String.format("%s/%s", className, controllersMethodName);
	}

	private MappingJackson2HttpMessageConverter messageConverter() {
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		converter.setObjectMapper(objectMapper);
		return converter;
	}

}
