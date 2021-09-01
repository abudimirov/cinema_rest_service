package cinema.entity;

import cinema.entity.DTO.SeatDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CinemaPlan {
    @JsonProperty("total_rows")
    private int totalRows;
    @JsonProperty("total_columns")
    private int totalColumns;
    @JsonProperty("available_seats")
    private List<SeatDTO> availableSeats;
    @JsonIgnore
    private HashMap<String, SeatDTO> purchasedTickets;

    public CinemaPlan() {
        this.totalRows = 9;
        this.totalColumns = 9;

        List<SeatDTO> availableSeats = new ArrayList<>();
        int price = 10;
        for (int i = 1; i <= totalRows; i++) {
            if (i > 4) {
                price = 8;
            }
            for (int j = 1; j <= totalColumns; j++) {
                availableSeats.add(new SeatDTO(i, j, price));
            }
        }

        this.availableSeats = availableSeats;
        this.purchasedTickets = new HashMap<>();
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<SeatDTO> getAvailableSeats() {
        List<SeatDTO> filteredList = availableSeats.stream().filter(seatDTO -> seatDTO.isAvailable()).collect(Collectors.toList());

        return filteredList;
    }

    public void setAvailableSeats(List<SeatDTO> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public HashMap<String, SeatDTO> getPurchasedTickets() {
        return purchasedTickets;
    }
}
