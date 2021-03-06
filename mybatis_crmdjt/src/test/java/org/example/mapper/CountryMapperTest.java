package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.model.Country;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CountryMapperTest extends BaseMapperTest {
    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSession()) {
            List<Country> result = session.selectList("org.example.mapper.CountryMapper.selectAll");
            printCountryList(result);
        }
    }

    private void printCountryList(List<Country> countryList) {
        for (Country country : countryList) {
            System.out.printf("%-4d%4s%4s\n", country.getId(), country.getCountryname(), country.getCountrycode());
        }
    }
}
