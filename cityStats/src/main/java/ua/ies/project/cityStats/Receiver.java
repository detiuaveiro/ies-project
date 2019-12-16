package ua.ies.project.cityStats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.*;

@Slf4j
@RabbitListener(queues = "cities")
public class Receiver {

    @Autowired
    private CityRepository cities;

    @Autowired
    private StatsRepository stats;

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
        Stat stat = createStat(newData);
        updateStat(stat, Long.valueOf((Integer) map.get("id")));
    }

    private Stat createStat(City city){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY , 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date day = calendar.getTime();
        return new Stat(day, city);
    }

    private Stat updateStat(Stat newData, long cityId){
        long id = -1; //no stat has this id so if the id remains w/ this value, a new stat will be created
        updateCity(newData.getCity(), cityId);
        City city;  //use the instance of City that is already saved - it corresponds to the city of the current Stat
        if (cities.findById(cityId).isPresent()){  //must be present bcs it was saved two lines before
            city = cities.findById(cityId).get();
            List<Stat> list = stats.findByDayAndCity(newData.getDay(), city);
            if (!list.isEmpty())
                id = list.get(0).getId(); //gets the id of the existing stat if there is any that corresponds to current city and day
            return stats.findById(id).map(stat ->
            {
                stat.setDay(newData.getDay());
                stat.setCity(city);
                stat.updateStat(newData);
                return stats.save(stat);
            }).orElseGet(() -> //if not exists creates new one
                    {
                        newData.setCity(city);
                        newData.updateStat(newData);
                        return stats.save(newData);
                    }
                    );
        }
        log.info("Could not insert stat: nonexistent city");
        return null;
    }

    private City updateCity(City newData, long id) {
        return cities.findById(id).map(city ->
        {
            city.setCo(newData.getCo());
            city.setCo2(newData.getCo2());
            city.setSo2(newData.getSo2());
            city.setNoise(newData.getNoise());
            city.setRainPh(newData.getRainPh());
            city.setNo2(newData.getNo2());
            city.setO3(newData.getO3());
            city.setTemperature(newData.getTemperature());
            return cities.save(city);
        }).orElseGet(() -> // if not exists, creates new one
                {
                    newData.setId(id);
                    return cities.save(newData);
                }
        );
    }
}