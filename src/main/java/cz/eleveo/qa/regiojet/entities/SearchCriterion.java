package cz.eleveo.qa.regiojet.entities;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchCriterion {
  private String cityFrom;
  private String cityTo;
  private LocalDate departureDate;
  private LocalDate arrivalDate;

  @Override
  public String toString() {
    return "SearchCriterion{"
        + "cityFrom='"
        + cityFrom
        + '\''
        + ", cityTo='"
        + cityTo
        + '\''
        + ", departureDate="
        + departureDate
        + ", arrivalDate="
        + arrivalDate
        + '}';
  }
}
