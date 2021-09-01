package cinema.entity.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnDTO {
    @JsonProperty("returned_ticket")
    private SeatDTO returnedTicket;

    public ReturnDTO(SeatDTO returnedTicket) {
        this.returnedTicket = returnedTicket;
    }

    public SeatDTO getReturnedTicket() {
        return returnedTicket;
    }

    public void setReturnedTicket(SeatDTO returnedTicket) {
        this.returnedTicket = returnedTicket;
    }
}
