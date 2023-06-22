package com.qg;

public interface TestConstants {
  String PAYLOAD_DIR = "src/test/resources/payloads";
  String BASE_URL = "http://localhost:3001";
  String SEARCH_ALL_ENDPOINT = "/api/v1/profiles/all";
  String SEARCH_BY_ID_ENDPOINT = "/api/v1/profiles/{id}";
  String CREATE_ENDPOINT = "/api/v1/profiles/create";
  String DELETE_BY_ID_ENDPOINT = "/api/v1/profiles/delete/{id}";
  String UPDATE_BY_ID_ENDPOINT = "/api/v1/profiles/update/{id}";
}
