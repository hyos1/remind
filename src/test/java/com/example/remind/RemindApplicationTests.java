package com.example.remind;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RemindApplicationTests {

    @Test
    void contextLoads() {
        //given
        int a = 3;
        int b = 1;
        //when
        int result = a + b;
        //then
        Assertions.assertThat(result).isEqualTo(4);
    }

}
