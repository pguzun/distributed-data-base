package ro.unitbv.pguzun.leaderless.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MainClient {

    @Autowired
    private RestTemplate template;

    @Async
    public ListenableFuture<Data> getData(String client, String collection, String identifier) throws Exception {
        URI uri = UriComponentsBuilder.fromUriString(client).path("/{collection}/{id}").build(collection, identifier);
        Data response = template.getForObject(uri, Data.class);

        return new AsyncResult<Data>(response);
    }

    @Async
    public ListenableFuture<Void> saveData(String client, String collection, String identifier, Data data) throws Exception {
        URI uri = UriComponentsBuilder.fromUriString(client).path("/{collection}/{id}").build(collection, identifier);
        template.put(uri, new HttpEntity<>(data));
        
        return new AsyncResult<Void>(null);
    }
}
