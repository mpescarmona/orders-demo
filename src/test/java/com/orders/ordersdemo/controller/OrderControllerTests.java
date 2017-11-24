/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.orders.ordersdemo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.ordersdemo.domain.Order;
import com.orders.ordersdemo.domain.Vendor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);


    @Test
    public void getExistingOrderShouldReturnValidOrder() throws Exception {
        this.mockMvc.perform(get("/orders").param("orderNumber", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value("1"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.sourceSystem").value("PROMPT"))
                .andExpect(jsonPath("$.vendor.number").value("1"))
                .andExpect(jsonPath("$.vendor.name").value("NIKE"));
    }

    @Test
    public void getNotExistingOrderShouldReturnNotFound() throws Exception {
        this.mockMvc.perform(get("/orders").param("orderNumber", "NotExistingOrder"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void postExistingOrderShouldReturnCreated() throws Exception {

        Vendor vendor = new Vendor("1", "NIKE");
        Order order = new Order("1", "1", "PENDING", "PROMPT", vendor, new Date(), null);

        String json = jsonMapper.writeValueAsString(order);

        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void postInvalidOrderShouldReturnNotAcceptable() throws Exception {

        Order order = new Order("1", null, null, null, null, null, null);

        String json = jsonMapper.writeValueAsString(order);

        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();
    }
}
