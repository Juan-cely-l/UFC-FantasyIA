package com.ufc.UFC_FantasyIA.controller;

import com.ufc.UFC_FantasyIA.dto.FighterResponseDto;
import com.ufc.UFC_FantasyIA.config.WebConfig;
import com.ufc.UFC_FantasyIA.service.FighterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FighterController.class)
@Import(WebConfig.class)
class FighterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FighterService fighterService;

    @Test
    void shouldListFightersUnderFightersResource() throws Exception {
        UUID id = UUID.randomUUID();
        FighterResponseDto fighter = new FighterResponseDto(
                id,
                "Jon Jones",
                "Bones",
                193,
                214,
                37,
                "United States"
        );

        when(fighterService.findAll(org.mockito.ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(List.of(fighter)));

        mockMvc.perform(get("/api/v1/fighters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(id.toString()))
                .andExpect(jsonPath("$.content[0].name").value("Jon Jones"));
    }
}
