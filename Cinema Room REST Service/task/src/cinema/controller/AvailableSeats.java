package cinema.controller;

import cinema.entity.CinemaPlan;
import cinema.entity.DTO.PurchaseDTO;
import cinema.entity.DTO.ReturnDTO;
import cinema.entity.DTO.SeatDTO;
import cinema.entity.Seat;
import cinema.entity.Statistics;
import cinema.entity.Token;
import cinema.exceptions.CinemaPlanException;
import cinema.exceptions.SecurityException;
import cinema.service.CinemaPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AvailableSeats {

    private static final String PASSWORD = "super_secret";

    @Autowired
    private CinemaPlanService cinemaPlanService;

    @GetMapping("/seats")
    public CinemaPlan getCinemaPlan() {
        return cinemaPlanService.getCinemaPlan();
    }

    @RequestMapping(value = "/purchase", method =
            RequestMethod.POST, consumes = "application/json", produces =
            "application/json")
    public ResponseEntity<PurchaseDTO> purchaseSeat(@RequestBody Seat seat) {
        return cinemaPlanService.makePurchase(seat.getRow(), seat.getColumn());
    }

    @RequestMapping(value = "/return", method =
            RequestMethod.POST, consumes = "application/json", produces =
            "application/json")
    public ResponseEntity<ReturnDTO> returnSeat(@RequestBody Token token) {
        return cinemaPlanService.makeReturn(token);
    }

    @RequestMapping(value = "/stats", method =
            RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Statistics> returnStatistic (@RequestParam(defaultValue = "wrong") String password) {
        if (passwordIsValid(password)) {
            return cinemaPlanService.showStatistics();
        } else {
            throw new SecurityException("The password is wrong!");
        }
    }

    public boolean passwordIsValid(String password) {
        boolean passwordValid = false;
        if (PASSWORD.equals(password)) {
            passwordValid = true;
        }
        return passwordValid;
    }
}
