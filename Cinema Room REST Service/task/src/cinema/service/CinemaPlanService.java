package cinema.service;

import cinema.entity.CinemaPlan;
import cinema.entity.DTO.PurchaseDTO;
import cinema.entity.DTO.ReturnDTO;
import cinema.entity.DTO.SeatDTO;
import cinema.entity.Statistics;
import cinema.entity.Token;
import cinema.exceptions.CinemaPlanException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class CinemaPlanService {

    private final CinemaPlan cinemaPlan = new CinemaPlan();
    private final Statistics statistics = new Statistics(0, cinemaPlan.getTotalColumns() * cinemaPlan.getTotalRows(), 0);
    private final HashMap<String, SeatDTO> purchasedTickets = cinemaPlan.getPurchasedTickets();

    public CinemaPlan getCinemaPlan() {
        return cinemaPlan;
    }

    public ResponseEntity<PurchaseDTO> makePurchase(int row, int column) {
        if (row > cinemaPlan.getTotalRows() || row < 1 || column > cinemaPlan.getTotalColumns() || column < 1) {
            throw new CinemaPlanException("The number of a row or a column is out of bounds!");
        }
        int price = 10;
        if (row > 4) {
            price = 8;
        }
        SeatDTO desiredSeat = new SeatDTO(row, column, price);
        List<SeatDTO> availableSeats = cinemaPlan.getAvailableSeats();

        if (availableSeats.contains(desiredSeat)) {
            int index = availableSeats.indexOf(desiredSeat);
            SeatDTO seat = availableSeats.get(index);
            seat.setAvailable(false);
            UUID uuid = UUID.randomUUID();
            PurchaseDTO purchase = new PurchaseDTO(uuid.toString(), seat);
            purchasedTickets.put(uuid.toString(), seat);
            statistics.setCurrentIncome(statistics.getCurrentIncome() + desiredSeat.getPrice());
            statistics.setNumberOfAvailableSeats(statistics.getNumberOfAvailableSeats() - 1);
            statistics.setNumberOfPurchasedTickets(statistics.getNumberOfPurchasedTickets() + 1);
            return new ResponseEntity<>(purchase, HttpStatus.OK);
        } else {
            throw new CinemaPlanException("The ticket has been already purchased!");
        }
    }

    public ResponseEntity<ReturnDTO> makeReturn(Token token) {
        String tokenValue = token.getToken();
        if (tokenIsValid(tokenValue)) {
            SeatDTO seat = purchasedTickets.get(tokenValue);
            seat.setAvailable(true);
            purchasedTickets.remove(tokenValue);
            statistics.setCurrentIncome(statistics.getCurrentIncome() - seat.getPrice());
            statistics.setNumberOfAvailableSeats(statistics.getNumberOfAvailableSeats() + 1);
            statistics.setNumberOfPurchasedTickets(statistics.getNumberOfPurchasedTickets() - 1);
            return new ResponseEntity<>(new ReturnDTO(seat), HttpStatus.OK);
        } else {
            throw new CinemaPlanException("Wrong token!");
        }
    }

    public ResponseEntity<Statistics> showStatistics() {
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    private boolean tokenIsValid(String tokenValue) {
        boolean tokenValid = false;
        if (tokenValue == null || tokenValue.isEmpty() || tokenValue.isBlank() ) {
            tokenValid = false;
        }
        if (purchasedTickets.containsKey(tokenValue)) {
            tokenValid = true;
        }
        return tokenValid;
    }
}
