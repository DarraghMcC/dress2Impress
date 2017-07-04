package com.dress2Impress.dress.controller;


import com.dress2Impress.api.Response;
import com.dress2Impress.api.ResponseFactory;
import com.dress2Impress.common.dto.dress.JacketCheckDTO;
import com.dress2Impress.dress.service.DressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.dress2Impress.common.constants.ApplicationConstants.URL;


@RestController
@RequestMapping(value = URL.API_ROOT_URL)
public class DressController {

    private final DressService dressService;
    private final ResponseFactory responseFactory;

    @Autowired
    public DressController(final DressService dressService, final ResponseFactory responseFactory) {
        this.dressService = dressService;
        this.responseFactory = responseFactory;
    }

    @RequestMapping(value = URL.DRESS.JACKET_CHECK, method = RequestMethod.GET)
    public Response checkJacketWeather() throws Exception {
        final JacketCheckDTO jacketCheckDTO = dressService.checkLondonJacketWeather();

        return responseFactory.buildResponse(jacketCheckDTO, "jacket.check");
    }

}
