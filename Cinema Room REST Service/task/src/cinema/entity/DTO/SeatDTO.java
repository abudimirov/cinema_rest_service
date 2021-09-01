package cinema.entity.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SeatDTO {
    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean isAvailable;

    public SeatDTO(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    @JsonProperty
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatDTO seatDTO = (SeatDTO) o;
        return row == seatDTO.row && column == seatDTO.column && price == seatDTO.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, price);
    }
}
