package ro.unitbv.pguzun.leaderless.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

@Service
public class MainService {

    @Autowired
    private MainClient client;

    private List<String> nodes = ImmutableList.of("http://192.168.99.100:8080/", "http://192.168.99.100:8081/",
            "http://192.168.99.100:8082/");

    public Data get(String collection, String identifier) throws Exception {

        HashMap<String, Future<Data>> resultsNodes = new HashMap<>();

        for (String node : nodes) {
            Future<Data> future = client.getData(node, collection, identifier);
            resultsNodes.put(node, future);
        }

        HashMap<Data, Integer> resultsHits = new HashMap<>();
        for (String node : resultsNodes.keySet()) {
            Future<Data> future = resultsNodes.get(node);
            try {
                Data data = future.get();
                Integer count = resultsHits.get(data);
                if (count == null) {
                    count = 0;
                }
                resultsHits.put(data, count + 1);

            } catch (Exception e) {
                // no op
            }
        }

        final Map.Entry<Data, Integer> majorityVote = Collections.max(resultsHits.entrySet(),
                (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));

        if (majorityVote.getValue() < nodes.size() / 2 + 1) {
            throw new RuntimeException(
                    "Getting the value did not succed. As less than n/2+1 of nodes responded successfully");
        }

        resultsNodes.entrySet().stream().filter(entry -> {
            try {
                return !majorityVote.equals(entry.getValue().get());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }).forEach(entry -> {
            try {
                client.saveData(entry.getKey(), collection, identifier, majorityVote.getKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return majorityVote.getKey();
    }

    public void save(String collection, String identifier, Data data) throws Exception {

        HashMap<String, Future<Void>> resultsNodes = new HashMap<>();

        for (String node : nodes) {
            Future<Void> future = client.saveData(node, collection, identifier, data);
            resultsNodes.put(node, future);
        }

        int hits = 0;
        for (Entry<String, Future<Void>> entry : resultsNodes.entrySet()) {
            try {
                entry.getValue().get();
                hits++;
            } catch (Exception e) {
                // no op
            }
        }

        if (hits < nodes.size() / 2 + 1) {
            throw new RuntimeException(
                    "Saving the value did not succed. As less than n/2+1 of nodes responded successfully");
        }

    }
}
