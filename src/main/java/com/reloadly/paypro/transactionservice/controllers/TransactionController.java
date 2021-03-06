package com.reloadly.paypro.transactionservice.controllers;

import com.reloadly.paypro.transactionservice.payload.dto.TransferDTO;
import com.reloadly.paypro.transactionservice.payload.request.TransferRequest;
import com.reloadly.paypro.transactionservice.security.AuthenticatedUserDetails;
import com.reloadly.paypro.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer-funds")
    public ResponseEntity<String> transferFunds(@AuthenticationPrincipal AuthenticatedUserDetails userDetails, @RequestBody TransferRequest transferRequest) {
        String response = transactionService.processFundTransfer(userDetails.getUsername(), transferRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransferDTO>> getTransactionHistory(@AuthenticationPrincipal AuthenticatedUserDetails userDetails) {
        List<TransferDTO> transferDTOList = transactionService.getUserTransactionHistory(userDetails.getUsername());
        return ResponseEntity.ok(transferDTOList);
    }

    @GetMapping("/{transactionReference}")
    public ResponseEntity<TransferDTO> getTransaction(@AuthenticationPrincipal AuthenticatedUserDetails userDetails, @PathVariable String transactionReference) {
        TransferDTO transferDTO = transactionService.getUserTransaction(userDetails.getUsername(), transactionReference);
        return ResponseEntity.ok(transferDTO);
    }
}
