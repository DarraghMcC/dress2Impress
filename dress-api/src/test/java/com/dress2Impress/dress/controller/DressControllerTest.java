package com.dress2Impress.dress.controller;

import com.dress2Impress.api.ResponseFactory;
import com.dress2Impress.common.constants.ApplicationConstants;
import com.dress2Impress.common.dto.dress.JacketCheckDTO;
import com.dress2Impress.dress.service.DressService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DressControllerTest extends ControllerMockTestBase<DressController> {

	@InjectMocks
	protected DressController dressController;

	@Mock
	protected DressService dressService;
	@Mock
	protected ResponseFactory responseFactory;

	@Test
	public void checkJacket() throws Exception {
		final JacketCheckDTO jacketCheckDTO = new JacketCheckDTO().setJacketDetails("need a jacket message")
				.setJacketNeeded(true);

		when(dressService.checkLondonJacketWeather()).thenReturn(jacketCheckDTO);

		mockMvc.perform(doGet(ApplicationConstants.URL.DRESS.JACKET_CHECK)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(jacketCheckDTO)))
				.andDo(document(getGeneratedDocumentationPath("get")))
				.andExpect(status().is(HttpStatus.OK.value()))
				.andReturn();

		verify(dressService).checkLondonJacketWeather();
	}

	@Override
	protected DressController controller() {
		return this.dressController;
	}

	@Override
	protected String root() {
		return ApplicationConstants.URL.API_ROOT_URL;
	}

	@Override
	public void setUp() {
		super.setUp();
	}
}
