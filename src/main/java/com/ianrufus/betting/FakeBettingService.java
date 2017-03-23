package com.ianrufus.betting;

import org.springframework.stereotype.Service; 

@Service 
public class FakeBettingService implements IRouletteBettingService { 
  public double GetWinnings() { 
    return 20; 
  } 
} 
