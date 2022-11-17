package com.example.handlemanager;

import static org.junit.Assert.assertEquals;

import com.example.handlemanager.service.HandleFactoryService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HandleFactoryServiceTest {

  // Expected format: 20.5000.1025/XRD-2UH-D99
  HandleFactoryService hf;

  @BeforeEach
  void setup() {
    hf = new HandleFactoryService();
  }

  @Test
  void basicHandleGeneration() {
    String newHandle = hf.newHandle();
    testHandleProperties(newHandle);
  }

  void testHandleProperties(String newHandle) {
    assertEquals(24, newHandle.length()); // Correct length
    assertEquals('-', newHandle.charAt(16)); // Dashes are going in right place
    assertEquals('-', newHandle.charAt(20));
  }

  String getStrFromBytes(byte[] handle) {
    return new String(handle);
  }

  @Test
  void batchHandleGeneration() {
    List<byte[]> handleList = hf.newHandle(10);
    String handle;

    for (byte[] h : handleList) {
      handle = getStrFromBytes(h);
      testHandleProperties(handle);
    }
  }

  @Test
  void invalidHandleGenerationParams() {
    List<byte[]> handleListLong = hf.newHandle(1001);
    assertEquals(1000, handleListLong.size());
    List<byte[]> handleListShort = hf.newHandle(-1);
    assertEquals(0, handleListShort.size());
  }

}
