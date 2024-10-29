package com.example.fabric.asset_contract;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private Gateway gateway;

    private Contract getContract() {
        Network network = gateway.getNetwork("mychannel");
        return network.getContract("asset_contract");
    }

    @PostMapping("/create")
    public String createAsset(
            @RequestParam String dealerId,
            @RequestParam String msisdn,
            @RequestParam String mpin,
            @RequestParam double balance,
            @RequestParam String status,
            @RequestParam double transAmount,
            @RequestParam String transType,
            @RequestParam String remarks) {

        try {
            Contract contract = getContract();
            contract.submitTransaction("createAsset", dealerId, msisdn, mpin, 
                                       String.valueOf(balance), status, 
                                       String.valueOf(transAmount), transType, remarks);
            return "Asset created successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PutMapping("/update")
    public String updateAsset(
            @RequestParam String dealerId,
            @RequestParam String msisdn,
            @RequestParam double newBalance,
            @RequestParam String newStatus) {

        try {
            Contract contract = getContract();
            contract.submitTransaction("updateAsset", dealerId, msisdn, 
                                       String.valueOf(newBalance), newStatus);
            return "Asset updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/query")
    public String queryAsset(@RequestParam String dealerId, @RequestParam String msisdn) {
        try {
            Contract contract = getContract();
            byte[] result = contract.evaluateTransaction("queryAsset", dealerId, msisdn);
            return new String(result);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/history")
    public String getAssetHistory(@RequestParam String dealerId, @RequestParam String msisdn) {
        try {
            Contract contract = getContract();
            byte[] result = contract.evaluateTransaction("getAssetHistory", dealerId, msisdn);
            return new String(result);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
