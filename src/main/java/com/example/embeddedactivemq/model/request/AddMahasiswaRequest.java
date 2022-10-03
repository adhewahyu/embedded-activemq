package com.example.embeddedactivemq.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMahasiswaRequest implements Serializable {

    private String nim;
    private String nama;
    private String jurusan;

}
