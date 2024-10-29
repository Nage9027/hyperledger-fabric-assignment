package com.example.fabric.asset_contract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class AssetContract extends Contract {

    @Transaction
    public Asset createAsset(Context ctx, String dealerId, String msisdn, String mpin, double balance, String status, double transAmount, String transType, String remarks) {
        ChaincodeStub stub = ctx.getStub();
        String assetKey = generateAssetKey(dealerId, msisdn);

        String existingAsset = stub.getStringState(assetKey);
        if (existingAsset != null && !existingAsset.isEmpty()) {
            throw new RuntimeException("Asset already exists: " + dealerId + "-" + msisdn);
        }

        Asset newAsset = new Asset(dealerId, msisdn, mpin, balance, status, transAmount, transType, remarks);

 
        stub.putStringState(assetKey, newAsset.toJSONString());

        return newAsset;
    }

    @Transaction
    public Asset updateAsset(Context ctx, String dealerId, String msisdn, double newBalance, String newStatus) {
        ChaincodeStub stub = ctx.getStub();
        String assetKey = generateAssetKey(dealerId, msisdn);
      
        String assetState = stub.getStringState(assetKey);
        if (assetState == null || assetState.isEmpty()) {
            throw new RuntimeException("Asset does not exist: " + dealerId + "-" + msisdn);
        }

        Asset asset = Asset.fromJSONString(assetState);
        asset.updateBalance(newBalance);
        asset.updateStatus(newStatus);
        stub.putStringState(assetKey, asset.toJSONString());

        return asset;
    }

    @Transaction
    public Asset queryAsset(Context ctx, String dealerId, String msisdn) {
        ChaincodeStub stub = ctx.getStub();
        String assetKey = generateAssetKey(dealerId, msisdn);

        String assetState = stub.getStringState(assetKey);
        if (assetState == null || assetState.isEmpty()) {
            throw new RuntimeException("Asset not found: " + dealerId + "-" + msisdn);
        }

        
        return Asset.fromJSONString(assetState);
    }

    @Transaction
    public String getAssetHistory(Context ctx, String dealerId, String msisdn) {
        ChaincodeStub stub = ctx.getStub();
        String assetKey = generateAssetKey(dealerId, msisdn);

        return "Transaction history for asset: " + assetKey;  // Placeholder for the history data
    }

    private String generateAssetKey(String dealerId, String msisdn) {
        return dealerId + "-" + msisdn;
    }
}
