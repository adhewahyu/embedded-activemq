package com.example.embeddedactivemq.service.mahasiswa;

import com.alibaba.fastjson2.JSON;
import com.example.embeddedactivemq.model.request.AddMahasiswaRequest;
import com.example.embeddedactivemq.model.response.ValidationResponse;
import com.example.embeddedactivemq.service.queue.publisher.PublishMahasiswaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddNewMahasiswaService {

    private final PublishMahasiswaService publishMahasiswaService;

    public ValidationResponse execute(AddMahasiswaRequest addMahasiswaRequest){
        log.info("AddNewMahasiswaService - begin");
        doValidateRequest(addMahasiswaRequest);
        publishMahasiswaService.execute(addMahasiswaRequest);
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateRequest(AddMahasiswaRequest addMahasiswaRequest){
        if(StringUtils.isEmpty(addMahasiswaRequest.getNim())){
            log.error("NIM is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NIM is required");
        }
        if(StringUtils.isEmpty(addMahasiswaRequest.getNama())){
            log.error("Nama is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nama is required");
        }
        if(StringUtils.isEmpty(addMahasiswaRequest.getJurusan())){
            log.error("Jurusan is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jurusan is required");
        }
    }

}
