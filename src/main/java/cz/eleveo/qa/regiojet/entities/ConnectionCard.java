package cz.eleveo.qa.regiojet.entities;

import com.codeborne.selenide.SelenideElement;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionCard {
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private Duration duration;
  private Integer numberOfStops;
  private BigDecimal journeyPriceCzk;
  private SelenideElement priceButton;

  @Override
  public String toString() {
    return "ConnectionCard{"
        + "departureTime="
        + departureTime
        + ", arrivalTime="
        + arrivalTime
        + ", duration="
        + duration
        + ", numberOfStops="
        + numberOfStops
        + ", journeyPriceCzk="
        + journeyPriceCzk
        + '}';
  }
}
