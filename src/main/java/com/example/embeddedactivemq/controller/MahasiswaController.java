package com.example.embeddedactivemq.controller;

import com.example.embeddedactivemq.model.request.AddMahasiswaRequest;
import com.example.embeddedactivemq.model.response.RestResponse;
import com.example.embeddedactivemq.service.mahasiswa.AddNewMahasiswaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mahasiswa")
@Slf4j
@RequiredArgsConstructor
public class MahasiswaController {

    private final AddNewMahasiswaService addNewMahasiswaService;

    @PostMapping("/v1/add")
    public ResponseEntity<RestResponse> addNewMahasiswa(@RequestBody AddMahasiswaRequest addMahasiswaRequest){
        return new ResponseEntity<>(new RestResponse(null, "Data submitted",
                addNewMahasiswaService.execute(addMahasiswaRequest).getResult()), HttpStatus.OK);
    }

}
