package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.model.Country;
import org.junit.Test;

import java.util.List;

public class CountryMapperTest extends BaseMapperTest {
    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSession()) {
            List<Country> countryList = session.selectList("org.example.mapper.CountryMapper.selectAll");
            printCountryList(countryList);
        }
    }

    private void printCountryList(List<Country> countryList) {
        for (Country country : countryList) {
            System.out.printf("%-4d%4s%4s\n", country.getId(), country.getCountryname(), country.getCountrycode());
        }
    }
}
