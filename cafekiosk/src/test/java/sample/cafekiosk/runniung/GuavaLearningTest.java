package sample.cafekiosk.runniung;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GuavaLearningTest {
    
    @DisplayName("주어진 개수만큼 List를 파티셔닝 한다. ")
    @Test
    void partitionLearningTest1(){

        // given
        // test.1 List를 나누어 List를 여러개 생성
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition  = Lists.partition(integers, 3); // 3개씩 나눔

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1, 2, 3), List.of(4, 5, 6)
                ));
    }

    @DisplayName("주어진 개수만큼 List를 파티셔닝 한다. ")
    @Test
    void partitionLearningTest2(){

        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition  = Lists.partition(integers, 4);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1, 2, 3, 4), List.of(5, 6)
                ));
    }

    @DisplayName("")
    @Test
    void test(){
        // given
        // MultiMap -> 하나의 Key에 여러 가지 map을 넣을 수 있음
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");


        // when
        Collection<String> strings = multimap.get("커피");

        // then
        assertThat(strings).hasSize(3)
                .isEqualTo(List.of("아메리카노", "카페라떼", "카푸치노"));
    }
}
