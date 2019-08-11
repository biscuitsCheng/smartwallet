package com.biscuits.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** @author biscuits */
@SpringBootApplication
@MapperScan(basePackages = "com.biscuits.wallet.mapper")
public class WalletApplication {

  public static void main(String[] args) {
    SpringApplication.run(WalletApplication.class, args);
  }
}
