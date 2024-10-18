package com.dlitv.storage.service;

import java.math.BigInteger;

public interface CacheService {

    void saveLastProcessedBlock(BigInteger blockNumber);

    BigInteger getLastProcessedBlock();
}
