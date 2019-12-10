package ua.ies.project.cityStats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.Map;

@Slf4j
@RabbitListener(queues = "cities")
public class Receiver {

    @Autowired
    private CityRepository repo;

    private final int instance;

    Receiver(int i){
        this.instance = i;
    }

    @RabbitHandler
    public void receive(byte[] in) throws InterruptedException, JsonProcessingException {
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("instance " + this.instance + "[x] Received '" + in + "'");
        process(in);
        watch.stop();
        log.info("instance " + this.instance + "[x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void process(byte[] in) throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(new String(in), Map.class);
        log.info("Message: " + new String(in));
        City newData = new City((String) map.get("name"), (String) map.get("district"),
                (double) map.get("lat"), (double) map.get("lon"));
        newData.setCo((double) map.get("co"));
        newData.setCo2((double) map.get("co2"));
        newData.setSo2((double) map.get("so2"));
        newData.setNoise((double) map.get("noise"));
        newData.setRainPh((double) map.get("rainPh"));
        updateCity(newData, Long.valueOf((Integer) map.get("id")));
    }

    private City updateCity(City newData, long id) {
        return repo.findById(id).map(city ->
        {
            city.setCo(newData.getCo());
            city.setCo2(newData.getCo2());
            city.setSo2(newData.getSo2());
            city.setNoise(newData.getNoise());
            city.setRainPh(newData.getRainPh());
            return repo.save(city);
        }).orElseGet(() -> // if not exists, creates new one
                {
                    newData.setId(id);
                    return repo.save(newData);
                }
        );
    }
}