package ro.unitbv.pguzun.leaderless.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

@Service
public class MainService {

    @Autowired
    private MainClient client;

    private List<String> nodes = ImmutableList.of("http://localhost:8080/");

    public Data get(String collection, String identifier) throws Exception {

        HashMap<String, Future<Data>> resultsNodes = new HashMap<>();

        for (String node : nodes) {
            Future<Data> feature = client.getData(node, collection, identifier);
            resultsNodes.put(node, feature);
        }

        HashMap<Data, Integer> resultsHits = new HashMap<>();
        for (String node : resultsNodes.keySet()) {
            Future<Data> future = resultsNodes.get(node);
            Data data = future.get();

            Integer count = resultsHits.get(data);
            if (count == null) {
                count = 0;
            }
            resultsHits.put(data, count + 1);
        }

        final Data majorityVote = Collections
                .max(resultsHits.entrySet(), (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()))
                .getKey();

        resultsNodes.entrySet().stream().filter(entry -> {
            try {
                return !majorityVote.equals(entry.getValue().get());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }).forEach(entry -> {
            try {
                client.saveData(entry.getKey(), collection, identifier, majorityVote);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return majorityVote;
    }

    public void save(String collection, String identifier, Data data) throws Exception {
        for (String node : nodes) {
            client.saveData(node, collection, identifier, data);
        }
    }
}
