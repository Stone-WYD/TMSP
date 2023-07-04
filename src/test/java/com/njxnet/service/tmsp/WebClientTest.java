package com.njxnet.service.tmsp;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.model.query.SysUserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@SpringBootTest
public class WebClientTest {

    @Resource
    private WebClient webClient;

    @Test
    public void useTest(){
        WebClient webClient = WebClient.create();
        Mono<String> mono = webClient.get().uri("https://www.baidu.com").retrieve()
                .bodyToMono(String.class);
        System.out.println(mono.block());
    }

    @Test
    public void testUseTMSP(){
        SysUserQuery query = new SysUserQuery();
        query.setSize(2L);
        query.setPage(1L);
        Mono<AjaxResult> resultMono = webClient.post().uri("/users/manage/query")
                .bodyValue(query).retrieve().bodyToMono(AjaxResult.class);

        AjaxResult result = resultMono.block();
        if (result.getData() != null) {
            Object data = result.getData();
            System.out.println(data);
        }
    }
}
