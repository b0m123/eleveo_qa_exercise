package cz.eleveo.qa.regiojet.pojos;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import lombok.Data;

@Data
public class RouteResponsePojo {
  private String id;
  private Long departureStationId;
  private Date departureTime;
  private Long arrivalStationId;
  private Date arrivalTime;
  private ArrayList<String> vehicleTypes;
  private int transfersCount;
  private int freeSeatsCount;
  private int priceFrom;
  private int priceTo;
  private int creditPriceFrom;
  private int creditPriceTo;
  private int pricesCount;
  private boolean actionPrice;
  private boolean surcharge;
  private boolean notices;
  private boolean support;
  private boolean nationalTrip;
  private boolean bookable;
  private Object delay;
  private String travelTime;
  private String vehicleStandardKey;
  private Duration journeyDuration;

  @Override
  public String toString() {
    return String.format(
        "Travel by %s, \nDeparture at: %s, Arrival at: %s, Duration: %s, \nPrice from: %s CZK, \nNumber of stops: %s",
        this.vehicleTypes.toString(),
        new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.departureTime),
        new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.arrivalTime),
        travelTime,
        priceFrom,
        (transfersCount > 0 ? transfersCount : "through direction"));
  }

  public Duration getJourneyDuration() {
    this.journeyDuration =
        Duration.between(
            this.arrivalTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            this.departureTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    return this.journeyDuration;
  }
}
