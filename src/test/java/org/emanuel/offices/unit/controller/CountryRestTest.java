package org.emanuel.offices.unit.controller;

import org.emanuel.offices.business.ICountryService;
import org.emanuel.offices.business.dto.CountryGetDto;
import org.emanuel.offices.controller.CountryRest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryRest.class)
@Import(CountryRestTest.MockConfig.class)
class CountryRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ICountryService countryService;

    static class MockConfig {
        @Bean
        public ICountryService countryService() {
            return Mockito.mock(ICountryService.class);
        }
    }

    @Test
    void getCountryById_shouldReturnCountry() throws Exception {
        CountryGetDto dto = CountryGetDto.builder().id(1L).name("Argentina").build();
        Mockito.when(countryService.getCountryById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/countries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Argentina"));
    }

    @Test
    void getAllCountries_shouldReturnList() throws Exception {
        List<CountryGetDto> countries = List.of(
                CountryGetDto.builder().id(1L).name("Argentina").build(),
                CountryGetDto.builder().id(2L).name("Brasil").build()
        );
        Mockito.when(countryService.getAllCountries()).thenReturn(countries);

        mockMvc.perform(get("/api/v1/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("Brasil"));
    }

    @Test
    void getAllCountries_shouldReturnNoContent() throws Exception {
        Mockito.when(countryService.getAllCountries()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/countries"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addCountry_shouldCreateCountry() throws Exception {
        CountryGetDto getDto = CountryGetDto.builder().id(3L).name("Chile").build();
        Mockito.when(countryService.addCountry("Chile")).thenReturn(getDto);

        mockMvc.perform(post("/api/v1/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Chile\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/countries/3"))
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Chile"));
    }

    @Test
    void updateCountry_shouldReturnUpdatedCountry() throws Exception {
        CountryGetDto updatedDto = CountryGetDto.builder().id(1L).name("Uruguay").build();
        Mockito.when(countryService.updateCountry(eq(1L), eq("Uruguay"))).thenReturn(updatedDto);

        mockMvc.perform(put("/api/v1/countries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Uruguay\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Uruguay"));
    }

    @Test
    void deleteCountry_shouldCallService() throws Exception {
        Mockito.doNothing().when(countryService).deleteCountry(1L);

        mockMvc.perform(delete("/api/v1/countries/1"))
                .andExpect(status().isOk());
    }
}