package com.example.phoneservice.controller;

import com.example.phoneservice.entities.Phone;
import com.example.phoneservice.exception.*;
import com.example.phoneservice.service.IPhoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneController.class)
public class PhoneControllerTest {

    @MockBean
    public IPhoneService phoneService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    Phone REC_1 = new Phone(1L, "Samsung s1");
    Phone REC_2 = new Phone(2L, "Samsung s2");
    Phone REC_3 = new Phone(3L, "Samsung s3");
    Phone REC_4 = new Phone(4L, "Samsung s4");
    Phone REC_5 = new Phone(5L, "Samsung s5");

    @Test
    public void getAllPhonesSuccessTest() throws Exception {
        List<Phone> records = List.of(REC_1, REC_2, REC_3, REC_4, REC_5);

        Mockito.when(phoneService.getAllPhonesSorted("1")).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders.get("/phones?sort=1")
                                              .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneList", hasSize(5)))
                .andExpect(jsonPath("$.phoneList").isArray())
                .andExpect(jsonPath("$.phoneList[1].name", is("Samsung s2")));
    }

    @Test
    public void getPhoneByIdSuccessTest() throws Exception {
        Mockito.when(phoneService.findPhoneById(REC_1.getId())).thenReturn(Optional.of(REC_1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/phones/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Samsung s1")));
    }

    @Test
    public void getNonExistingPhoneByIdTest() throws Exception {
        Mockito.when(phoneService.findPhoneById(100L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/phones/100")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        .andExpect(r -> assertTrue(r.getResolvedException() instanceof PhoneNotFoundException))
        .andExpect(r -> assertEquals("Could not find phone with id: 100", r.getResolvedException().getMessage()));
    }

    @Test
    public void createPhoneSuccessTest() throws Exception {
        Phone p = new Phone(10L, "Nokia 3311");

        Mockito.when(this.phoneService.savePhone(p)).thenReturn(p);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/phones")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(p));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Nokia 3311")));

    }

    @Test
    public void createPhoneNotSuccessTest() throws Exception {
        Phone p = new Phone(10L, "");

        Mockito.when(this.phoneService.savePhone(p)).thenReturn(p);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/phones")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(p));

        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        response.addViolation(new Violation("name", "name of the phone cannot be empty"));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(r -> assertEquals(this.mapper.writeValueAsString(response), r.getResponse().getContentAsString()));

        p = new Phone(1L, "Samsung s22");
        Mockito.when(this.phoneService.savePhone(p)).thenThrow(new PhoneAlreadyExistsException(1L));


        mockRequest = MockMvcRequestBuilders.post("/phones")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(p));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(r -> assertTrue(r.getResolvedException() instanceof PhoneAlreadyExistsException))
                .andExpect(r -> assertEquals("Phone with id: 1 already exists!", r.getResolvedException().getMessage()));
    }

    @Test
    public void putPhoneTestSuccess() throws Exception {
        Phone p = new Phone(10L, "Nokia 3310");

        Mockito.when(this.phoneService.createOrUpdatePhone(p)).thenReturn(p);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/phones")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(p));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Nokia 3310")));

    }

    @Test
    public void putPhoneTestNotSuccess() throws Exception {
        Phone p = new Phone(null, "Nokia 3310");

        Mockito.when(this.phoneService.createOrUpdatePhone(p)).thenReturn(p);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/phones")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(p));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(r -> assertTrue(r.getResolvedException() instanceof InvalidRequestException))
                .andExpect(r -> assertEquals("phone or phone id must not be null!", r.getResolvedException().getMessage()));

    }

    @Test
    public void deletePhoneByIdSuccess() throws Exception {
        Mockito.when(this.phoneService.deletePhoneById(REC_2.getId())).thenReturn(REC_2);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/phones/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
